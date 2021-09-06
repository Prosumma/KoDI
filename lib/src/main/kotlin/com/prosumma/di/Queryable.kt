package com.prosumma.di

interface Queryable {
    fun containsKey(key: Key): Boolean
    fun filter(predicate: Predicate<Key>): List<Key>
}