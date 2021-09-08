package com.prosumma.di

import kotlin.reflect.KClass

/**
 * Finds the key matching the given criteria. Note that `tag` and `group` default to
 * `Unit`.
 */
inline fun <reified T> Queryable.contains(tag: Any = Unit, group: Any = Unit): Boolean =
    containsKey(Key.create<T>(tag, group))

/**
 * Asks whether the receiver contains any keys that match all of the given criteria.
 *
 * For example:
 *
 * ```kotlin
 * queryable.containsAny(Plugin::class, group = "plugins")
 * ```
 *
 * The above expression returns `true` if the are any keys for the type `Plugin::class` in
 * the "plugins" group.
 */
fun Queryable.containsAny(klass: KClass<*>? = null, tag: Any? = null, group: Any? = null): Boolean {
    var predicate: Predicate<Key> = { true }
    if (klass != null) predicate = predicate and Key.isClass(klass)
    if (tag != null) predicate = predicate and Key.tagged(tag)
    if (group != null) predicate = predicate and Key.grouped(group)
    return filter(predicate).isNotEmpty()
}