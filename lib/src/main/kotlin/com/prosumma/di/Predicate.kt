package com.prosumma.di

typealias Predicate<T> = (T) -> Boolean

infix fun <T> Predicate<T>.or(next: Predicate<T>): Predicate<T> = { value ->
    this(value) || next(value)
}

infix fun <T> Predicate<T>.and(next: Predicate<T>): Predicate<T> = { value ->
    this(value) && next(value)
}