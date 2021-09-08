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

inline fun <reified T> QueryableResolver.resolveAll(
    vararg args: Any,
    tag: Any? = null,
    group: Any? = null
): List<T> {
    var predicate = Key.isClass<T>()
    if (tag != null) predicate = predicate and Key.tagged(tag)
    if (group != null) predicate = predicate and Key.grouped(group)
    return resolveFilter(predicate, *args)
}