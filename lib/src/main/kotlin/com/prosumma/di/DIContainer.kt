package com.prosumma.di

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * A KoDI dependency injection container.
 *
 * In most cases, prefer to use the `DI` instance,
 * which is a subclass of this type.
 *
 * To create a container hierarchy, simply register
 * an instance of `DIContainer` as a tagged singleton
 * in a parent container.
 *
 * ```kotlin
 * class ChildContainerAssembly: AbstractAssembly {
 *   override fun register(registrar: Registrar) {
 *     registrar.singleton<Foo> { Bar() }
 *   }
 * }
 *
 * DI.tag("child").singleton<DIContainer> {
 *   val di = DIContainer()
 *   di.assemble(ChildContainerAssembly())
 *   di
 * }
 * ```
 *
 * The advantage of this is that, if needed, the child
 * container can be dropped with a single statement:
 *
 * ```kotlin
 * DI.unregister(Key.create<DIContainer>("child"))
 * ```
 */
open class DIContainer: Container, Assembler {
    // This container is thread-safe.
    private val lock = ReentrantReadWriteLock()
    private val registrations: MutableMap<Key, Registration<*>> = mutableMapOf()

    internal operator fun get(key: Key): Registration<*>? =
        lock.read { registrations[key] }

    internal operator fun set(key: Key, value: Registration<*>) {
        lock.write { registrations[key] = value }
    }

    override fun <T> register(key: Key, lifetime: Lifetime, definition: Definition<T>): Key {
        this[key] = Factory(lifetime, definition)
        return key
    }

    override fun unregister(key: Key): Boolean = lock.write {
        registrations.remove(key) != null
    }

    @Suppress("UNCHECKED_CAST", "NAME_SHADOWING")
    override fun <T> resolve(key: Key, params: Params): Pair<Key, T?> =
        this[key]?.let { registration ->
            when (registration) {
                is Singleton<*> -> key to (registration.instance as T)
                is Factory<*> -> {
                    val registration = registration as Factory<T>
                    when (registration.lifetime) {
                        Lifetime.FACTORY -> key to registration.resolve(this, params)
                        Lifetime.SINGLETON -> key to
                                resolveFactoryAsSingleton(key, registration, params)
                    }
                }
            }
        } ?: key to null

    @Suppress("UNCHECKED_CAST")
    private fun <T> resolveFactoryAsSingleton(key: Key, factory: Factory<T>, params: Params): T? =
        synchronized(factory) {
            // While we were waiting for the lock, things may have changed,
            // so we need to check that…
            // 1. The key still exists.
            // 2. It's still a factory and not a singleton.
            // 3. It's still the same factory instance.
            this[key]?.let { registration ->
                when (registration) {
                    is Singleton<*> -> registration.instance as T
                    is Factory<*> -> if (registration === factory) {
                        val value = registration.resolve(this, params)
                        this[key] = Singleton(value)
                        value
                    } else {
                        // We can safely recurse and get another lock while keeping this one,
                        // because we have a different instance.
                        resolveFactoryAsSingleton(key, registration as Factory<T>, params)
                    }
                }
            }
        }

    override fun containsKey(key: Key): Boolean =
        lock.read { registrations.containsKey(key) }

    override fun filter(predicate: Predicate<Key>): List<Key> =
        lock.read { registrations.keys.filter(predicate) }

    override fun assemble(vararg assemblies: Assembly) =
        when (assemblies.size) {
            0 -> Unit
            1 -> assembleAssembly(assemblies.first())
            else -> assembleAssembly(object: AbstractAssembly() {
                override val assemblies: List<Assembly>
                    get() = listOf(*assemblies)

                override fun register(registrar: Registrar) {

                }
            })
        }

    @Suppress("NAME_SHADOWING")
    private fun assembleAssembly(assembly: Assembly) {
        val assemblies = gatherAssemblies(assembly)
        for (assembly in assemblies) {
            assembly.register(this)
        }
        for (assembly in assemblies) {
            assembly.registered(this)
        }
    }

    private fun gatherAssemblies(assembly: Assembly): List<Assembly> {
        val set = linkedSetOf<Assembly>()
        gatherAssemblies(set, assembly)
        return set.toList()
    }

    @Suppress("NAME_SHADOWING")
    private fun gatherAssemblies(set: LinkedHashSet<Assembly>, assembly: Assembly) {
        for (assembly in assembly.assemblies) {
            if (!set.contains(assembly)) {
                gatherAssemblies(set, assembly)
            }
        }
        set.add(assembly)
    }

}