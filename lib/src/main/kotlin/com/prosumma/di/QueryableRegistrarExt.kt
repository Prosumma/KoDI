package com.prosumma.di

import kotlin.reflect.KClass

fun QueryableRegistrar.unregisterFilter(predicate: Predicate<Key>) {
    unregister(filter(predicate))
}

fun QueryableRegistrar.unregisterAll(
    klass: KClass<*>? = null,
    tag: Any? = null,
    group: Any? = null
) {
    var predicate: Predicate<Key> = { true }
    if (klass != null) predicate = predicate and Key.isClass(klass)
    if (tag != null) predicate = predicate and Key.tagged(tag)
    if (group != null) predicate = predicate and Key.grouped(group)
    return unregisterFilter(predicate)
}
