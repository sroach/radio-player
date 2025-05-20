package gy.roach.radio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import gy.roach.radio.ui.IosCard
import gy.roach.radio.visualizers.*
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
    settingsState: SettingsState,
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
                // Album graphic that changes based on selected station
                if (stations.isNotEmpty()) {
                    // Create a random color from a pleasing palette
                    val randomColor = when (selectedStation.index % 5) {
                        0 -> Color(0xFFFF7EB3) // Pink
                        1 -> Color(0xFF7AFCFF) // Cyan
                        2 -> Color(0xFFFEFF9C) // Yellow
                        3 -> Color(0xFFFF9E7A) // Orange
                        else -> Color(0xFF9CFFBA) // Green
                    }.copy(alpha = 0.9f)

                    val random = kotlin.random.Random(selectedStation.index)

                    // Use a Box with gradient background to simulate albumcover.svg
                    Box(
                        modifier = Modifier
                            .size(135.dp) // Reduced by 75% from 180.dp
                            .padding(8.dp)
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                    colors = listOf(
                                        randomColor,
                                        randomColor.copy(red = random.nextFloat(), green = random.nextFloat(), blue = random.nextFloat())
                                    )
                                ),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // Create a column to hold the station name text and add some circles
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Station name text with ellipsis for long names
                            Text(
                                text = selectedStation.label,
                                color = Color.White,
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                maxLines = 2,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )

                            // Add some small circles to simulate the abstract elements in albumcover.svg
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Three small circles with different opacities
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(Color.White.copy(alpha = 0.3f), CircleShape)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(Color.White.copy(alpha = 0.4f), CircleShape)
                                )
                            }
                        }
                    }
                }

                // Header row with title and refresh button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Title
                    Text(
                        text = "Select a Radio Station",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Refresh button
                    IconButton(
                        onClick = { station.refreshStations() },
                        enabled = !isRefreshing
                    ) {
                        if (isRefreshing) {
                            // Show a circular progress indicator when refreshing
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            // Show refresh icon when not refreshing
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh Stations",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
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

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)

                ) {

                    items(filteredStations) { stationItem ->
                        StationItemCard(
                            stationItem = stationItem,
                            isSelected = stationItem.index == selectedStationIndex,
                            isPlaying = isPlaying && stationItem.index == selectedStationIndex,
                            audioPlayer = audioPlayer,
                            settingsState = settingsState,
                            onClick = {
                                onStationSelected(stationItem.index)
                            }
                        )
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
 * Bottom bar with station info and expandable audio spectrum visualization.
 *
 * @param selectedStation The currently selected station
 * @param isPlaying Whether audio is currently playing
 * @param audioPlayer The audio player to use for playback
 * @param settingsState The state object that holds settings preferences
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
    settingsState: SettingsState,
    onNavigateToSettings: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
    onStopPlayback: () -> Unit = {},
    onPlayStation: () -> Unit = {}
) {
    // State to track if the audio spectrum is expanded
    var isSpectrumExpanded by remember { mutableStateOf(false) }

    // Use Box as the root composable to allow proper positioning of all elements
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Column to hold the bottom bar and expandable audio spectrum
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) // Ensure column is aligned to bottom
        ) {
            // Expandable audio spectrum visualization
            AnimatedVisibility(visible = isSpectrumExpanded && isPlaying) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Audio Spectrum",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    SelectedVisualizer(
                        type = settingsState.visualizerType,
                        isPlaying = isPlaying,
                        audioPlayer = audioPlayer,
                        modifier = Modifier.height(150.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Box to position the toggle button above the line
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Subtle line at the top of the bottom bar
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    0.5.dp,
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                )

                // Toggle audio spectrum button with circular background
                if (isPlaying) {
                    Surface(
                        modifier = Modifier
                            .offset(y = (-18).dp) // Position it half above the line
                            .size(36.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                        tonalElevation = 1.dp, // Very subtle elevation for iOS look
                        border = BorderStroke(
                            width = 0.5.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { isSpectrumExpanded = !isSpectrumExpanded }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // Horizontal line/pill shape instead of chevron
                            Box(
                                modifier = Modifier
                                    .width(16.dp)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                }
            }

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

                    // The toggle button has been moved above the line

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

                    // Settings button
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
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
}
