package com.prosumma.di

fun Resolver.tagged(tag: String): TaggedResolver = TaggedResolver(this, tag)

inline fun <reified T> Resolver.tryResolve(vararg args: Any): T? =
    resolve<T>(Key.create<T>(), Params(*args)).second

inline fun <reified T> Resolver.resolve(vararg args: Any): T =
    resolve<T>(Key.create<T>(), Params(*args)).getInstance()

inline fun <reified T> Resolver.resolveKeys(keys: List<Key>, vararg args: Any): List<T> {
    val list = mutableListOf<T>()
    val params = Params(*args)
    for (key in keys) {
        resolve<T>(key, params).second?.let { value ->
            list.add(value)
        }
    }
    return list
}

inline fun <reified T> Resolver.resolveLazy(): LazyResolver<T> =
    LazyResolver.create(this)