package gy.roach.radio

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * JVM implementation of the AudioPlayer interface.
 * This is a simple implementation that delegates to the desktop implementation.
 */
class JvmAudioPlayer : AudioPlayer {
    private var playing = false

    // Simulated frequency data for JVM
    private val _frequencyData = MutableStateFlow(FloatArray(16) { 0.1f })
    private val frequencyData: StateFlow<FloatArray> = _frequencyData.asStateFlow()

    override fun play(url: String) {
        // Implementation for JVM target
        println("JVM AudioPlayer: Playing $url")
        playing = true
        startFrequencyDataSimulation()
    }

    override fun stop() {
        // Implementation for JVM target
        println("JVM AudioPlayer: Stopping")
        playing = false
        stopFrequencyDataSimulation()
    }

    override fun isPlaying(): Boolean {
        // Implementation for JVM target
        return playing
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