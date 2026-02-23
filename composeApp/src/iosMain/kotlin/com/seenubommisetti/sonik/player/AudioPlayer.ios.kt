package com.seenubommisetti.sonik.player

import platform.AVFoundation.*
import platform.Foundation.*
import kotlinx.cinterop.*
import platform.darwin.NSObjectProtocol
import platform.CoreMedia.CMTimeGetSeconds

@OptIn(ExperimentalForeignApi::class)
actual class AudioPlayer actual constructor() {
    private var avPlayer: AVPlayer? = null
    private var observer: NSObjectProtocol? = null

    actual var onIsPlayingChanged: ((Boolean) -> Unit)? = null
    actual var onError: ((String) -> Unit)? = null

    actual fun playUrl(url: String) {
        stop()

        val nsUrl = NSURL.URLWithString(url)
        if (nsUrl == null) {
            onError?.invoke("Invalid URL")
            return
        }

        val playerItem = AVPlayerItem(uRL = nsUrl)
        avPlayer = AVPlayer(playerItem = playerItem)

        observer = NSNotificationCenter.defaultCenter.addObserverForName(
            name = AVPlayerItemDidPlayToEndTimeNotification,
            `object` = playerItem,
            queue = NSOperationQueue.mainQueue,
            usingBlock = { _ ->
                onIsPlayingChanged?.invoke(false)
            }
        )

        avPlayer?.play()
        onIsPlayingChanged?.invoke(true)
    }

    actual fun togglePlayPause() {
        avPlayer?.let { player ->
            if (player.rate != 0.0f) {
                player.pause()
                onIsPlayingChanged?.invoke(false)
            } else {
                player.play()
                onIsPlayingChanged?.invoke(true)
            }
        }
    }

    actual fun getCurrentPosition(): Int {
        val player = avPlayer ?: return 0
        val time = player.currentTime()
        val seconds = CMTimeGetSeconds(time)
        return if (seconds.isNaN()) 0 else (seconds * 1000).toInt()
    }

    actual fun stop() {
        avPlayer?.pause()
        observer?.let {
            NSNotificationCenter.defaultCenter.removeObserver(it)
        }
        observer = null
        avPlayer = null
        onIsPlayingChanged?.invoke(false)
    }
}
