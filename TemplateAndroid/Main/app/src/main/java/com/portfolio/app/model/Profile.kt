package com.portfolio.app.model

/**
 * Immutable representation of a single portfolio profile.
 *
 * Kept deliberately flat and serialization-friendly so it can later be
 * mapped 1:1 onto a network DTO or a Room entity without restructuring
 * the UI layer that consumes it.
 */
data class Profile(
    val fullName: String,
    val title: String,
    val id: String,
    val email: String,
    val phone: String,
    val address: String,
    val photoUrl: String,
    val bio: String,
    val hobbies: List<String>
) {
    init {
        require(fullName.isNotBlank()) { "fullName must not be blank" }
        require(id.isNotBlank()) { "id must not be blank" }
    }
}
