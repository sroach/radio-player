package gy.roach.radio

import kotlinx.browser.document
import org.w3c.dom.HTMLAudioElement

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

            // Assign early to avoid races if events fire quickly
            audioElement = audio
            playing = false

            // Configure source and playback properties
            audio.src = url
            audio.autoplay = true
            audio.preload = "auto"

            // Set up event listeners
            audio.addEventListener("playing") {
                playing = true
            }

            audio.addEventListener("ended") {
                playing = false
                audioElement = null
            }

            audio.addEventListener("error") {
                // Playback error event from the media element
                playing = false
            }

            // Start playback
            try {
                audio.play()
            } catch (e: Throwable) {
                // Some browsers may throw synchronously
                println("audio.play() threw: ${e.message}")
                playing = false
            }
        } catch (e: Throwable) {
            println("Error setting up audio player: ${e.message}")
            playing = false
            audioElement = null
        }
    }

    override fun stop() {
        try {
            audioElement?.let { audio ->
                try {
                    audio.pause()
                } catch (_: Throwable) { }
                // Clear source to release any network activity
                audio.src = ""
            }
        } catch (e: Throwable) {
            println("Error stopping audio: ${e.message}")
        } finally {
            audioElement = null
            playing = false
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