package org.spotify.application.useCase.Observable

import org.spotify.domain.model.User
import se.michaelthelin.spotify.model_objects.IPlaylistItem

interface TrackChangeObservable {
    fun onTrackChanged(user: User, newTrack: IPlaylistItem)
}