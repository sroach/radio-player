package gy.roach.radio.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import gy.roach.radio.StationItem
import gy.roach.radio.viewmodel.StationViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A screen that demonstrates fetching and displaying stations using the HTTP client.
 * This is an example of how to use the StationViewModel with Compose UI.
 *
 * @param viewModel The ViewModel to use for fetching stations
 * @param apiUrl The URL to fetch stations from
 */
@Preview
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
                style = MaterialTheme.typography.titleLarge
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
            ) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.onError,
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
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = station.label,
                style = MaterialTheme.typography.titleLarge
            )
            
            Text(
                text = station.typeAsString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            Text(
                text = station.url,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun IosCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp)), // iOS typically uses smaller corner radii
        color = Color(0xFFF2F2F7), // iOS light gray background color
        // iOS cards have very subtle shadows
        shadowElevation = 1.dp,
        tonalElevation = 0.dp,
        border = BorderStroke(0.5.dp, Color(0xFFD1D1D6)) // Subtle border
    ) {
        content()
    }
}
