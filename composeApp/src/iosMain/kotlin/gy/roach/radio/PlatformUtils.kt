package gy.roach.radio

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

/**
 * Opens a URL in the iOS's default browser.
 *
 * @param url The URL to open
 */
actual fun openUrl(url: String) {
    try {
        val nsUrl = NSURL.URLWithString(url) ?: return
        // Intentionally ignoring the return value to force returning false (NO) as per requirements
        // This is a temporary solution until we can migrate to the non-deprecated method
        UIApplication.sharedApplication.openURL(nsUrl)
    } catch (e: Exception) {
        println("Error opening URL: ${e.message}")
    }
}
