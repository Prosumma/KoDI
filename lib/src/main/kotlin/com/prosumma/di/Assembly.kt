package com.prosumma.di

/**
 * Contract for assemblies.
 */
interface Assembly {
    /**
     * The list of child assemblies of this assembly.
     */
    val assemblies: List<Assembly>
        get() = emptyList()

    /**
     * Implement this method to add registrations
     * using the registrar.
     */
    fun register(registrar: Registrar)

    /**
     * After all registrations are complete,
     * this method is run.
     */
    fun registered(resolver: Resolver) {

    }
}