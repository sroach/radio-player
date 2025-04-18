package gy.roach.radio

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLAudioElement
import org.w3c.dom.events.Event

/**
 * WebAssembly implementation of the AudioPlayer interface using HTML5 Audio element.
 */
class WasmJsAudioPlayer : AudioPlayer {
    private var audioElement: HTMLAudioElement? = null
    private var playing = false

    override fun play(url: String) {
        // Stop any currently playing audio
        stop()

        try {
            // Create a new audio element
            val audio = document.createElement("audio") as HTMLAudioElement
            audio.src = url
            audio.autoplay = true

            // Set up event listeners using addEventListener instead of direct property assignment
            audio.addEventListener("playing", {
                playing = true
                null // Return null to satisfy the JsAny? return type
            })

            audio.addEventListener("ended", {
                playing = false
                audioElement = null
                null // Return null to satisfy the JsAny? return type
            })

            // We'll use a timeout to check if playback started successfully
            window.setTimeout({
                if (audioElement != null && !playing) {
                    window.alert("Failed to play audio. Please try again.")
                    stop()
                }
                null // Return null to satisfy the JsAny? return type
            }, 5000) // 5 seconds timeout

            audioElement = audio

            // Start playback
            audio.play()
        } catch (e: Exception) {
            window.alert("Error setting up audio player: ${e.message}")
            playing = false
            audioElement = null
        }
    }

    override fun stop() {
        try {
            audioElement?.pause()
            audioElement = null
            playing = false
        } catch (e: Exception) {
            window.alert("Error stopping audio: ${e.message}")
        }
    }

    override fun isPlaying(): Boolean {
        return playing
    }
}

/**
 * Get a WebAssembly-specific implementation of the AudioPlayer interface.
 */
actual fun getAudioPlayer(): AudioPlayer = WasmJsAudioPlayer()
