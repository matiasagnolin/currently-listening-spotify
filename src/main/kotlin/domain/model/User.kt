package org.spotify.domain.model

import org.springframework.beans.factory.annotation.Value
import se.michaelthelin.spotify.SpotifyApi

class User(
    val username: String,
    val country: String,
    val email: String,
    val birthdate: String,
    val spotifyClient: SpotifyApi
) {
    // Private constructor to enforce the builder pattern
    private constructor(builder: Builder) : this(
        builder.displayName,
        builder.country,
        builder.email,
        builder.birthdate,
        builder.spotifyClient
    )

    // Builder class
    class Builder {
        var displayName: String = ""
            private set
        var country: String = ""
            private set
        var email: String = ""
            private set
        var birthdate: String = ""
            private set
        lateinit var spotifyClient: SpotifyApi

        fun setDisplayName(displayName: String) = apply { this.displayName = displayName }
        fun setCountry(country: String) = apply { this.country = country }
        fun setEmail(email: String) = apply { this.email = email }
        fun setBirthdate(birthdate: String) = apply { this.birthdate = birthdate }
        fun setSpotifyClient(spotifyClient: SpotifyApi) = apply { this.spotifyClient = spotifyClient }

        fun build(): User {
            // Validate required fields before building
            if (displayName.isEmpty()) {
                throw IllegalArgumentException("All fields must be provided.")
            }
            return User(this)
        }
    }

}