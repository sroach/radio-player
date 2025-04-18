package gy.roach.radio

/**
 * JVM implementation of the AudioPlayer interface.
 * This is a simple implementation that delegates to the desktop implementation.
 */
class JvmAudioPlayer : AudioPlayer {
    override fun play(url: String) {
        // Implementation for JVM target
        println("JVM AudioPlayer: Playing $url")
    }

    override fun stop() {
        // Implementation for JVM target
        println("JVM AudioPlayer: Stopping")
    }

    override fun isPlaying(): Boolean {
        // Implementation for JVM target
        return false
    }
}

/**
 * Get a JVM-specific implementation of the AudioPlayer interface.
 */
actual fun getAudioPlayer(): AudioPlayer = JvmAudioPlayer()