package com.prosumma.di

class ResolveException(key: Key): Exception(getMessage(key)) {
    companion object {
        private fun getMessage(key: Key): String =
            when (key.tag) {
                is Unit -> "Could not resolve type '${key.qualifiedName}'."
                else -> "Could not resolve type '${key.qualifiedName}' with tag '${key.tag}'."
            }
    }
}