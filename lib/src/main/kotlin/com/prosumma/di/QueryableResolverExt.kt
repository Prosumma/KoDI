package com.prosumma.di

/**
 * Resolves the results of the filter as `List<T>`.
 *
 * This is a potentially dangerous method. If one of
 * the registrations is not of the given type, it will
 * raise an exception.
 */
inline fun <reified T> QueryableResolver.resolveFilter(
    noinline predicate: Predicate<Key>,
    vararg args: Any
): List<T> = resolveKeys(filter(predicate), *args)

/**
 * Resolves all registrations of type `T` whatever
 * their tag.
 */
inline fun <reified T> QueryableResolver.resolveClass(
    vararg args: Any
): List<T> = resolveFilter(Key.isClass<T>(), *args)

/**
 * Resolves all registrations of type `T` with the
 * specified tag.
 */
inline fun <reified T> QueryableResolver.resolveTag(
    tag: Any,
    vararg args: Any
): List<T> = resolveFilter(Key.tagged(tag) and Key.isClass<T>(), *args)