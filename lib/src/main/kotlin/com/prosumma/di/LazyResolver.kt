package com.prosumma.di

class LazyResolver<T>(private val resolver: Resolver, private val key: Key) {
    companion object {
        inline fun <reified T> create(resolver: Resolver): LazyResolver<T> =
            LazyResolver(resolver, Key(T::class))
    }

    fun resolve(vararg args: Any): T = resolver.resolve<T>(key, Params(*args)).getInstance()

    fun tryResolve(vararg args: Any): T? = resolver.resolve<T>(key, Params(*args)).second
}