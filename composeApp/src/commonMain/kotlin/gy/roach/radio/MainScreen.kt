package gy.roach.radio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.PlayCircle

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    val selectedStation = remember(selectedStationIndex) { station.item(selectedStationIndex) }

    // Collect the isRefreshing state
    val isRefreshing by station.isRefreshing.collectAsState()

    // Extract unique station types
    val allStationTypes = remember {
        station.array.flatMap { it.type }.map { it.trim().lowercase() }.distinct().sorted()
    }

    // State for selected station type filters
    var selectedStationTypes by remember { mutableStateOf<Set<String>>(emptySet()) }

    // Filter stations based on selected types
    val filteredStations = remember(selectedStationTypes) {
        if (selectedStationTypes.isEmpty()) {
            station.array
        } else {
            station.array.filter { stationItem ->
                stationItem.type.any { type -> 
                    selectedStationTypes.contains(type.trim().lowercase())
                }
            }
        }
    }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        remember { Greeting().greet() }

        // Main content
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            // Display a side-by-side view with stations and image
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header row with title and refresh button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Title
                    Text(
                        text = "Select a Radio Station",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface
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
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                }

                // Station type filter chips
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Filter by Type:",
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredStations) { stationItem ->
                        StationItemCard(
                            stationItem = stationItem,
                            isSelected = stationItem.index == selectedStationIndex,
                            isPlaying = isPlaying && stationItem.index == selectedStationIndex,
                            audioPlayer = audioPlayer,
                            onClick = {
                                onStationSelected(stationItem.index)
                            },
                            onPlayPauseClick = {
                                // First select the station if it's not already selected
                                if (stationItem.index != selectedStationIndex) {
                                    onStationSelected(stationItem.index)
                                }

                                // Then toggle play/pause
                                try {
                                    if (isPlaying && stationItem.index == selectedStationIndex) {
                                        audioPlayer.stop()
                                        onPlayingStateChanged(false)
                                    } else {
                                        // Make sure we have a valid URL
                                        if (stationItem.url.isNotBlank()) {
                                            audioPlayer.play(stationItem.url)
                                            onPlayingStateChanged(true)
                                        } else {
                                            println("Error: Station URL is blank")
                                        }
                                    }
                                } catch (e: Exception) {
                                    println("Error toggling playback: ${e.message}")
                                    // Reset state to ensure UI is consistent
                                    onPlayingStateChanged(audioPlayer.isPlaying())
                                }
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
        color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        contentColor = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
        elevation = if (selected) 4.dp else 1.dp,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

/**
 * Bottom bar with station info and equalizer visualizer.
 *
 * @param selectedStation The currently selected station
 * @param isPlaying Whether audio is currently playing
 * @param audioPlayer The audio player to use for playback
 * @param settingsState The state object that holds settings preferences
 * @param onNavigateToSettings Callback to navigate to settings screen
 * @param onNavigateToAbout Callback to navigate to about screen
 */
@Composable
fun MainBottomBar(
    selectedStation: StationItem,
    isPlaying: Boolean,
    audioPlayer: AudioPlayer,
    settingsState: SettingsState,
    onNavigateToSettings: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {}
) {
    // State to control equalizer visibility
    var isEqualizerVisible by remember { mutableStateOf(false) }

    // Use Box as the root composable to allow proper positioning of all elements
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Equalizer button - positioned behind the bottom bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align to bottom center
                .offset(y = if (isEqualizerVisible && isPlaying) (-235).dp else (-45).dp) // Position relative to the bottom bar
                .size(width = 60.dp, height = 40.dp) // Wider for rectangle shape
                .clip(RoundedCornerShape(12.dp)) // Rounded rectangle instead of circle
                .background(MaterialTheme.colors.surface) // Match bottom bar color
                .clickable { isEqualizerVisible = !isEqualizerVisible },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isEqualizerVisible) "v" else "^", // Caret symbol for down/up
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.button.copy(
                    fontSize = 18.sp
                )
            )
        }

        // Column to hold the bottom bar and equalizer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) // Ensure column is aligned to bottom
        ) {
            // Show equalizer when visible and playing
            AnimatedVisibility(visible = isEqualizerVisible && isPlaying) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Audio Spectrum",
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    SelectedVisualizer(
                        type = settingsState.visualizerType,
                        isPlaying = isPlaying,
                        audioPlayer = audioPlayer,
                        modifier = Modifier.height(150.dp)
                    )


                }
            }

            // Bottom app bar
            BottomAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 8.dp,
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
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            text = selectedStation.typeAsString(),
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    // Settings button
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }

                    // About button
                    IconButton(onClick = onNavigateToAbout) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
        }
    }
}
