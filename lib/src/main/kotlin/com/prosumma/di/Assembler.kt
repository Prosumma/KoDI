package com.prosumma.di

interface Assembler {
    /**
     * Assembles the dependencies declared
     * in an assembly.
     *
     * If more than one assembly is given, they
     * will be assembled in the order specified.
     *
     * An assembly declares dependencies in its
     * `register` method. A conforming assembler
     * calls `register` on all assemblies in order,
     * starting with the dependent assemblies of the
     * root assembly and recursing to the dependent
     * assemblies of each. Two assemblies are equal
     * if they have the same type, and a conforming
     * implementation must never assemble an assembly
     * twice, even if it occurs more than once in the
     * directed graph of assemblies.
     *
     * After all assemblies have been registered, their
     * `registered` method will be called in the same
     * order.
     */
    fun assemble(vararg assemblies: Assembly)
}