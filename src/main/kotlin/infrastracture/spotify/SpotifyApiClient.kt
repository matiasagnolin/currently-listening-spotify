package org.spotify.infrastracture.spotify

import org.springframework.stereotype.Component
import se.michaelthelin.spotify.SpotifyApi
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying

@Component
class SpotifyApiClient() {

    fun getCurrentPlayingTrack(spotifyApi: SpotifyApi): CurrentlyPlaying {
        val currentlyPlaying = spotifyApi.usersCurrentlyPlayingTrack.build().execute()
        return currentlyPlaying
    }
}