package com.prosumma.di

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * A KoDI dependency injection container.
 *
 * In most cases, prefer to use the `DI` instance,
 * which is a subclass of this type.
 */
open class DIContainer: Container, Assembler {
    // This container is thread-safe.
    private val lock = ReentrantReadWriteLock()
    private val entries: MutableMap<Key, Entry<*>> = mutableMapOf()

    internal operator fun get(key: Key): Entry<*>? =
        lock.read { entries[key] }

    internal operator fun set(key: Key, value: Entry<*>) {
        lock.write { entries[key] = value }
    }

    override fun <T> register(key: Key, lifetime: Lifetime, definition: Definition<T>): Key {
        this[key] = Entry(lifetime, definition)
        return key
    }

    override fun <Keys: Iterable<Key>> unregister(keys: Keys) {
        lock.write {
            for (key in keys) {
                entries.remove(key)
            }
        }
    }

    @Suppress("UNCHECKED_CAST", "NAME_SHADOWING")
    override fun <T> resolve(key: Key, params: Params): Pair<Key, T?> =
        key to this[key]?.let { entry ->
            val entry = entry as Entry<T>
            entry.resolve(this, params)
        }

    override fun containsKey(key: Key): Boolean =
        lock.read { entries.containsKey(key) }

    override fun filter(predicate: Predicate<Key>): List<Key> =
        lock.read { entries.keys.filter(predicate) }

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