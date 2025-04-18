package gy.roach.radio

import java.io.BufferedInputStream
import java.net.URL
import java.io.InputStream
import java.lang.Thread

/**
 * Desktop implementation of the AudioPlayer interface using javazoom.jl.player.Player.
 * Uses reflection to avoid direct imports that might cause issues.
 */
class JvmAudioPlayer : AudioPlayer {
    private var player: Any? = null
    private var playerThread: Thread? = null
    private var playing = false

    override fun play(url: String) {
        // Stop any currently playing audio
        stop()

        try {
            // Create a new thread for playback
            playerThread = Thread {
                try {
                    val bufferedInputStream = BufferedInputStream(URL(url).openStream())

                    // Use reflection to create a Player instance
                    val playerClass = Class.forName("javazoom.jl.player.Player")
                    val constructor = playerClass.getConstructor(InputStream::class.java)
                    player = constructor.newInstance(bufferedInputStream)

                    // Set playing flag
                    playing = true

                    // Call play method
                    val playMethod = playerClass.getMethod("play")
                    playMethod.invoke(player)

                    // When play completes, reset state
                    playing = false
                    player = null
                } catch (e: Exception) {
                    println("Error playing audio: ${e.message}")
                    playing = false
                    player = null
                }
            }

            // Start the playback thread
            playerThread?.start()
        } catch (e: Exception) {
            println("Error setting up audio player: ${e.message}")
        }
    }

    override fun stop() {
        try {
            // Use reflection to call close method if player exists
            if (player != null) {
                val playerClass = player!!.javaClass
                val closeMethod = playerClass.getMethod("close")
                closeMethod.invoke(player)
            }
        } catch (e: Exception) {
            println("Error stopping audio: ${e.message}")
        }

        // Interrupt the player thread
        playerThread?.interrupt()
        playerThread = null
        player = null
        playing = false
    }

    override fun isPlaying(): Boolean {
        return playing
    }
}

/**
 * Get a desktop-specific implementation of the AudioPlayer interface.
 */
actual fun getAudioPlayer(): AudioPlayer = JvmAudioPlayer()
