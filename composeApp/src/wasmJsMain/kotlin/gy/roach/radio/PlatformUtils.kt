package gy.roach.radio

import kotlinx.browser.window

/**
 * Opens a URL in the browser's new tab.
 *
 * @param url The URL to open
 */
actual fun openUrl(url: String) {
    try {
        window.open(url, "_blank")
    } catch (e: Exception) {
        println("Error opening URL: ${e.message}")
    }
}