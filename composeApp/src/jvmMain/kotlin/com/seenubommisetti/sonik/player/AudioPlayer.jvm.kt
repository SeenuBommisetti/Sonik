package com.seenubommisetti.sonik.player

actual class AudioPlayer actual constructor() {
    actual var onIsPlayingChanged: ((Boolean) -> Unit)? = null
    actual var onError: ((String) -> Unit)? = null

    actual fun playUrl(url: String) {
        // Stub for JVM. Will be implemented in Phase 2 using JavaFX or similar.
        println("AudioPlayer(JVM): playUrl stub called with $url")
    }

    actual fun togglePlayPause() {
        println("AudioPlayer(JVM): togglePlayPause stub called")
    }

    actual fun getCurrentPosition(): Int {
        return 0
    }

    actual fun stop() {
        println("AudioPlayer(JVM): stop stub called")
    }
}
