package gy.roach.radio

import android.media.MediaPlayer
import android.util.Log

/**
 * Android implementation of the AudioPlayer interface using MediaPlayer.
 */
class AndroidAudioPlayer : AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private var playing = false

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
}

/**
 * Get an Android-specific implementation of the AudioPlayer interface.
 */
actual fun getAudioPlayer(): AudioPlayer = AndroidAudioPlayer()