package com.prosumma.di

/**
 * The base class for assemblies.
 *
 * Use this instead of implementing `Assembly`
 * directly.
 */
abstract class AbstractAssembly: Assembly {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (this::class == other::class) return true
        return false
    }

    override fun hashCode(): Int = this::class.hashCode()
}
