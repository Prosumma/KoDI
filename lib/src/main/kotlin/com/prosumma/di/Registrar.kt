package com.prosumma.di

interface Registrar {

    fun <T> register(
        key: Key,
        lifetime: Lifetime = Lifetime.FACTORY,
        definition: Definition<T>
    ): Key

    fun unregister(key: Key): Boolean
}
