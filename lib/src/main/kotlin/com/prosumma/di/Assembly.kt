package com.prosumma.di

/**
 * Contract for assemblies.
 *
 * Any two assemblies having the same type
 * must be `==` and must give the same hashcode.
 * For this reason, it's recommended to subclass
 * `AbstractAssembly` rather than implementing
 * this interface directly.
 */
interface Assembly {
    /**
     * The ordered list of child assemblies of this assembly.
     *
     * When `Assembler::assemble` is run, all assemblies which
     * have not already been registered will be registered in
     * order.
     */
    val assemblies: List<Assembly>
        get() = emptyList()

    /**
     * Implement this method to add registrations
     * using the registrar.
     *
     * Because defaults are provided for the others,
     * this is the only required method.
     */
    fun register(registrar: Registrar)

    /**
     * After all registrations are complete,
     * this method is run.
     */
    fun registered(resolver: Resolver) {

    }
}