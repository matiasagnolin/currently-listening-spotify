package org.spotify.application.useCase

import org.spotify.application.service.UserTrackManager
import org.spotify.application.useCase.Observable.TrackChangeObservable
import org.spotify.domain.model.Song
import org.spotify.domain.model.User
import org.springframework.stereotype.Service
import se.michaelthelin.spotify.model_objects.IPlaylistItem

@Service
class UpdateUserTrack() {
    fun execute(user: User, newTrack: IPlaylistItem) {
        UserTrackManager.updateTrack(user.username, newTrack)
        println("Updated track for user ${user.username}: ${newTrack.name}")
    }

    fun execute(user: User) {
        UserTrackManager.updateTrack(user.username, Song())
    }
}