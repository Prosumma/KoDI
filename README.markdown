# KoDI

KoDI is a tiny dependency injection library written in Kotlin. (It's also not the first Kotlin DI library to be called "KoDI".) 

It's primarily intended as an exercise in learning Kotlin. I have no plans to promote its use, though I may use it for some of my own projects. Its goals are to be very simple, extensible, and pleasant to use.

The unit tests and source documentation will tell you most of what you need to know about using it.

## Examples

```kotlin
interface Plugin
class MyPlugin: Plugin
class YourPlugin: Plugin

class PluginAssembly: Assembly {
  override fun register(registrar: Registar) {
    registrar.apply { 
      singleton<Plugin>("my") { MyPlugin() }
      singleton<Plugin>("your") { YourPlugin() }
    }
  }
}

interface CoolService
class ConcreteCoolService(val target: String): CoolService

class CoreAssembly: Assembly {
  override val assemblies: List<Assembly>
    get() = listOf(PluginAssembly())

  override fun register(registrar: Registrar) {
    registrar.singleton<CoolService>(params(::ConcreteCoolService))
  }
}

fun main() {
  DI.assemble(CoreAssembly())
  // Resolve all plugins, regardless of tag
  val plugins: List<Plugin> = DI.resolveClass<Plugin>()
  // Resolve a particular plugin
  val plugin: Plugin = DI..resolve(tag = "my")
  // Resolve a service with parameter
  val coolService: CoolService = DI.resolve("cool") 
}
```


