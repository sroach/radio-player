package gy.roach.radio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gy.roach.radio.StationItem
import gy.roach.radio.http.StationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing radio station data.
 * This demonstrates how to use the StationService with the HTTP client.
 */
class StationViewModel(
    private val stationService: StationService = StationService()
) : ViewModel() {
    
    // State for stations
    private val _stations = MutableStateFlow<List<StationItem>>(emptyList())
    val stations: StateFlow<List<StationItem>> = _stations.asStateFlow()
    
    // State for loading status
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // State for error messages
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    /**
     * Fetches stations from a remote API.
     * 
     * @param url The URL to fetch stations from
     */
    fun fetchStations(url: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                // Fetch stations using the StationService
                val fetchedStations = stationService.fetchStations(url)
                _stations.value = fetchedStations
                
            } catch (e: Exception) {
                _error.value = "Failed to fetch stations: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Adds a new station to the remote API.
     * 
     * @param url The URL to post the new station to
     * @param station The station to add
     */
    fun addStation(url: String, station: StationItem) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                // Add station using the StationService
                val addedStation = stationService.addStation(url, station)
                
                // Update the stations list with the new station
                val currentStations = _stations.value.toMutableList()
                currentStations.add(addedStation)
                _stations.value = currentStations
                
            } catch (e: Exception) {
                _error.value = "Failed to add station: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}