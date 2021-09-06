package com.prosumma.di

class TaggingRegistrar(private val registrar: Registrar, private val tag: Any): Registrar {
    override fun <T> register(key: Key, lifetime: Lifetime, definition: Definition<T>): Key =
        registrar.register(Key(key.klass, tag), lifetime, definition)

    override fun unregister(key: Key): Boolean =
        registrar.unregister(Key(key.klass, tag))
}