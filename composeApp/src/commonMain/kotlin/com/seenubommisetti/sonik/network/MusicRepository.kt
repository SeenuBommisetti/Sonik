package com.seenubommisetti.sonik.network

import com.seenubommisetti.sonik.model.JamendoResponse
import com.seenubommisetti.sonik.model.Track
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import com.seenubommisetti.sonik.database.SonikDatabase

class MusicRepository(private val database: SonikDatabase) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    fun fetchTracks(): Flow<List<Track>> = flow {
        // 1. Emit cached data if available
        val cachedTracks = database.sonikDatabaseQueries.selectAll().executeAsList().map {
            Track(it.id, it.name, it.artistName, it.duration.toInt(), it.image, it.audio)
        }
        
        if (cachedTracks.isNotEmpty()) {
            emit(cachedTracks)
        }

        // 2. Fetch from network
        val clientId = "b40a8710"
        val url = "https://api.jamendo.com/v3.0/tracks/?client_id=$clientId&format=jsonpretty&limit=20&include=musicinfo&group=all"

        try {
            val response: JamendoResponse = client.get(url).body()
            val networkTracks = response.results
            
            if (networkTracks.isNotEmpty()) {
                // 3. Cache the new results
                database.sonikDatabaseQueries.transaction {
                    database.sonikDatabaseQueries.deleteAll()
                    networkTracks.forEach { track ->
                        database.sonikDatabaseQueries.insertTrack(
                            id = track.id,
                            name = track.name,
                            artistName = track.artistName,
                            duration = track.duration.toLong(),
                            image = track.image,
                            audio = track.audio
                        )
                    }
                }
                // Emit final fresh network tracks
                emit(networkTracks)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error fetching tracks: ${e.message}")
            if (cachedTracks.isEmpty()) {
                throw e
            }
        }
    }.flowOn(Dispatchers.Default)
}