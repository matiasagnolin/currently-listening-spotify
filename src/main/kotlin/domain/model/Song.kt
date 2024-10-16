package org.spotify.domain.model

import se.michaelthelin.spotify.enums.ModelObjectType
import se.michaelthelin.spotify.model_objects.IModelObject
import se.michaelthelin.spotify.model_objects.IPlaylistItem
import se.michaelthelin.spotify.model_objects.specification.ExternalUrl
import se.michaelthelin.spotify.model_objects.specification.Track

class Song() : IPlaylistItem {
    override fun builder(): IModelObject.Builder {
        return Track.Builder()
    }

    override fun getDurationMs(): Int {
        return 0
    }

    override fun getExternalUrls(): ExternalUrl {
        return ExternalUrl.Builder().build()
    }

    override fun getHref(): String {
        return ""
    }

    override fun getId(): String {
        return ""
    }

    override fun getName(): String {
        return ""
    }

    override fun getType(): ModelObjectType {
        return ModelObjectType.TRACK
    }

    override fun getUri(): String {
        return ""
    }
}