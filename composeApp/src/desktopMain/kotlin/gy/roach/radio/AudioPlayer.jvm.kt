package gy.roach.radio

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.co.caprica.vlcj.factory.MediaPlayerFactory
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter

/**
 * JVM implementation of the AudioPlayer interface.
 * This is a simple implementation that delegates to the desktop implementation.
 */
class JvmAudioPlayer : AudioPlayer {
    private var factory: MediaPlayerFactory? = null
    private var mediaPlayer: MediaPlayer? = null

    init {
        try {
            factory = MediaPlayerFactory()
            mediaPlayer = factory?.mediaPlayers()?.newMediaPlayer()

            // Optional: Listen for events to sync internal state if needed
            mediaPlayer?.events()?.addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
                override fun playing(mediaPlayer: MediaPlayer?) {
                    println("vlcj: Started playing")
                }
                override fun error(mediaPlayer: MediaPlayer?) {
                    println("vlcj: Error occurred")
                }
            })
        } catch (e: Exception) {
            println("Failed to initialize vlcj: ${e.message}")
        }
    }

    // Simulated frequency data for JVM
    private val _frequencyData = MutableStateFlow(FloatArray(16) { 0.1f })
    private val frequencyData: StateFlow<FloatArray> = _frequencyData.asStateFlow()

    override fun play(url: String) {
        println("JVM AudioPlayer: Playing $url")
        mediaPlayer?.media()?.play(url)
        startFrequencyDataSimulation()
    }

    override fun stop() {
        println("JVM AudioPlayer: Stopping")
        mediaPlayer?.controls()?.stop()
        stopFrequencyDataSimulation()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer?.status()?.isPlaying ?: false
    }


    private fun startFrequencyDataSimulation() {
        val simulatedData = FloatArray(16) { index ->
            0.2f + (kotlin.random.Random.nextFloat() * 0.6f)
        }
        _frequencyData.value = simulatedData
    }

    private fun stopFrequencyDataSimulation() {
        _frequencyData.value = FloatArray(16) { 0.1f }
    }
}

/**
 * Get a JVM-specific implementation of the AudioPlayer interface.
 */
actual fun getAudioPlayer(): AudioPlayer = JvmAudioPlayer()