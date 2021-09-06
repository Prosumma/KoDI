package com.prosumma.di

class Params(vararg params: Any) {
    internal val parameters = params

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(index: Int): T = parameters[index] as T

    operator fun <T> component1(): T = this[0]
    operator fun <T> component2(): T = this[1]
    operator fun <T> component3(): T = this[2]
}