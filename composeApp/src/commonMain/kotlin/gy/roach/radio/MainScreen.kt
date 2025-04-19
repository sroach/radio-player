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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
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
    onNavigateToAbout: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val station = remember { Station() }
    val selectedStation = remember(selectedStationIndex) { station.item(selectedStationIndex) }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        remember { Greeting().greet() }

        // Main content
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            // Display a side-by-side view with stations and image
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Single column scrollable list of stations
                Text(
                    text = "Select a Radio Station",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(station.array) { stationItem ->
                        StationItemCard(
                            stationItem = stationItem,
                            isSelected = stationItem.index == selectedStationIndex,
                            isPlaying = isPlaying && stationItem.index == selectedStationIndex,
                            audioPlayer = audioPlayer,
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
 * Bottom bar with play/stop control, navigation, and equalizer visualizer.
 *
 * @param selectedStation The currently selected station
 * @param isPlaying Whether audio is currently playing
 * @param audioPlayer The audio player to use for playback
 * @param settingsState The state object that holds settings preferences
 * @param onPlayPauseClick Callback when the play/pause button is clicked
 */
@Composable
fun MainBottomBar(
    selectedStation: StationItem,
    isPlaying: Boolean,
    audioPlayer: AudioPlayer,
    settingsState: SettingsState,
    onPlayPauseClick: () -> Unit
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
                            text = selectedStation.type,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    // Play/Stop button - iOS style
                    // Define iOS system green color for play button
                    val iosGreen = Color(0xFF34C759) // iOS system green
                    val iosRed = MaterialTheme.colors.error // Using the iOS red from theme

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(44.dp) // iOS standard control size
                            .background(if (isPlaying) iosRed else iosGreen)
                            .clickable(onClick = onPlayPauseClick),
                        contentAlignment = Alignment.Center
                    ) {
                        if(isPlaying) {
                            Icon(
                                imageVector = Icons.Filled.StopCircle,
                                contentDescription = "Play Arrow Icon",
                                tint = MaterialTheme.colors.onSurface
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Rounded.PlayCircle,
                                contentDescription = "Done Icon",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }


                    }
                }
            }
        }
    }
}
