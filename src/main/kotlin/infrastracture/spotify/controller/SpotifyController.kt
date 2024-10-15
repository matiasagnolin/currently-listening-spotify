package org.spotify.infrastracture.spotify.controller

import org.spotify.application.service.GetCurrentPlayingSong
import org.spotify.application.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RequestMapping("/spotify")
class SpotifyController(
    private val getCurrentPlayingSong: GetCurrentPlayingSong,
    private val userService: UserService
) {

    @GetMapping("/success")
    fun getSpotifyAccessToken(
        @RegisteredOAuth2AuthorizedClient("spotify") authorizedClient: OAuth2AuthorizedClient,
        @AuthenticationPrincipal oauth2User: OAuth2User
    ): ResponseEntity<String> {
        val accessToken = authorizedClient.accessToken.tokenValue
        val user = userService.createUser(accessToken, oauth2User.attributes["display_name"].toString());
        val currentlyPlaying = getCurrentPlayingSong.execute(user)
        val song = currentlyPlaying?.item?.name ?: "No track currently playing";
        return ResponseEntity.ok(song)
    }
}