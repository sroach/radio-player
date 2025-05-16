package gy.roach.radio.http

import gy.roach.radio.StationItem
import kotlinx.serialization.Serializable

/**
 * Service class for fetching radio station data from a remote API.
 * This is an example of how to use the ApiClient for a specific use case.
 */
class StationService(private val apiClient: ApiClient = ApiClient()) {
    
    /**
     * Fetches a list of radio stations from a remote API.
     * 
     * @param url The URL to fetch the stations from
     * @return A list of StationItem objects
     */
    suspend fun fetchStations(url: String): List<StationItem> {
        return apiClient.get<StationResponse>(url).stations
    }
    
    /**
     * Adds a new radio station to the remote API.
     * 
     * @param url The URL to post the new station to
     * @param station The station to add
     * @return The added station with any server-side modifications
     */
    suspend fun addStation(url: String, station: StationItem): StationItem {
        return apiClient.post<StationItem, StationItem>(url, station)
    }
}

/**
 * Data class for deserializing the response from the stations API.
 */
@Serializable
data class StationResponse(
    val stations: List<StationItem>
)