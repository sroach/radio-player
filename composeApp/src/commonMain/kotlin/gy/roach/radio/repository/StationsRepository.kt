package gy.roach.radio.repository

import gy.roach.radio.StationItem
import gy.roach.radio.config.ApiConfig
import gy.roach.radio.config.ApiConfigFactory
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

/**
 * Repository for fetching station data from the API.
 */
class StationsRepository {
    private val apiConfig: ApiConfig = ApiConfigFactory.create()
    
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }
    
    /**
     * Fetches the list of stations from the API.
     * 
     * @return A list of StationItem objects
     */
    suspend fun getStations(): List<StationItem> = withContext(Dispatchers.Default) {
        try {
            val response = client.get(apiConfig.getStationsApiUrl()) {
                header("X-API-Key", apiConfig.getStationsApiKey())
            }
            response.body<List<StationItem>>()
        } catch (e: Exception) {
            println("Error fetching stations: ${e.message}")
            // Return empty list if there's an error
            emptyList()
        }
    }
    
    /**
     * Closes the HTTP client.
     */
    fun close() {
        client.close()
    }
}