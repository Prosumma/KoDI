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

    /**
     * Resolves an entry.
     *
     * This is the heart of everything. This is where resolution
     * actually occurs.
     */
    @Suppress("NAME_SHADOWING")
    fun resolve(resolver: Resolver, params: Params): T =
        when (val current = registration) {
            // If we're a singleton, just return the value
            is Singleton<T> -> current.instance
            is Factory<T> -> when (lifetime) {
                // If we're just a factory, then run the definition
                Lifetime.FACTORY -> {
                    val definition = current.definition
                    resolver.definition(params)
                }
                // If we're an unresolved singleton, things get interesting.
                // First, we have to lock to make sure that a singleton
                // is only resolved once.
                Lifetime.SINGLETON -> synchronized(this) {
                    // We must check again because things may have
                    // changed while we were waiting on the lock.
                    when (val current = registration) {
                        // While we were waiting on the lock,
                        // another thread resolved the singleton,
                        // so we just return it.
                        is Singleton<T> -> current.instance
                        // We get to resolve the singleton. It's
                        // pretty simple. We invoke the definition
                        // on the resolver, create a singleton with it
                        // and assign it to our registration property
                        // so that it will be used henceforth with no
                        // need to lock again.
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
}