package gy.roach.radio

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.*
import platform.Foundation.*

/**
 * Manager class for configuring the AVAudioSession for iOS.
 * This is crucial for enabling background audio playback when the device is in sleep mode.
 */
class AudioSessionManager {
    companion object {
        private var isConfigured = false

        /**
         * Configure the AVAudioSession for background playback.
         * This must be called before starting audio playback to ensure it continues when the device is in sleep mode.
         */
        @OptIn(ExperimentalForeignApi::class)
        fun configureAudioSession() {
            if (isConfigured) {
                println("Audio session already configured")
                return
            }

            try {
                println("Configuring AVAudioSession for background playback")

                // Get the shared audio session
                val audioSession = AVAudioSession.sharedInstance()

                // Set the audio session category to playback
                // This is crucial for background audio playback
                audioSession.setCategory(AVAudioSessionCategoryPlayback, null)

                // Set the audio session mode to default
                audioSession.setMode(AVAudioSessionModeDefault, null)

                // Activate the audio session
                audioSession.setActive(true, null)

                isConfigured = true
                println("AVAudioSession configured successfully for background playback")
            } catch (e: Exception) {
                println("Error configuring AVAudioSession: ${e.message}")
            }
        }

        /**
         * Deactivate the audio session when playback is stopped.
         */
        @OptIn(ExperimentalForeignApi::class)
        fun deactivateAudioSession() {
            if (!isConfigured) {
                return
            }

            try {
                println("Deactivating AVAudioSession")

                // Get the shared audio session
                val audioSession = AVAudioSession.sharedInstance()

                // Deactivate the audio session
                audioSession.setActive(false, null)

                isConfigured = false
                println("AVAudioSession deactivated")
            } catch (e: Exception) {
                println("Error deactivating AVAudioSession: ${e.message}")
            }
        }
    }
}
