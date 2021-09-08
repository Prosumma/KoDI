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
        container.factory<Dependency>(tag)
        assertTrue(container.contains<Dependency>(tag))
        container.unregister<Dependency>(tag)
        assertFalse(container.contains<Dependency>(tag))
    }
}