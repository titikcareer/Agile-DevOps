package com.portfolio.app.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TextUtilsTest {

    @Test
    fun `initialsOf returns first and last initial for a two-word name`() {
        assertEquals("AL", TextUtils.initialsOf("Ada Lovelace"))
    }

    @Test
    fun `initialsOf handles a single-word name`() {
        assertEquals("M", TextUtils.initialsOf("Madonna"))
    }

    @Test
    fun `initialsOf collapses extra whitespace and takes only two initials`() {
        assertEquals("JR", TextUtils.initialsOf("  Jordan   Ray Reyes  "))
    }

    @Test
    fun `isValidEmail accepts a well-formed address`() {
        assertTrue(TextUtils.isValidEmail("jordan.reyes@example.com"))
    }

    @Test
    fun `isValidEmail rejects a missing domain`() {
        assertFalse(TextUtils.isValidEmail("jordan.reyes@"))
    }

    @Test
    fun `isValidEmail rejects a missing at symbol`() {
        assertFalse(TextUtils.isValidEmail("jordan.reyes.example.com"))
    }
}
