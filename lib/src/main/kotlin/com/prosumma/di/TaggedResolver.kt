package com.prosumma.di

class TaggedResolver(private val resolver: Resolver, val tag: Any): Resolver {
    override fun <T> resolve(key: Key, params: Params): Pair<Key, T?> =
        resolver.resolve(Key(key.klass, tag), params)
}