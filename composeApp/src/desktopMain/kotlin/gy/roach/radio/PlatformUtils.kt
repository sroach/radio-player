package gy.roach.radio

import java.awt.Desktop
import java.net.URI

/**
 * Opens a URL in the desktop's default browser.
 *
 * @param url The URL to open
 */
actual fun openUrl(url: String) {
    try {
        val uri = URI(url)
        val desktop = Desktop.getDesktop()
        desktop.browse(uri)
    } catch (e: Exception) {
        println("Error opening URL: ${e.message}")
    }
}