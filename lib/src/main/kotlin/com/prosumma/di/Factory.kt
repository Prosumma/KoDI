package com.prosumma.di

internal class Factory<T>(
    val lifetime: Lifetime,
    private val definition: Definition<T>
): Registration<T>() {
    fun resolve(resolver: Resolver, params: Params): T = resolver.definition(params)
}