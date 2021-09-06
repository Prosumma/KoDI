package com.prosumma.di

inline fun <reified T, P> params(noinline init: (P) -> T): Definition<T> = { parameters ->
    init(parameters[0])
}

inline fun <reified T, P1, P2> params(
    noinline init: (P1, P2) -> T
): Definition<T> = { parameters ->
    init(parameters[0], parameters[1])
}

inline fun <reified T, P1, P2, P3> params(
    noinline init: (P1, P2, P3) -> T
): Definition<T> = { parameters ->
    init(parameters[0], parameters[1], parameters[2])
}

inline fun <reified T, P1, P2, P3, P4> params(
    noinline init: (P1, P2, P3, P4) -> T
): Definition<T> = { parameters ->
    init(parameters[0], parameters[1], parameters[2], parameters[3])
}

inline fun <reified T, P1, P2, P3, P4, P5> params(
    noinline init: (P1, P2, P3, P4, P5) -> T
): Definition<T> = { parameters ->
    init(parameters[0], parameters[1], parameters[2], parameters[3], parameters[4])
}