package com.seenubommisetti.sonik.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Track(
    val id: String,
    val name: String,
    @SerialName("artist_name")
    val artistName: String,
    val duration: Int,
    val image: String,
    val audio: String
)

@Serializable
data class JamendoResponse(
    val results: List<Track>
)