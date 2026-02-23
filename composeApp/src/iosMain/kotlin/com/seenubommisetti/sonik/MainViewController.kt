package com.seenubommisetti.sonik

import androidx.compose.ui.window.ComposeUIViewController

import platform.AVFoundation.*
import platform.AVFAudio.*
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
fun MainViewController() = ComposeUIViewController {
    try {
        val audioSession = AVAudioSession.sharedInstance()
        audioSession.setCategory(AVAudioSessionCategoryPlayback, null)
        audioSession.setActive(true, null)
    } catch (e: Exception) {
        println("Failed to set audio session category: ${e.message}")
    }
    
    App()
}