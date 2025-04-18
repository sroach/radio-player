package gy.roach.radio

import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.stringWithFormat
import platform.UIKit.UIApplication
import platform.UIKit.UIBackgroundTaskInvalid
import platform.UIKit.UIBackgroundTaskIdentifier

/**
 * Helper class to provide additional configuration for iOS audio playback.
 * This is a simplified version that doesn't rely on direct AVAudioSession access.
 */
class AudioSessionHelper {
    companion object {
        // Background task identifier
        private var backgroundTaskId: UIBackgroundTaskIdentifier = UIBackgroundTaskInvalid
        /**
         * Prepare the app for background audio playback.
         * This should be called before starting playback.
         */
        fun prepareForBackgroundPlayback() {
            println("Preparing for background audio playback")

            // Log that we're attempting to configure for background playback
            val infoPlist = NSBundle.mainBundle.infoDictionary
            val hasBackgroundModes = infoPlist?.containsKey("UIBackgroundModes") ?: false

            println("Info.plist has UIBackgroundModes: $hasBackgroundModes")

            if (hasBackgroundModes) {
                val modes = infoPlist?.get("UIBackgroundModes")
                println("Background modes: $modes")

                // Check if audio mode is included
                val modesString = modes.toString()
                if (!modesString.contains("audio")) {
                    println("WARNING: UIBackgroundModes does not include 'audio' mode. Background playback may not work.")
                    println("Add <string>audio</string> to the UIBackgroundModes array in Info.plist.")
                }
            } else {
                println("WARNING: UIBackgroundModes not found in Info.plist. Background playback will not work.")
                println("Add the following to Info.plist:")
                println("<key>UIBackgroundModes</key>")
                println("<array>")
                println("    <string>audio</string>")
                println("</array>")
            }

            // Additional logging to help with debugging
            println("App Bundle ID: ${NSBundle.mainBundle.bundleIdentifier}")

            // Begin a background task to help with background playback
            beginBackgroundTask()
        }

        /**
         * Begin a background task to help with background playback.
         * This gives the app additional time to run in the background.
         */
        fun beginBackgroundTask() {
            // End any existing background task
            endBackgroundTask()

            println("Beginning background task for audio playback")

            // Start a new background task
            backgroundTaskId = UIApplication.sharedApplication.beginBackgroundTaskWithExpirationHandler {
                // This closure is called when the background task is about to expire
                println("Background task is expiring")
                endBackgroundTask()
            }

            if (backgroundTaskId == UIBackgroundTaskInvalid) {
                println("Failed to start background task")
            } else {
                println("Background task started with ID: $backgroundTaskId")
            }
        }

        /**
         * End the current background task if one exists.
         */
        fun endBackgroundTask() {
            if (backgroundTaskId != UIBackgroundTaskInvalid) {
                println("Ending background task with ID: $backgroundTaskId")
                UIApplication.sharedApplication.endBackgroundTask(backgroundTaskId)
                backgroundTaskId = UIBackgroundTaskInvalid
            }
        }
    }
}
