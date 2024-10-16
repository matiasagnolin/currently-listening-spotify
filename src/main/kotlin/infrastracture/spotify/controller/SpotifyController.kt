package org.spotify.infrastracture.spotify.controller

import org.spotify.application.service.GetCurrentPlayingSongSpotify
import org.spotify.application.service.UserService
import org.spotify.application.service.UserTrackManager
import org.spotify.application.useCase.Observable.TrackChangeChecker
import org.spotify.application.useCase.Observable.TrackChangeNotifier
import org.spotify.application.useCase.Observable.TrackChangeUpdater
import org.spotify.application.useCase.UpdateUserTrack
import org.spotify.domain.model.Song
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import se.michaelthelin.spotify.model_objects.IPlaylistItem

@Controller
@RequestMapping("/spotify")
class SpotifyController(
    private val userService: UserService,
    private val updateUserTrack: UpdateUserTrack,
    private val getCurrentPlayingSongSpotify: GetCurrentPlayingSongSpotify,
    private val trackChangeChecker: TrackChangeChecker,
    private val trackChangeNotifier: TrackChangeNotifier,
    private val trackChangeUpdater: TrackChangeUpdater
) {

    @GetMapping("/success")
    fun getSpotifyAccessToken(
        @RegisteredOAuth2AuthorizedClient("spotify") authorizedClient: OAuth2AuthorizedClient,
        @AuthenticationPrincipal oauth2User: OAuth2User
    ): String {
        val accessToken = authorizedClient.accessToken.tokenValue

        val user = userService.createUser(accessToken, oauth2User.attributes["display_name"].toString());
        val currentlyPlaying = getCurrentPlayingSongSpotify.execute(user)?.item ?: Song()
        updateUserTrack.execute(user, currentlyPlaying)
        trackChangeNotifier.addListener(trackChangeUpdater)
        trackChangeChecker.startChecking(user)

        return "redirect:/spotify/home"
    }

    @GetMapping("/home")
    fun getHome(): ResponseEntity<Map<String, IPlaylistItem>> {
        return ResponseEntity.ok(UserTrackManager.getAllUserTracks())
    }
}