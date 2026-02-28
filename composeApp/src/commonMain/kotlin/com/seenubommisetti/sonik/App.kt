package com.seenubommisetti.sonik

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.seenubommisetti.sonik.ui.TrackListScreen

import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        MaterialTheme {
            TrackListScreen()
        }
    }
}
