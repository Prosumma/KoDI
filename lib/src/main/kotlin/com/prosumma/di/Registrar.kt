package com.prosumma.di

/**
 * Interface for registrars.
 */
interface Registrar {

    /**
     * The base registration method. Avoid calling this directly
     * except when implementing a registrar. Instead, use one
     * of the many extension methods.
     */
    fun <T> register(
        key: Key,
        lifetime: Lifetime = Lifetime.FACTORY,
        definition: Definition<T>
    ): Key

    fun <Keys: Iterable<Key>> unregister(keys: Keys)
}
