package com.prosumma.di

inline fun <reified T> Queryable.contains(tag: Any = Unit): Boolean =
    containsKey(Key.create<T>(tag))
