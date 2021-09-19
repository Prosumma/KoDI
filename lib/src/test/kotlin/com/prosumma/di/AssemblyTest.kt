package com.prosumma.di

import kotlin.test.Test
import kotlin.test.assertTrue

class AssemblyTest {
    class Service

    abstract class CountingAssembly: AbstractAssembly() {
        var count = 0

        override fun registered(resolver: Resolver) {
            count += 1
        }
    }

    class ServiceAssembly: CountingAssembly() {
        override fun register(registrar: Registrar) {
            registrar.singleton<Service>()
        }
    }

    class CoreAssembly: CountingAssembly() {
        override val assemblies: List<Assembly>
            get() = listOf(ServiceAssembly())

        override fun register(registrar: Registrar) {

        }
    }

    @Test
    fun `assembly succeeds`() {
        val container = DIContainer()
        container.assemble(ServiceAssembly())
        assertTrue(container.contains<Service>())
    }

    @Test
    fun `assembly is registered only once by type`() {
        val container = DIContainer()
        val assembly1 = ServiceAssembly()
        val assembly2 = ServiceAssembly()
        container.assemble(assembly1, assembly2, assembly1, ServiceAssembly())
        assertTrue(assembly1.count == 1)
        // Because it was skipped
        assertTrue(assembly2.count == 0)
    }

    @Test
    fun `child assembly is registered`() {
        val container = DIContainer()
        // ServiceAssembly is a child of CoreAssembly
        container.assemble(CoreAssembly())
        assertTrue(container.contains<Service>())
    }
}

