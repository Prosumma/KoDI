package com.prosumma.di

import kotlin.reflect.KClass

data class Key(val klass: KClass<*>, val tag: Any = Unit, val group: Any = Unit) {
    companion object {
        inline fun <reified T> create(tag: Any = Unit, group: Any = Unit) =
            Key(T::class, tag, group)

        fun isClass(klass: KClass<*>): Predicate<Key> = { key ->
            key.klass == klass
        }

        inline fun <reified T> isClass(): Predicate<Key> = isClass(T::class)

        fun tagged(tag: Any): Predicate<Key> = { key ->
            key.tag == tag
        }

        fun grouped(group: Any): Predicate<Key> = { key ->
            key.group == group
        }
    }

    val qualifiedName: String
        get() = klass.qualifiedName ?:
            throw IllegalArgumentException("Local and anonymous classes cannot be used with DI.")
}
