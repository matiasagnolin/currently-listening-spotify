package org.spotify.infrastracture.spotify.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import se.michaelthelin.spotify.SpotifyApi
import java.net.URI

@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.spotify")
class SpotifyConfig(
    @Value("\${spring.security.oauth2.client.registration.spotify.client-id}")
    private var clientId: String,
    @Value("\${spring.security.oauth2.client.registration.spotify.client-secret}")
    private var clientSecret: String,
    @Value("\${spring.security.oauth2.client.registration.spotify.redirect-uri}")
    private var redirectUri: String
) {
    @Bean
    fun spotifyApi(): SpotifyApi {
        return SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(URI.create(redirectUri))
            .build()
    }
}