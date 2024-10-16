package org.spotify.application.useCase.Observable

import kotlinx.coroutines.*
import org.spotify.application.service.GetCurrentPlayingSongSpotify
import org.spotify.application.useCase.GetUserTrack
import org.spotify.domain.model.User
import org.springframework.stereotype.Service
import se.michaelthelin.spotify.model_objects.IPlaylistItem

@Service
class TrackChangeChecker(
    private val trackChangeNotifier: TrackChangeNotifier,
    private val getCurrentPlayingSongSpotify: GetCurrentPlayingSongSpotify,
    private val getUserTrack: GetUserTrack
) {
    sealed class TrackResult {
        data class TrackFound(val item: IPlaylistItem) : TrackResult()
        object NoTrack : TrackResult()
    }


    private var checkJob: Job? = null

    fun startChecking(user: User) {
        checkJob = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                when (val result = checkCurrentTrack(user)) {
                    is TrackResult.TrackFound -> {
                        val track = result.item
                        trackChangeNotifier.notifyTrackChanged(user, track)
                        delay(6000L)
                    }

                    TrackResult.NoTrack -> {
                        // Handle the case where no new track is found
                    }
                }
            }
        }
    }

    fun checkCurrentTrack(user: User): TrackResult {
        val currentTrack = getCurrentPlayingSongSpotify.execute(user)
        val lastTrack = getUserTrack.execute(user.username)
        return if (currentTrack != null && !currentTrack.item.name.equals(lastTrack?.name)) {
            TrackResult.TrackFound(currentTrack.item)
        } else {
            TrackResult.NoTrack
        }
    }

    fun stopChecking() {
        checkJob?.cancel()
    }
}
