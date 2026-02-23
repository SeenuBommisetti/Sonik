package com.seenubommisetti.sonik.player

expect class AudioPlayer() {
    var onIsPlayingChanged: ((Boolean) -> Unit)?
    var onError: ((String) -> Unit)?

    fun playUrl(url: String)
    fun togglePlayPause()
    fun getCurrentPosition(): Int
    fun stop()
}
