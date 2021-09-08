package com.prosumma.di

class Entry<T>(val lifetime: Lifetime, definition: Definition<T>) {
    enum class Status {
        FACTORY,
        UNRESOLVED_SINGLETON,
        RESOLVED_SINGLETON
    }

    private sealed interface Registration<T>
    private class Singleton<T>(val instance: T): Registration<T>
    private class Factory<T>(val definition: Definition<T>): Registration<T>

    private var registration: Registration<T> = Factory(definition)

    val status: Status
        get() = when(registration) {
            is Singleton<T> -> Status.RESOLVED_SINGLETON
            is Factory<T> -> when (lifetime) {
                Lifetime.FACTORY -> Status.FACTORY
                Lifetime.SINGLETON -> Status.UNRESOLVED_SINGLETON
            }
        }

    @Suppress("NAME_SHADOWING")
    fun resolve(resolver: Resolver, params: Params): T =
        when (val current = registration) {
            is Singleton<T> -> current.instance
            is Factory<T> -> synchronized(this) {
                // We have to check again because
                // things may have changed while
                // we were waiting for the lock
                when (val current = registration) {
                    is Singleton<T> -> current.instance
                    is Factory<T> -> {
                        val definition = current.definition
                        val value = resolver.definition(params)
                        registration = Singleton(value)
                        value
                    }
                }
            }
        }
}