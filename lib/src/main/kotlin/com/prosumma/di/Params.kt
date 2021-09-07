package com.prosumma.di

/**
 * Class used to pass params.
 */
class Params(vararg params: Any) {
    private val parameters = params

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(index: Int): T = parameters[index] as T

    operator fun <T> component1(): T = this[0]
    operator fun <T> component2(): T = this[1]
    operator fun <T> component3(): T = this[2]
    operator fun <T> component4(): T = this[3]
    operator fun <T> component5(): T = this[4]
}