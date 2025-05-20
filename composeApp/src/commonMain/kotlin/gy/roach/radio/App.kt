package gy.roach.radio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import radio_guyana_player.composeapp.generated.resources.*

@Preview
@Composable
fun App(themeState: ThemeState? = null) = AppTheme {

    // Use provided theme state or get the current one from composition
    val currentThemeState = themeState ?: rememberThemeState()

    // Navigation state
    val navigationState = rememberNavigationState()

    // Settings state
    val settingsState = rememberSettingsState()

    val isDark = currentThemeState.isDarkTheme

    val icon = remember(isDark) {
        if (isDark) Res.drawable.ic_light_mode
        else Res.drawable.ic_dark_mode
    }

    // We still need the theme here for the preview and other platforms
    RadioGuyanaTheme(darkTheme = currentThemeState.isDarkTheme) {
        // Audio player state
        val audioPlayer = remember { getAudioPlayer() }
        var isPlaying by remember { mutableStateOf(false) }
        var selectedStationIndex by remember { mutableStateOf(0) }
        val station = remember { Station() }
        val selectedStation = remember(selectedStationIndex) { station.item(selectedStationIndex) }

        // Update isPlaying state when the component is recomposed or when selectedStationIndex changes
        LaunchedEffect(Unit, selectedStationIndex) {
            try {
                isPlaying = audioPlayer.isPlaying()
            } catch (e: Exception) {
                println("Error checking isPlaying: ${e.message}")
                isPlaying = false
            }
        }

        Scaffold(
            topBar = {
                when (navigationState.currentScreen) {
                    is Screen.Splash -> {
                        // No top bar for splash screen
                    }
                    is Screen.Main -> {

                        Surface(
                            color = MaterialTheme.colorScheme.background,
                            tonalElevation = 3.dp
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Image(
                                    painter = painterResource(Res.drawable.flag_gy),
                                    contentDescription = "Flag",
                                )
                                // Application title text
                                Text(
                                    text = "Guyanese Internet Radio",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                // Theme toggle
                                IconButton(
                                    onClick = { currentThemeState.toggleTheme()},
                                    colors = IconButtonDefaults.iconButtonColors(
                                        contentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)


                                    )
                                ) {

                                    Icon(
                                        imageVector = vectorResource(icon),
                                        contentDescription = stringResource(Res.string.theme)
                                    )
                                }
                            }
                        }

                    }
                    is Screen.About -> {
                        AboutTopBar(onNavigateToMain = { navigationState.navigateToMain() })
                    }
                    is Screen.Settings -> {
                        SettingsTopBar(onNavigateToMain = { navigationState.navigateToMain() })
                    }
                    is Screen.ReleaseNotes -> {
                        ReleaseNotesTopBar(onNavigateToAbout = { navigationState.navigateToAbout() })
                    }
                }
            },
            bottomBar = {
                when (navigationState.currentScreen) {
                    is Screen.Splash -> {
                        // No bottom bar for splash screen
                    }
                    else -> {
                        // Show bottom bar on all screens except splash
                        MainBottomBar(
                            selectedStation = selectedStation,
                            isPlaying = isPlaying,
                            audioPlayer = audioPlayer,
                            settingsState = settingsState,
                            onNavigateToSettings = { navigationState.navigateToSettings() },
                            onNavigateToAbout = { navigationState.navigateToAbout() },
                            onStopPlayback = {
                                // Stop the currently playing station
                                audioPlayer.stop()
                                isPlaying = false
                            },
                            onPlayStation = {
                                // Play the selected station
                                if (selectedStation.url.isNotBlank()) {
                                    audioPlayer.play(selectedStation)
                                    isPlaying = true
                                } else {
                                    println("Error: Station URL is blank")
                                }
                            }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                when (navigationState.currentScreen) {
                    is Screen.Splash -> {
                        SplashScreen(
                            onSplashFinished = { navigationState.navigateToMain() }
                        )
                    }
                    is Screen.Main -> {
                        MainScreen(
                            audioPlayer = audioPlayer,
                            selectedStationIndex = selectedStationIndex,
                            isPlaying = isPlaying,
                            settingsState = settingsState,
                            onStationSelected = { newIndex ->
                                // If selecting a different station while one is playing,
                                // stop the current one first
                                if (isPlaying && selectedStationIndex != newIndex) {
                                    audioPlayer.stop()
                                    isPlaying = false
                                }
                                selectedStationIndex = newIndex
                            },
                            onPlayingStateChanged = { newIsPlaying ->
                                isPlaying = newIsPlaying
                            },
                            onNavigateToAbout = { navigationState.navigateToAbout() },
                            onNavigateToSettings = { navigationState.navigateToSettings() }
                        )
                    }
                    is Screen.About -> {
                        AboutScreen(
                            onNavigateToMain = { navigationState.navigateToMain() },
                            onNavigateToReleaseNotes = { navigationState.navigateToReleaseNotes() }
                        )
                    }
                    is Screen.Settings -> {
                        SettingsScreen(
                            settingsState = settingsState,
                            onNavigateToMain = { navigationState.navigateToMain() }
                        )
                    }
                    is Screen.ReleaseNotes -> {
                        ReleaseNotesScreen(
                            onNavigateToAbout = { navigationState.navigateToAbout() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StationItemCard(
    stationItem: StationItem,
    isSelected: Boolean = false,
    isPlaying: Boolean = false,
    audioPlayer: AudioPlayer? = null,
    settingsState: SettingsState? = null,
    onClick: () -> Unit = {}
) {
    // iOS-native card with subtle styling and lighter elevation
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp) // Reduced vertical padding to decrease space between cards
            .clickable {
                // Perform the onClick action
                onClick()
            },

        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp), // Lighter elevation for iOS look
        shape = RoundedCornerShape(8.dp), // iOS typically uses 8-10dp corner radius
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F2F7) // iOS systemGray6 color for background
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 0.5.dp,
            color = Color(0xFFE5E5EA) // iOS light gray border color
        )
    ) {
        // Station information layout with left accent bar for selection
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = if (isSelected) 0.dp else 18.dp,
                    top = 18.dp,
                    end = 18.dp,
                    bottom = 18.dp
                ), // Padding on all sides except left when selected
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // Changed from SpaceBetween to Start for better control
        ) {
            // Left section with accent bar, station number and info
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth() // Use full width now that play button is removed
            ) {
                // Left accent bar for selection
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(56.dp) // Match the height of the row content
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(14.dp)) // Add spacing after the accent bar
                }

                // Create a random color from a pleasing palette (same as album cover)
                val circleColor = when (stationItem.index % 5) {
                    0 -> Color(0xFFFF7EB3) // Pink
                    1 -> Color(0xFF7AFCFF) // Cyan
                    2 -> Color(0xFFFEFF9C) // Yellow
                    3 -> Color(0xFFFF9E7A) // Orange
                    else -> Color(0xFF9CFFBA) // Green
                }.copy(alpha = 0.9f)

                // Display the item number inside a circle with the album cover color
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp) // iOS standard icon size
                        .clip(CircleShape)
                        .background(circleColor)
                ) {
                    Text(
                        text = "${stationItem.index + 1}",
                        color = Color.Black.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.width(12.dp)) // iOS uses tighter spacing

                // Display the station's information
                Column(
                    modifier = Modifier.weight(1f) // Give the text column a weight to control its space
                ) {
                    // Title with SF Pro text styling - no more maxLines limit
                    Text(
                        text = stationItem.label,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Medium // SF Pro typically uses Medium weight for headings
                        ), 
                        color = Color(0xFF000000) // iOS uses pure black for primary text
                    )

                    Spacer(modifier = Modifier.height(2.dp)) // iOS uses tighter spacing

                    // Subtitle with SF Pro text styling - no more maxLines limit
                    Text(
                        text = stationItem.typeAsString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal // SF Pro uses Normal weight for body text
                        ),
                        color = Color(0xFF8E8E93) // iOS systemGray color for secondary text
                    )
                }
            }
        }
    }
}
