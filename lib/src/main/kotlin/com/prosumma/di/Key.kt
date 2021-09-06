package com.prosumma.di

import kotlin.reflect.KClass

data class Key(val klass: KClass<*>, val tag: Any = Unit) {
    companion object {
        inline fun <reified T> create(tag: Any = Unit) =
            Key(T::class, tag)

        inline fun <reified T> isClass(): Predicate<Key> = { key ->
            val klass = T::class
            key.klass == klass
        }

        fun tagged(tag: String): Predicate<Key> = { key ->
            key.tag == tag
        }
    }

    val qualifiedName: String
        get() = klass.qualifiedName ?:
            throw IllegalArgumentException("Local and anonymous classes cannot be used with DI.")
}
