package com.prosumma.di

interface Assembler {
    /**
     * Assembles the dependencies declared
     * in an assembly.
     *
     * If more than one assembly is given, they
     * will be assembled in the order specified.
     *
     * An assembly declares dependent assemblies
     * in its `assemblies` property, which returns
     * an ordered list of assemblies to be registered.
     * Two assemblies are considered identical if they
     * have the same type. The assembler ensures that
     * an assembly is only registered once, no matter
     * how many times it appears in the directed graph
     * of assemblies.
     *
     * However, all of the above only applies during
     * an invocation of `assemble`. If `assemble` is
     * run more than once for the same assemblies,
     * they will be registered more than once.
     *
     * After all assemblies have been registered, their
     * `registered` method will be called in the same
     * order.
     */
    fun assemble(vararg assemblies: Assembly)
}