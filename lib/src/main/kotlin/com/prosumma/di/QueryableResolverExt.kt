package com.prosumma.di

inline fun <reified T> QueryableResolver.resolveFilter(
    noinline predicate: Predicate<Key>,
    vararg args: Any
): List<T> = resolveKeys(filter(predicate), *args)