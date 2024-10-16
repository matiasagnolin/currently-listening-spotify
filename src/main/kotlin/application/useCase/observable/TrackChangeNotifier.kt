package org.spotify.application.useCase.Observable

import org.spotify.application.useCase.Observable.TrackChangeUpdater
import org.spotify.domain.model.User
import org.springframework.stereotype.Service
import se.michaelthelin.spotify.model_objects.IPlaylistItem

@Service
class TrackChangeNotifier {
    private val listeners = mutableListOf<TrackChangeUpdater>()

    fun addListener(listener: TrackChangeUpdater) {
        listeners.add(listener)
    }

    fun notifyTrackChanged(user: User, newTrack: IPlaylistItem) {
        for (listener in listeners) {
            listener.onTrackChanged(user, newTrack)
        }
    }
}