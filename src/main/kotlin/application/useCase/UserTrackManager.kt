package org.spotify.application.service

import se.michaelthelin.spotify.model_objects.IPlaylistItem
import se.michaelthelin.spotify.model_objects.specification.Track
import java.util.concurrent.ConcurrentHashMap

object UserTrackManager {
    private val userTracks: ConcurrentHashMap<String, IPlaylistItem> = ConcurrentHashMap()

    fun updateTrack(userName: String, track: IPlaylistItem) {
        userTracks[userName] = track
    }

    fun getTrack(userName: String): IPlaylistItem? {
        return userTracks[userName]
    }

    fun removeUserTrack(userName: String) {
        userTracks.remove(userName)
    }

    fun getAllUserTracks(): Map<String, IPlaylistItem> {
        return userTracks.toMap()
    }
}