package com.prosumma.di

fun <T> Pair<Key, T?>.getInstance(): T = this.second ?: throw ResolveException(this.first)