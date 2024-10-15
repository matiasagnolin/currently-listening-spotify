package org.spotify.application.service

import org.spotify.domain.exceptions.InsufficientPermissionsException
import org.spotify.domain.exceptions.InvalidAccessTokenException
import org.spotify.domain.model.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import se.michaelthelin.spotify.SpotifyApi

@Service
class UserService(
    @Value("\${spring.security.oauth2.client.registration.spotify.client-id}")
    private var clientId: String,
    @Value("\${spring.security.oauth2.client.registration.spotify.client-secret}")
    private var clientSecret: String,
) {
    fun createUser(accessToken: String, userName: String): User {
        val userPorfile = validateUserAccess(accessToken)
        return User.Builder()
            .setEmail(userPorfile.email ?: "")
            .setBirthdate(userPorfile.birthdate ?: "")
            .setDisplayName(userPorfile.displayName)
            .setSpotifyClient(createSpotifyApiWithToken(accessToken)).build()
    }

    private fun validateUserAccess(accessToken: String):
            se.michaelthelin.spotify.model_objects.specification.User {
        try {
            val spotifyApi = createSpotifyApiWithToken(accessToken);
            return spotifyApi.currentUsersProfile.build().execute()
        } catch (e: Exception) {
            throw InvalidAccessTokenException("Access token is invalid.")
        }
    }

    private fun checkUserPermissions(scopes: List<String>) {
        val requiredScopes = setOf("user-read-currently-playing", "user-read-email")
        if (!scopes.containsAll(requiredScopes)) {
            throw InsufficientPermissionsException("Missing required permissions.")
        }
    }

    private fun createSpotifyApiWithToken(accessToken: String): SpotifyApi {
        return SpotifyApi.builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setAccessToken(accessToken)
            .build()
    }
}