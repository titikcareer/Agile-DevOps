package com.portfolio.app.util

/**
 * Small, pure, unit-testable helpers used by the UI layer.
 * Kept separate from Composables so the logic can be tested without
 * pulling in any Compose or Android framework dependency.
 */
object TextUtils {

    private val EMAIL_REGEX = Regex(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )

    /**
     * Derives up to two uppercase initials from a full name, e.g.
     * "Ada Lovelace" -> "AL". Used as a fallback avatar when no photo
     * is available or the image fails to load.
     */
    fun initialsOf(fullName: String): String =
        fullName
            .trim()
            .split(Regex("\\s+"))
            .filter { it.isNotBlank() }
            .take(2)
            .map { it.first().uppercaseChar() }
            .joinToString(separator = "")

    /** Lightweight, dependency-free email format check for UI-level validation. */
    fun isValidEmail(email: String): Boolean = EMAIL_REGEX.matches(email.trim())
}
