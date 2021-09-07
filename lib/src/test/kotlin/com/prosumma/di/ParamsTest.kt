package com.prosumma.di

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ParamsTest {
    fun `parameter destructuring`() {
        val s: String? = null
        val params = Params(7, "something", 4.3, true, s as Any)
        val (i: Int, s2: String, f: Double, b: Boolean, q: String?) = params
        assertEquals(i, 7)
        assertEquals(s2, "something")
        assertEquals(f, 4.3)
        assertTrue(b)
        assertNull(q)
    }

    fun `type coercion`() {
        fun coerce(s: String) = s
        val params = Params("something")
        assertEquals(coerce(params[0]), "something")
    }
}