package com.prosumma.di

inline fun <reified T> Resolver.tryResolve(
    vararg args: Any,
    tag: Any = Unit,
    group: Any = Unit
): T? = resolve<T>(Key.create<T>(tag, group), Params(*args)).second

inline fun <reified T> Resolver.resolve(
    vararg args: Any,
    tag: Any = Unit,
    group: Any = Unit
): T = resolve<T>(Key.create<T>(tag, group), Params(*args)).getInstance()

inline fun <reified T> Resolver.resolveKeys(keys: List<Key>, vararg args: Any): List<T> {
    val klass = T::class
    val list = mutableListOf<T>()
    val params = Params(*args)
    for (key in keys) {
        if (key.klass != klass) {
            val message = "The key with type ${key.qualifiedName} and tag ${key.tag}" +
                    " cannot be resolved as the type ${klass.qualifiedName ?: "<anonymous>"}."
            throw IllegalArgumentException(message)
        }
        resolve<T>(key, params).second?.let { value ->
            list.add(value)
        }
    }
    return list
}