package com.prosumma.di

inline fun <reified T, reified D> auto(noinline init: (D) -> T): Definition<T> = {
    init(resolve())
}

inline fun <reified T, reified D1, reified D2> auto(
    noinline init: (D1, D2) -> T
): Definition<T> = {
    init(resolve(), resolve())
}

inline fun <reified T, reified D1, reified D2, reified D3> auto(
    noinline init: (D1, D2, D3) -> T
): Definition<T> = {
    init(resolve(), resolve(), resolve())
}

inline fun <reified T, reified D1, reified D2, reified D3, reified D4> auto(
    noinline init: (D1, D2, D3, D4) -> T
): Definition<T> = {
    init(resolve(), resolve(), resolve(), resolve())
}

inline fun <reified T, reified D1, reified D2, reified D3, reified D4, reified D5> auto(
    noinline init: (D1, D2, D3, D4, D5) -> T
): Definition<T> = {
    init(resolve(), resolve(), resolve(), resolve(), resolve())
}