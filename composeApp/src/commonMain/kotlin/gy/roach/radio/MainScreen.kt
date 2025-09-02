package gy.roach.radio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Main screen of the app showing the radio stations list.
 *
 * @param audioPlayer The audio player to use for playback
 * @param selectedStationIndex The index of the currently selected station
 * @param isPlaying Whether audio is currently playing
 * @param settingsState The state object that holds settings preferences
 * @param onStationSelected Callback when a station is selected
 * @param onPlayingStateChanged Callback when the playing state changes
 * @param onNavigateToAbout Callback to navigate to the about screen
 * @param onNavigateToSettings Callback to navigate to the settings screen
 */

@Preview
@Composable
fun MainScreen(
    audioPlayer: AudioPlayer,
    selectedStationIndex: Int = 0,
    isPlaying: Boolean = false,
    onStationSelected: (Int) -> Unit = {},
    onPlayingStateChanged: (Boolean) -> Unit = {},
    onNavigateToAbout: () -> Unit,
    onNavigateToSettings: () -> Unit
) {

    val station = remember { Station() }

    // Collect the isRefreshing state
    val isRefreshing by station.isRefreshing.collectAsState()

    // Collect the stations flow to ensure UI updates when stations change
    val stations by station.stationsFlow.collectAsState()

    // Ensure selectedStation is updated when stations change
    val selectedStation = remember(selectedStationIndex, stations) { 
        stations.getOrNull(selectedStationIndex) ?: stations.firstOrNull() ?: station.item(selectedStationIndex)
    }

    // Extract unique station types
    val allStationTypes = remember(stations) {
        stations.flatMap { it.type }.map { it.trim().lowercase() }.distinct().sorted()
    }

    // State for selected station type filters
    var selectedStationTypes by remember { mutableStateOf<Set<String>>(emptySet()) }

    // Filter stations based on selected types
    val filteredStations = remember(selectedStationTypes, stations) {
        if (selectedStationTypes.isEmpty()) {
            stations
        } else {
            stations.filter { stationItem ->
                stationItem.type.any { type -> 
                    selectedStationTypes.contains(type.trim().lowercase())
                }
            }
        }
    }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        // Main content
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            // Display a side-by-side view with stations and image
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                // Header spacing
                Spacer(modifier = Modifier.height(0.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)

                ) {

                    items(filteredStations) { stationItem ->
                        StationItemCard(
                            stationItem = stationItem,
                            isSelected = stationItem.index == selectedStationIndex,
                            isPlaying = isPlaying && stationItem.index == selectedStationIndex,
                            onClick = {
                                onStationSelected(stationItem.index)
                            }
                        )
                    }
                }

                // Station type filter chips
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Filter by Type:",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // Scrollable row of filter chips
                    androidx.compose.foundation.lazy.LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // "All" filter chip
                        item {
                            StationTypeChip(
                                text = "All",
                                selected = selectedStationTypes.isEmpty(),
                                onClick = { selectedStationTypes = emptySet() }
                            )
                        }

                        // Type filter chips
                        items(allStationTypes) { type ->
                            StationTypeChip(
                                text = type.replaceFirstChar { it.uppercase() },
                                selected = selectedStationTypes.contains(type),
                                onClick = {
                                    selectedStationTypes = if (selectedStationTypes.contains(type)) {
                                        selectedStationTypes - type
                                    } else {
                                        selectedStationTypes + type
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Custom chip component for station type filtering.
 *
 * @param text The text to display in the chip
 * @param selected Whether the chip is selected
 * @param onClick Callback when the chip is clicked
 */
@Composable
fun StationTypeChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

/**
 * Bottom bar with station info and controls.
 *
 * @param selectedStation The currently selected station
 * @param isPlaying Whether audio is currently playing
 * @param audioPlayer The audio player to use for playback
 * @param onNavigateToSettings Callback to navigate to settings screen
 * @param onNavigateToAbout Callback to navigate to about screen
 * @param onStopPlayback Callback to stop the currently playing station
 * @param onPlayStation Callback to play the selected station
 */
@Composable
fun MainBottomBar(
    selectedStation: StationItem,
    isPlaying: Boolean,
    audioPlayer: AudioPlayer,
    onNavigateToSettings: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
    onStopPlayback: () -> Unit = {},
    onPlayStation: () -> Unit = {},
    isRefreshing: Boolean = false,
    onRefreshStations: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Subtle line at the top of the bottom bar
        HorizontalDivider(
            Modifier.fillMaxWidth(),
            0.5.dp,
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )

        // Bottom app bar
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Display selected station info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = selectedStation.label,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = selectedStation.typeAsString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                }

                // Play/Stop button
                if (isPlaying) {
                    // Stop button - visible when a station is playing
                    IconButton(
                        onClick = onStopPlayback,
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.StopCircle,
                            contentDescription = "Stop",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                } else {
                    // Play button - visible when no station is playing
                    IconButton(
                        onClick = onPlayStation,
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PlayCircle,
                            contentDescription = "Play",
                            tint = Color(0xFF34C759) // iOS system green
                        )
                    }
                }

                IconButton(onClick = onNavigateToSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Refresh button (moved to bottom bar, after play/stop)
                IconButton(
                    onClick = onRefreshStations,
                    enabled = !isRefreshing,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    if (isRefreshing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Stations",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // About button
                IconButton(onClick = onNavigateToAbout) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "About",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

