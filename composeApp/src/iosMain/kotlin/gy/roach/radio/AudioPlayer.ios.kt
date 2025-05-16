package gy.roach.radio

import platform.AVFoundation.*
import platform.Foundation.NSURL
import platform.MediaPlayer.*
import platform.UIKit.UIImage

/**
 * iOS implementation of the AudioPlayer interface using AVPlayer.
 */
class IosAudioPlayer : AudioPlayer {
    private var player: AVPlayer? = null
    private var playing = false
    private var nowPlayingInfo = mutableMapOf<Any?, Any?>()
    private val remoteCommandCenter = MPRemoteCommandCenter.sharedCommandCenter()
    private var currentUrl: String? = null
    private var currentStation: StationItem? = null

    init {
        println("Initializing IosAudioPlayer for background playback")
        setupRemoteTransportControls()

        // Configure audio session at initialization
        AudioSessionManager.configureAudioSession()
    }

    /**
     * Set up remote transport controls to handle play/pause commands from the notification center.
     */
    private fun setupRemoteTransportControls() {
        try {
            println("Setting up remote transport controls")

            // Handle play command
            remoteCommandCenter.playCommand.addTargetWithHandler { _ ->
                println("Remote play command received")
                if (!isPlaying() && currentUrl != null) {
                    play(currentUrl!!)
                }
                // Return 0 for success (MPRemoteCommandHandlerStatusSuccess)
                0L
            }

            // Handle pause command
            remoteCommandCenter.pauseCommand.addTargetWithHandler { _ ->
                println("Remote pause command received")
                if (isPlaying()) {
                    stop()
                }
                // Return 0 for success (MPRemoteCommandHandlerStatusSuccess)
                0L
            }

            println("Remote transport controls set up successfully")
        } catch (e: Exception) {
            println("Error setting up remote transport controls: ${e.message}")
        }
    }

    /**
     * Update the Now Playing Info Center with track information.
     * 
     * @param stationName The name of the station
     * @param stationGenre The genre of the station
     */
    private fun updateNowPlayingInfo(stationName: String, stationGenre: String) {
        try {
            // Create a new dictionary for now playing info
            nowPlayingInfo = mutableMapOf(
                MPMediaItemPropertyTitle to stationName,
                MPMediaItemPropertyArtist to stationGenre,
                MPNowPlayingInfoPropertyPlaybackRate to 1.0,
                MPNowPlayingInfoPropertyIsLiveStream to true
            )

            // Add artwork to the now playing info
            val image = UIImage.imageNamed("lockscreen")
            if (image != null) {
                try {
                    // Create artwork using the constructor
                    val artwork = MPMediaItemArtwork(image = image)
                    nowPlayingInfo[MPMediaItemPropertyArtwork] = artwork
                } catch (e: Exception) {
                    println("Error creating artwork: ${e.message}")
                }
            }

            // Set the now playing info
            MPNowPlayingInfoCenter.defaultCenter().nowPlayingInfo = nowPlayingInfo

            println("Now playing info updated with station: $stationName, genre: $stationGenre")
        } catch (e: Exception) {
            println("Error updating now playing info: ${e.message}")
        }
    }

    /**
     * Update the Now Playing Info Center with default information.
     * This is used when we only have a URL and no station information.
     */
    private fun updateNowPlayingInfo(url: String) {
        updateNowPlayingInfo("Radio Guyana", "Live Stream")
    }

    override fun play(station: StationItem) {
        // Store the current station
        currentStation = station

        // Call the URL-based play method
        play(station.url)
    }

    override fun play(url: String) {
        // Stop any currently playing audio
        stop()

        try {
            // Store the current URL for use with remote commands
            currentUrl = url

            // Configure audio session for background playback
            AudioSessionManager.configureAudioSession()

            // Prepare for background playback (background task)
            AudioSessionHelper.prepareForBackgroundPlayback()

            // Create a new AVPlayer instance
            val nsUrl = NSURL.URLWithString(url)
            if (nsUrl != null) {
                val playerItem = AVPlayerItem.playerItemWithURL(nsUrl)
                player = AVPlayer.playerWithPlayerItem(playerItem)

                // Configure player for background playback
                player?.setAllowsExternalPlayback(true)
                player?.setUsesExternalPlaybackWhileExternalScreenIsActive(true)
                player?.allowsExternalPlayback = true
                player?.allowsAirPlayVideo()

                println("Starting playback with URL: $url")

                // Start playback
                player?.play()
                playing = true

                // Update the Now Playing Info Center
                if (currentStation != null) {
                    updateNowPlayingInfo(currentStation!!.label, currentStation!!.typeAsString())
                } else {
                    updateNowPlayingInfo(url)
                }

                println("Playback started, player state: ${player != null}, playing: $playing")
            } else {
                println("Error: Invalid URL")
                currentUrl = null
            }
        } catch (e: Exception) {
            println("Error setting up audio player: ${e.message}")
            playing = false
            player = null
            currentUrl = null
        }
    }

    override fun stop() {
        try {
            println("Stopping playback")
            player?.pause()
            player = null
            playing = false
            currentUrl = null
            currentStation = null

            // Clear the Now Playing Info Center
            MPNowPlayingInfoCenter.defaultCenter().nowPlayingInfo = null

            // End the background task when playback stops
            AudioSessionHelper.endBackgroundTask()

            // Deactivate the audio session
            AudioSessionManager.deactivateAudioSession()

            println("Playback stopped")
        } catch (e: Exception) {
            println("Error stopping audio: ${e.message}")
        }
    }

    override fun isPlaying(): Boolean {
        // Check if player is null, which means it's definitely not playing
        if (player == null) {
            return false
        }

        // Return the tracked playing state
        return playing
    }
}

/**
 * Get an iOS-specific implementation of the AudioPlayer interface.
 */
actual fun getAudioPlayer(): AudioPlayer = IosAudioPlayer()
