package com.prosumma.di

inline fun <reified T, P, reified D> paramAuto(
    noinline init: (P, D) -> T
): Definition<T> = { parameters ->
    init(parameters[0], resolve())
}

inline fun <reified T, P, reified D1, reified D2> paramAuto(
    noinline init: (P, D1, D2) -> T
): Definition<T> = { parameters ->
    init(parameters[0], resolve(), resolve())
}

inline fun <reified T, P, reified D1, reified D2, reified D3> paramAuto(
    noinline init: (P, D1, D2, D3) -> T
): Definition<T> = { parameters ->
    init(parameters[0], resolve(), resolve(), resolve())
}

inline fun <reified T, P, reified D1, reified D2, reified D3, reified D4> paramAuto(
    noinline init: (P, D1, D2, D3, D4) -> T
): Definition<T> = { parameters ->
    init(parameters[0], resolve(), resolve(), resolve(), resolve())
}

inline fun <reified T, P, reified D1, reified D2, reified D3, reified D4, reified D5> paramAuto(
    noinline init: (P, D1, D2, D3, D4, D5) -> T
): Definition<T> = { parameters ->
    init(parameters[0], resolve(), resolve(), resolve(), resolve(), resolve())
}