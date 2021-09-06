package com.prosumma.di

interface Resolver {
    fun <T> resolve(key: Key, params: Params = Params()): Pair<Key, T?>
}