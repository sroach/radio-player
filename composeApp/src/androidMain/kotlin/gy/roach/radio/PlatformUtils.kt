package gy.roach.radio

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Opens a URL in the Android's default browser.
 *
 * @param url The URL to open
 */
actual fun openUrl(url: String) {
    try {
        val context = ContextProvider.getContext()
        if (context != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } else {
            println("Error opening URL: Context is null")
        }
    } catch (e: Exception) {
        println("Error opening URL: ${e.message}")
    }
}
