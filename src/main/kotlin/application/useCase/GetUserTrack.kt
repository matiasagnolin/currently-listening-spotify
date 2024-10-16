package org.spotify.application.useCase

import org.spotify.application.service.UserTrackManager
import org.springframework.stereotype.Service
import se.michaelthelin.spotify.model_objects.IPlaylistItem
import se.michaelthelin.spotify.model_objects.specification.Track

@Service
class GetUserTrack {
    fun execute(userName: String): IPlaylistItem? {
        return UserTrackManager.getTrack(userName)
    }
}