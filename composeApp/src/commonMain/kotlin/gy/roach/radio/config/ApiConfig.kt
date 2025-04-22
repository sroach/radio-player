package gy.roach.radio.config

/**
 * Interface for accessing API configuration values.
 */
interface ApiConfig {
    /**
     * Get the API key for the stations API.
     */
    fun getStationsApiKey(): String
    
    /**
     * Get the base URL for the stations API.
     */
    fun getStationsApiUrl(): String
    
    companion object {
        /**
         * Default URL for the stations API.
         */
        const val DEFAULT_STATIONS_API_URL = "https://roach.gy/steve/api/stations/stations.json"
    }
}

/**
 * Factory for creating platform-specific ApiConfig instances.
 */
expect object ApiConfigFactory {
    /**
     * Create a platform-specific ApiConfig instance.
     */
    fun create(): ApiConfig
}