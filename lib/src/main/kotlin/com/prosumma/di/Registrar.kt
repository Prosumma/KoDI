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

    /**
     * Unregisters the given key. If the key was found and
     * removed, returns `true`, otherwise `false`.
     */
    fun unregister(key: Key): Boolean
}
