package com.seenubommisetti.sonik.player

import android.media.AudioAttributes
import android.media.MediaPlayer

class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    var onIsPlayingChanged: ((Boolean) -> Unit)? = null

    var onError: ((String) -> Unit)? = null

    fun playUrl(url: String) {

        stop()

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )

            try {
                setDataSource(url)
                prepareAsync()

                setOnPreparedListener {
                    start()
                    onIsPlayingChanged?.invoke(true)
                }

                setOnCompletionListener {
                    onIsPlayingChanged?.invoke(false)
                }

                setOnErrorListener { _, what, extra ->
                    val message = when (what) {
                        MediaPlayer.MEDIA_ERROR_SERVER_DIED -> "Server connection died."
                        MediaPlayer.MEDIA_ERROR_UNKNOWN -> "Unknown media error occurred."
                        else -> "Playback error ($what, $extra)."
                    }

                    onIsPlayingChanged?.invoke(false)

                    onError?.invoke("Stream failed: $message")

                    reset()

                    true
                }

            } catch (e: Exception) {
                e.printStackTrace()
                onIsPlayingChanged?.invoke(false)
                onError?.invoke("Unable to play: ${e.message}")
            }
        }
    }

    fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                onIsPlayingChanged?.invoke(false)
            } else {
                it.start()
                onIsPlayingChanged?.invoke(true)
            }
        }
    }

    fun getCurrentPosition(): Int {
        return try {
            mediaPlayer?.currentPosition ?: 0
        } catch (e: Exception) {
            0
        }
    }

    fun stop() {
        mediaPlayer?.release()
        mediaPlayer = null
        onIsPlayingChanged?.invoke(false)
    }
}