package com.prosumma.di

/**
 * Tags a registration with the given tag. Mirror of the `TaggedResolver`, but for
 * registration. Get an instance with the `tag` method:
 *
 * ```kotlin
 * DI.tag("tag").singleton<Service>()
 * ```
 */
class TaggingRegistrar(private val registrar: Registrar, private val tag: Any): Registrar {
    override fun <T> register(key: Key, lifetime: Lifetime, definition: Definition<T>): Key =
        registrar.register(Key(key.klass, tag), lifetime, definition)

    override fun unregister(key: Key): Boolean =
        registrar.unregister(Key(key.klass, tag))
}