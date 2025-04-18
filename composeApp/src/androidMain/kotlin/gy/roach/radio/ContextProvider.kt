package gy.roach.radio

import android.content.Context
import android.app.Application
import android.content.ContextWrapper

/**
 * A singleton class that provides access to the application context.
 * This allows non-composable functions to access the context without
 * relying on Compose's LocalContext.
 */
object ContextProvider {
    private var applicationContext: Context? = null

    /**
     * Initializes the context provider with the application context.
     * This should be called when the application starts, typically from MainActivity.
     *
     * @param context The application context
     */
    fun initialize(context: Context) {
        if (applicationContext == null) {
            // Store the application context to avoid memory leaks
            applicationContext = context.applicationContext
        }
    }

    /**
     * Gets the application context.
     *
     * @return The application context, or null if not initialized
     */
    fun getContext(): Context? {
        return applicationContext
    }
}