package com.prosumma.di

import java.lang.ArrayIndexOutOfBoundsException
import kotlin.test.*

class ResolverTest {
    class Service
    class SuperService(val service: Service)

    class Something(val s: String)

    @Test
    fun `resolution succeeds`() {
        val container = DIContainer()
        container.singleton<Service>()
        container.resolve<Service>()
    }

    @Test
    fun `resolution succeeds with dependency`() {
        val container = DIContainer()
        container.singleton<Service>()
        container.singleton(auto(::SuperService))
        val superService = container.resolve<SuperService>()
        val service: Service = container.resolve()
        assertSame(superService.service, service)
    }

    @Test
    fun `resolution of singleton replaces Factory with Singleton`() {
        val container = DIContainer()
        container.singleton<Service>()
        val key = Key.create<Service>()
        var entry = container[key]
        assertNotNull(entry)
        assertEquals(entry.status, Entry.Status.UNRESOLVED_SINGLETON)
        container.resolve<Service>()
        entry = container[key]
        assertNotNull(entry)
        assertEquals(entry.status, Entry.Status.RESOLVED_SINGLETON)
    }

    @Test
    fun `parameters are ignored after first resolution of singleton`() {
        val container = DIContainer()
        container.singleton(params(::Something))
        assertFailsWith(ArrayIndexOutOfBoundsException::class) {
            // In other words, we didn't pass a parameter
            container.resolve<Something>()
        }
        // Parameter is passed, because the registration is still a Factory<*> with
        // SINGLETON lifetime.
        container.resolve<Something>("something")
        // Parameter is no longer needed, because the Factory<*> has now been
        // replaced with a Singleton<*> that doesn't care about parameters.
        // (This doesn't mean you should do this!)
        val something = container.resolve<Something>()
        // Because it's now a singleton, the parameter is ignored.
        container.resolve<Something>("nothing")
        assertEquals(something.s, "something")
    }

    @Test
    fun `tagged resolution succeeds`() {
        val container = DIContainer()
        // Tags can be of any type
        val tag = 7
        container.factory(tag) { params ->
            Something(params[0])
        }
        val something: Something = container.resolve("something", tag = tag)
        assertEquals(something.s, "something")
    }

    @Test
    fun `tryResolve works`() {
        val container = DIContainer()
        val tag = 42
        assertNull(container.tryResolve<Service>(tag = tag))
    }

    @Test
    fun `resolveAll works`() {
        val container = DIContainer()
        val group = 42
        container.factory(params(::Something), group = group)
        container.factory(params(::Something), tag = 1, group = group)
        container.factory(params(::Something), tag = 2, group = group)
        val somethings = container.resolveAll<Something>("fish", group = group)
        assertEquals(somethings.size, 3)
    }
}