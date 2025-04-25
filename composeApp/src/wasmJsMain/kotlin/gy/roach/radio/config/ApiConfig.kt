package gy.roach.radio.config

/**
 * Gets the APP_CONFIG object from window
 */
private fun getAppConfigJs(): String = js("JSON.stringify(window.APP_CONFIG || {})")

private fun getAppConfig(): Any? {
    val jsonString = getAppConfigJs()
    return try {
        kotlinx.serialization.json.Json.parseToJsonElement(jsonString)
    } catch (e: Exception) {
        null
    }
}

/**
 * Gets the STATIONS_API_KEY from APP_CONFIG
 */
private fun getStationsApiKeyJs(): String = js("window.APP_CONFIG && window.APP_CONFIG.STATIONS_API_KEY ? window.APP_CONFIG.STATIONS_API_KEY : ''")

private fun getStationsApiKeyFromConfig(): String? {
    val apiKey = getStationsApiKeyJs()
    return if (apiKey.isNotEmpty()) apiKey else null
}

/**
 * Logs an error to the console
 */
private fun logErrorToConsoleJs(message: String): Unit = js("console.error(message)")

private fun logErrorToConsole(message: String) {
    logErrorToConsoleJs(message)
}

/**
 * Web implementation of ApiConfig.
 * This implementation reads the API key from a configuration file that is generated during build.
 * 
 * In a real production application, you would use more robust security measures like:
 * - Server-side authentication and API proxying
 * - Environment variables in your deployment environment
 * - Proper key management services
 * 
 * For client-side web applications, consider these best practices:
 * 1. Never expose sensitive API keys directly in client-side code
 * 2. Use server-side proxies to make API calls that require authentication
 * 3. For development, use environment variables with your build system
 * 4. For production, consider using a backend service to handle API requests
 */
class WebApiConfig : ApiConfig {
    // Read the API key from the config.js file that is generated during build
    private val apiKey = getApiKeyFromConfig()

    override fun getStationsApiKey(): String {
        return apiKey
    }

    override fun getStationsApiUrl(): String {
        return ApiConfig.DEFAULT_STATIONS_API_URL
    }

    /**
     * Gets the API key from the config.js file.
     * The config.js file is generated during build and contains the API key.
     */
    private fun getApiKeyFromConfig(): String {
        try {
            // Access the global APP_CONFIG object that is defined in config.js
            val appConfig = getAppConfig()
            if (appConfig != null) {
                val apiKey = getStationsApiKeyFromConfig()
                if (apiKey != null && apiKey.isNotEmpty()) {
                    return apiKey
                }
            }
        } catch (e: Exception) {
            // Log error but don't expose details
            logErrorToConsole("Error reading API key from config")
        }

        // Return empty string if config is not available or API key is not set
        return ""
    }
}

/**
 * Factory implementation for Web platform.
 */
actual object ApiConfigFactory {
    actual fun create(): ApiConfig = WebApiConfig()
}
