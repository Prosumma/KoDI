package com.prosumma.di

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

interface Recipe

class Recipe1: Recipe
class Recipe2: Recipe

interface Dingle
class Dong: Dingle
class Zing(val s: String, val dingle: Dingle)

class CoreRecipeAssembly: AbstractAssembly() {
    override fun register(registrar: Registrar) {
        registrar.apply {
            factory<Dingle> { Dong() }
            factory(paramAuto(::Zing))
            tag(1).factory<Recipe> { Recipe1() }
            tag(2).factory<Recipe> { Recipe2() }
        }
    }
}

class LocalStorage

class Amazing(val lazyLocalStorage: LazyResolver<LocalStorage>)

class Watusi(val s: String)

class CoreAssembly: AbstractAssembly() {
    override val assemblies: List<Assembly>
        get() = listOf(CoreRecipeAssembly())

    override fun register(registrar: Registrar) {
        registrar.singleton(params(::Watusi))
        registrar.tag("bob").singleton<LocalStorage>()
        registrar.singleton<LocalStorage>()
        registrar.factory {
            Amazing(tagged("bob").resolveLazy())
        }
    }

    override fun registered(resolver: Resolver) {
        resolver.resolve<Watusi>("watusi")
    }
}

class RegistrarTest {
    @Test
    fun testIdiocy() {
        DI.assemble(CoreAssembly())

        val zing: Zing = DI.resolve("zing")
        println(zing.s)

        val recipes: List<Recipe> = DI.resolveFilter(Key.isClass<Recipe>())
        assertTrue(recipes.isNotEmpty())

        val amazing: Amazing = DI.resolve()
        val ls = amazing.lazyLocalStorage.resolve()

        assertTrue(DI.filter(Key.isClass<LocalStorage>()).size == 2)

        DI.tag("bob").unregister<LocalStorage>()
        assertFalse(DI.contains<LocalStorage>(tag = "bob"))

        val watusi: Watusi = DI.resolve()
    }
}