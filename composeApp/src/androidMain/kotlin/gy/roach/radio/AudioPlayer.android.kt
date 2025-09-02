package gy.roach.radio

import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Android implementation of the AudioPlayer interface using MediaPlayer.
 */
class AndroidAudioPlayer : AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private var playing = false

    // For now, provide simulated frequency data for Android
    // TODO: Implement real audio analysis using Visualizer API
    private val _frequencyData = MutableStateFlow(FloatArray(16) { 0.1f })
    private val frequencyData: StateFlow<FloatArray> = _frequencyData.asStateFlow()

    override fun play(url: String) {
        // Stop any currently playing audio
        stop()

        try {
            // Create a new MediaPlayer instance
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                setOnPreparedListener {
                    start()
                    playing = true
                    // Start simulated frequency data updates
                    startFrequencyDataSimulation()
                }
                setOnCompletionListener {
                    playing = false
                    release()
                    mediaPlayer = null
                }
                setOnErrorListener { _, what, extra ->
                    Log.e("AndroidAudioPlayer", "Error playing audio: what=$what, extra=$extra")
                    playing = false
                    release()
                    mediaPlayer = null
                    true
                }
                prepareAsync()
            }
        } catch (e: Exception) {
            Log.e("AndroidAudioPlayer", "Error setting up audio player", e)
            playing = false
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    override fun stop() {
        try {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                }
                release()
            }
            mediaPlayer = null
            playing = false
        } catch (e: Exception) {
            Log.e("AndroidAudioPlayer", "Error stopping audio", e)
        }
    }

    override fun isPlaying(): Boolean {
        return playing
    }



    /**
     * Start simulated frequency data for visualization.
     * TODO: Replace with real audio analysis using Android's Visualizer API
     */
    private fun startFrequencyDataSimulation() {
        // Simple simulation - replace with real audio analysis
        val simulatedData = FloatArray(16) { index ->
            0.2f + (kotlin.random.Random.nextFloat() * 0.6f)
        }
        _frequencyData.value = simulatedData
    }


}

/**
 * Get an Android-specific implementation of the AudioPlayer interface.
 */
actual fun getAudioPlayer(): AudioPlayer = AndroidAudioPlayer()