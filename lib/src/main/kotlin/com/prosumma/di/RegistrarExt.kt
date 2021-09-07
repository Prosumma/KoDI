package com.prosumma.di

import kotlin.reflect.full.createInstance

inline fun <reified T: Any> Registrar.register(
    lifetime: Lifetime = Lifetime.FACTORY,
    tag: Any = Unit,
    noinline definition: Definition<T>? = null
): Key = register(Key.create<T>(tag), lifetime, definition ?: { T::class.createInstance() })

inline fun <reified T: Any> Registrar.register(
    lifetime: Lifetime = Lifetime.FACTORY,
    noinline definition: Definition<T>
): Key = register(Key.create<T>(), lifetime, definition)

inline fun <reified T: Any> Registrar.factory(
    tag: Any = Unit,
    noinline definition: Definition<T>? = null
): Key = register(Lifetime.FACTORY, tag, definition)

inline fun <reified T: Any> Registrar.factory(
    noinline definition: Definition<T>
): Key = register(Lifetime.FACTORY, definition)

inline fun <reified T: Any> Registrar.singleton(
    tag: Any = Unit,
    noinline definition: Definition<T>? = null
): Key = register(Lifetime.SINGLETON, tag, definition)

inline fun <reified T: Any> Registrar.singleton(
    noinline definition: Definition<T>
): Key = register(Lifetime.SINGLETON, definition)

inline fun <reified T> Registrar.unregister(tag: Any): Boolean =
    unregister(Key.create<T>(tag))