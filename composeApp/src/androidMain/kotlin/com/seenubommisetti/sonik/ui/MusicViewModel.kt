package com.seenubommisetti.sonik.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.seenubommisetti.sonik.model.Track
import com.seenubommisetti.sonik.network.MusicRepository
import com.seenubommisetti.sonik.player.AudioPlayer

class MusicViewModel : ViewModel() {

    private val repository = MusicRepository()
    private val player = AudioPlayer()

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks = _tracks.asStateFlow()

    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack = _currentTrack.asStateFlow()

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition = _currentPosition.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isBuffering = MutableStateFlow(false)
    val isBuffering = _isBuffering.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {

        loadTracks()

        player.onIsPlayingChanged = { playing ->
            _isPlaying.value = playing
            if (playing) {
                _isBuffering.value = false
                startProgressLoop()
            }
        }

        player.onError = { errorMsg ->
            _isBuffering.value = false
            _isPlaying.value = false

            println("Player Error: $errorMsg")
        }
    }

    fun retry() {
        loadTracks()
    }

    private fun loadTracks() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val result = repository.fetchTracks()
                if (result.isNotEmpty()) {
                    _tracks.value = result
                } else {
                    _errorMessage.value = "Unable to load songs. Check your internet connection."
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onTrackSelected(track: Track) {
        _currentTrack.value = track
        _isBuffering.value = true
        player.playUrl(track.audio)
    }

    fun togglePlayPause() {
        player.togglePlayPause()
    }

    fun sortTracks(byDuration: Boolean) {
        val currentList = _tracks.value
        val sortedList = if (byDuration) {
            currentList.sortedBy { it.duration }
        } else {
            currentList.sortedBy { it.name }
        }
        _tracks.value = sortedList
    }

    private fun startProgressLoop() {
        viewModelScope.launch {
            while (_isPlaying.value) {
                _currentPosition.value = player.getCurrentPosition()
                kotlinx.coroutines.delay(1000L)
            }
        }
    }

    fun playNext() {
        val currentList = _tracks.value
        val currentT = _currentTrack.value ?: return

        val index = currentList.indexOf(currentT)

        if (index != -1 && index < currentList.size - 1) {
            onTrackSelected(currentList[index + 1])
        }
    }

    fun playPrevious() {
        val currentList = _tracks.value
        val currentT = _currentTrack.value ?: return

        val index = currentList.indexOf(currentT)

        if (index > 0) {
            onTrackSelected(currentList[index - 1])
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.stop()
    }
}