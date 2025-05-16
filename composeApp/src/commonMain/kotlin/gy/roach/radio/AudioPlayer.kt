package gy.roach.radio

/**
 * Interface for platform-specific audio player implementations.
 */
interface AudioPlayer {
    /**
     * Play audio from the given URL.
     * @param url The URL of the audio stream to play.
     */
    fun play(url: String)

    /**
     * Play audio from the given station.
     * @param station The station to play.
     */
    fun play(station: StationItem) {
        // Default implementation calls the URL-only version
        play(station.url)
    }

    /**
     * Stop the currently playing audio.
     */
    fun stop()

    /**
     * Check if audio is currently playing.
     * @return True if audio is playing, false otherwise.
     */
    fun isPlaying(): Boolean
}

/**
 * Get a platform-specific implementation of the AudioPlayer interface.
 */
expect fun getAudioPlayer(): AudioPlayer
