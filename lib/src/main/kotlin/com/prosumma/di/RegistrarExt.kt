package com.prosumma.di

/*
Why are there so many "unnecessary" overloads?

Kotlin's overload resolution gets confused when using higher-order functions, but not when
using trailing lambdas.

registrar.singleton {
  Dependency()
}

If we always used the form above, there would never be any doubt in Kotlin's mind that the lambda
is the final parameter. It is after all a trailing lambda.

But if we use a higher-order function, Kotlin gets confused, because it could be the tag or the
group, which has the type Any:

registrar.singleton(auto(::Service))

These overloads eliminate Kotlin's confusion when using higher-order functions. Whenever
using a higher-order function, always use the overload that puts the Definition before the
group and tag. When using a trailing lambda, do the opposite.

registrar.singleton(auto(::Foo), group = "foo")
registrar.singleton(group = "foo") {
  Foo(resolve())
}
 */

import kotlin.reflect.full.createInstance

inline fun <reified T: Any> Registrar.register(
    lifetime: Lifetime = Lifetime.FACTORY,
    tag: Any = Unit,
    group: Any = Unit,
    noinline definition: Definition<T>? = null
): Key = register(Key.create<T>(tag, group), lifetime, definition ?: { T::class.createInstance() })

inline fun <reified T> Registrar.register(
    noinline definition: Definition<T>,
    lifetime: Lifetime = Lifetime.FACTORY,
    tag: Any = Unit,
    group: Any = Unit
): Key = register(Key.create<T>(tag, group), lifetime, definition)

inline fun <reified T: Any> Registrar.factory(
    tag: Any = Unit,
    group: Any = Unit,
    noinline definition: Definition<T>? = null
): Key = register(Lifetime.FACTORY, tag, group, definition)

inline fun <reified T> Registrar.factory(
    noinline definition: Definition<T>,
    tag: Any = Unit,
    group: Any = Unit
): Key = register(definition, Lifetime.FACTORY, tag, group)

inline fun <reified T: Any> Registrar.singleton(
    tag: Any = Unit,
    group: Any = Unit,
    noinline definition: Definition<T>? = null
): Key = register(Lifetime.SINGLETON, tag, group, definition)

inline fun <reified T> Registrar.singleton(
    noinline definition: Definition<T>,
    tag: Any = Unit,
    group: Any = Unit
): Key = register(definition, Lifetime.SINGLETON, tag, group)

fun Registrar.unregister(key: Key) = unregister(listOf(key))

inline fun <reified T> Registrar.unregister(
    tag: Any = Unit,
    group: Any = Unit
) = unregister(Key.create<T>(tag, group))