package org.spotify.application.useCase.Observable

import org.spotify.application.useCase.Observable.TrackChangeObservable
import org.spotify.application.useCase.UpdateUserTrack
import org.spotify.domain.model.User
import org.springframework.stereotype.Service
import se.michaelthelin.spotify.model_objects.IPlaylistItem

@Service
class TrackChangeUpdater(private val updateUserTrack: UpdateUserTrack) : TrackChangeObservable {
    override fun onTrackChanged(user: User, newTrack: IPlaylistItem) {
        updateUserTrack.execute(user, newTrack)
    }
}