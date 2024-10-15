package org.spotify.application.service

import org.spotify.domain.model.User
import org.spotify.infrastracture.spotify.SpotifyApiClient
import org.springframework.stereotype.Service
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying

@Service
class GetCurrentPlayingSong(
    private val spotifyApiClient: SpotifyApiClient
) {
    fun execute(user: User): CurrentlyPlaying? {
        return spotifyApiClient.getCurrentPlayingTrack(user.spotifyClient)
    }
}