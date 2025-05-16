package gy.roach.radio.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gy.roach.radio.StationItem
import gy.roach.radio.viewmodel.StationViewModel

/**
 * A screen that demonstrates fetching and displaying stations using the HTTP client.
 * This is an example of how to use the StationViewModel with Compose UI.
 *
 * @param viewModel The ViewModel to use for fetching stations
 * @param apiUrl The URL to fetch stations from
 */
@Composable
fun StationScreen(
    viewModel: StationViewModel = remember { StationViewModel() },
    apiUrl: String = "https://api.example.com/stations" // Replace with your actual API URL
) {
    // Collect state from the ViewModel
    val stations by viewModel.stations.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    // Effect to fetch stations when the screen is first displayed
    LaunchedEffect(apiUrl) {
        viewModel.fetchStations(apiUrl)
    }
    
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with refresh button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Radio Stations",
                style = MaterialTheme.typography.h6
            )
            
            IconButton(
                onClick = { viewModel.fetchStations(apiUrl) },
                enabled = !isLoading
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh stations"
                )
            }
        }
        
        // Loading indicator
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        
        // Error message
        error?.let { errorMessage ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.error
            ) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colors.onError,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        
        // Stations list
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(stations) { station ->
                StationCard(station = station)
            }
        }
    }
}

/**
 * A card that displays information about a radio station.
 *
 * @param station The station to display
 */
@Composable
fun StationCard(station: StationItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = station.label,
                style = MaterialTheme.typography.h6
            )
            
            Text(
                text = station.typeAsString(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            )
            
            Text(
                text = station.url,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}