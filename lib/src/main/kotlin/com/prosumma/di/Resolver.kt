package com.prosumma.di

import java.lang.ClassCastException
import kotlin.jvm.Throws

/**
 * The interface implemented by a resolver.
 */
interface Resolver {
    /**
     * The root resolution method.
     *
     * This method should not be called directly. Instead,
     * a large number of extensions are provided based on
     * this one.
     *
     * To implement a resolver, simply implement this method
     * and the rest comes for free.
     *
     * A conforming implementation throws `ClassCastException`
     * if the registered type is not `T`.
     */
    fun <T> resolve(key: Key, params: Params = Params()): Pair<Key, T?>
}