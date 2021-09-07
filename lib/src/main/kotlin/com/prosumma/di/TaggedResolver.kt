package com.prosumma.di

/**
 * A resolver that uses the given tag for resolution.
 *
 * This type ultimately exists to prevent ambiguity with method
 * resolution. Right now, the `resolve` method looks like this:
 *
 * ```kotlin
 * resolve(vararg args: Any): T
 * ```
 *
 * Since a tag also has the type `Any`, where would it go? The obvious
 * place is at the beginning:
 *
 * ```kotlin
 * resolve(tag: Any, vararg args: Any)
 * ```
 *
 * But now the tag is required. If it weren't we'd have no way to distinguish
 * between the overloads with and without the tag. Since tagging is not the common
 * case, we use the `TaggedResolver`. We get an instance of it with the `tagged`
 * method.
 *
 * ```kotlin
 * DI.tagged("tag").resolve(1, 2, 3)
 * ```
 */
class TaggedResolver(private val resolver: Resolver, private val tag: Any): Resolver {
    override fun <T> resolve(key: Key, params: Params): Pair<Key, T?> =
        resolver.resolve(Key(key.klass, tag), params)
}