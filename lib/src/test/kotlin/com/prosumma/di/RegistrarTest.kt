package com.prosumma.di

import kotlin.test.*

class RegistrarTest {
    class Dependency

    @Test
    fun `registration succeeds`() {
        val container = DIContainer()
        container.factory<Dependency>()
        assertTrue(container.contains<Dependency>())
        val key = Key.create<Dependency>()
        val entry = container[key]
        assertNotNull(entry)
        assertEquals(entry.status, Entry.Status.FACTORY)
    }

    @Test
    fun `unregistration succeeds`() {
        val container = DIContainer()
        val tag = "dep"
        container.factory<Dependency>(tag = tag)
        assertTrue(container.contains<Dependency>(tag))
        container.unregister<Dependency>(tag)
        assertFalse(container.contains<Dependency>(tag))
    }

    @Test
    fun `unregisterAll succeeds`() {
        val container = DIContainer()
        val group = "foo"
        container.factory<Dependency>(tag = 1, group = group)
        container.factory<Dependency>(tag = 2, group = group)
        assertTrue(container.containsAny(group = group))
        container.unregisterAll(group = group)
        assertFalse(container.containsAny(group = group))
    }
}