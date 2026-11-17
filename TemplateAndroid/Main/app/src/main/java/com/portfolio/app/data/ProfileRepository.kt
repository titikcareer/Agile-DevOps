package com.portfolio.app.data

import com.portfolio.app.model.Profile

/**
 * Single source of truth for profile data.
 *
 * Today this returns a static sample. Swapping in a network or database
 * source later only means changing this file — the UI layer depends on
 * this interface, not on where the data actually comes from.
 */
interface ProfileRepository {
    fun getProfile(): Profile
}

class InMemoryProfileRepository : ProfileRepository {
    override fun getProfile(): Profile = Profile(
        fullName = "Jordan Reyes",
        title = "Mobile Application Developer",
        id = "DEV-2026-0417",
        email = "jordan.reyes@example.com",
        phone = "+1 (555) 010-2938",
        address = "San Francisco, California, USA",
        photoUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=400",
        bio = "Kotlin-focused mobile developer building fast, well-structured " +
            "Android applications with Jetpack Compose.",
        hobbies = listOf(
            "Open-Source Contributions",
            "Chess",
            "Hiking",
            "Photography",
            "Electronic Music Production"
        )
    )
}
