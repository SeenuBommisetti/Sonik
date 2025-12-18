package com.seenubommisetti.sonik.network

import com.seenubommisetti.sonik.model.JamendoResponse
import com.seenubommisetti.sonik.model.Track
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MusicRepository {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun fetchTracks(): List<Track> {
        val clientId = "b40a8710"
        val url = "https://api.jamendo.com/v3.0/tracks/?client_id=$clientId&format=jsonpretty&limit=20&include=musicinfo&group=all"

        return try {
            val response: JamendoResponse = client.get(url).body()
            response.results
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error fetching tracks: ${e.message}")
            emptyList()
        }
    }
}