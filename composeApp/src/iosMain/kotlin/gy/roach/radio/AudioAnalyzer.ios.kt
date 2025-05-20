package gy.roach.radio

import kotlinx.cinterop.*
import platform.AVFAudio.*
import platform.Foundation.*
import platform.darwin.*
import kotlin.math.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Class for analyzing audio data in real-time on iOS.
 * Uses AVAudioEngine to tap into the audio stream and perform FFT analysis.
 */
class AudioAnalyzer {
    // Audio engine components
    private val audioEngine = AVAudioEngine()
    private val playerNode = AVAudioPlayerNode()
    private val mixerNode = audioEngine.mainMixerNode

    // FFT analysis settings
    private val bufferSize = 1024
    private val fftSize = bufferSize / 2

    // State flow for frequency data
    private val _frequencyData = MutableStateFlow(FloatArray(16) { 0.1f })
    val frequencyData: StateFlow<FloatArray> = _frequencyData.asStateFlow()

    // Flag to track if analyzer is running
    private var isRunning = false

    init {
        setupAudioEngine()
    }

    /**
     * Set up the audio engine for analysis.
     */
    @OptIn(ExperimentalForeignApi::class)
    private fun setupAudioEngine() {
        try {
            // Make sure the audio session is configured
            AudioSessionManager.configureAudioSession()

            // Attach player node to engine
            audioEngine.attachNode(playerNode)

            // Connect player to mixer
            val playerFormat = playerNode.outputFormatForBus(0u)
            audioEngine.connect(playerNode, to = mixerNode, format = playerFormat)

            // Install tap on mixer node for audio analysis
            installTap()

            println("Audio analyzer engine set up successfully")
        } catch (e: Exception) {
            println("Error setting up audio analyzer: ${e.message}")
        }
    }

    /**
     * Install a tap on the mixer node to receive audio data for analysis.
     */
    @OptIn(ExperimentalForeignApi::class)
    private fun installTap() {
        try {
            // Remove any existing tap
            mixerNode.removeTapOnBus(0u)

            // Install new tap
            mixerNode.installTapOnBus(
                0u,
                bufferSize = bufferSize.toUInt(),
                format = null, // Use same format as the node
                block = { buffer, _ ->
                    // Process audio buffer if not null
                    buffer?.let { processAudioBuffer(it) }
                }
            )

            println("Audio tap installed successfully")
        } catch (e: Exception) {
            println("Error installing audio tap: ${e.message}")
        }
    }

    /**
     * Process audio buffer to extract frequency data.
     */
    @OptIn(ExperimentalForeignApi::class)
    private fun processAudioBuffer(buffer: AVAudioPCMBuffer) {
        // Get buffer data
        val channelData = buffer.floatChannelData
        if (channelData == null) {
            return
        }

        // Get the first channel of audio data
        val data = channelData[0] ?: return
        val frameLength = buffer.frameLength.toInt()

        if (frameLength <= 0) {
            return
        }

        // Create a new array to hold the frequency data
        val newFrequencyData = FloatArray(16) { 0.1f }

        // Simple analysis: divide the buffer into 16 segments and calculate average amplitude
        val segmentSize = frameLength / 16

        for (i in 0 until 16) {
            var sum = 0.0f
            val startIdx = i * segmentSize
            val endIdx = minOf((i + 1) * segmentSize, frameLength)

            for (j in startIdx until endIdx) {
                // Get absolute value of sample
                val sample = data[j]
                sum += abs(sample)
            }

            // Calculate average amplitude for this segment
            val average = if (endIdx > startIdx) sum / (endIdx - startIdx) else 0.0f

            // Scale to 0.05-0.95 range and apply some smoothing with previous value
            val currentValue = _frequencyData.value[i]
            val newValue = (average * 4.0f).coerceIn(0.05f, 0.95f)
            newFrequencyData[i] = (currentValue * 0.3f + newValue * 0.7f).coerceIn(0.05f, 0.95f)
        }

        // Update frequency data
        _frequencyData.value = newFrequencyData
    }

    /**
     * Start the audio analyzer.
     */
    @OptIn(ExperimentalForeignApi::class)
    fun start() {
        if (isRunning) return

        try {
            // Start the audio engine
            audioEngine.prepare()
            audioEngine.startAndReturnError(null)

            isRunning = true
            println("Audio analyzer started")
        } catch (e: Exception) {
            println("Error starting audio analyzer: ${e.message}")
        }
    }

    /**
     * Stop the audio analyzer.
     */
    @OptIn(ExperimentalForeignApi::class)
    fun stop() {
        if (!isRunning) return

        try {
            // Stop the audio engine
            audioEngine.stop()
            mixerNode.removeTapOnBus(0u)

            // Reset frequency data to minimum values
            _frequencyData.value = FloatArray(16) { 0.1f }

            isRunning = false
            println("Audio analyzer stopped")
        } catch (e: Exception) {
            println("Error stopping audio analyzer: ${e.message}")
        }
    }

    /**
     * Get the current frequency data.
     */
    fun getFrequencyData(): FloatArray {
        return frequencyData.value
    }
}

/**
 * Singleton instance of AudioAnalyzer.
 */
object AudioAnalyzerManager {
    private val analyzer = AudioAnalyzer()

    /**
     * Start the audio analyzer.
     */
    fun start() {
        analyzer.start()
    }

    /**
     * Stop the audio analyzer.
     */
    fun stop() {
        analyzer.stop()
    }

    /**
     * Get the current frequency data.
     */
    fun getFrequencyData(): FloatArray {
        return analyzer.getFrequencyData()
    }

    /**
     * Get the frequency data flow.
     */
    fun getFrequencyDataFlow(): StateFlow<FloatArray> {
        return analyzer.frequencyData
    }
}
