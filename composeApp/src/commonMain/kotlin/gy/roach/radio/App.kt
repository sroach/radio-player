package gy.roach.radio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import radio_guyana_player.composeapp.generated.resources.Res
import radio_guyana_player.composeapp.generated.resources.favicon
import radio_guyana_player.composeapp.generated.resources.flag_gy
import radio_guyana_player.composeapp.generated.resources.ic_dark_mode
import radio_guyana_player.composeapp.generated.resources.ic_light_mode
import radio_guyana_player.composeapp.generated.resources.radio_icon
import radio_guyana_player.composeapp.generated.resources.theme

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
                            color = MaterialTheme.colors.background,
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
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onSurface
                                )
                                // Theme toggle
                                IconButton(
                                    onClick = { currentThemeState.toggleTheme()},
                                    colors = IconButtonDefaults.iconButtonColors(
                                        contentColor = MaterialTheme.colors.primary.copy(alpha = 0.85f)


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
                            onNavigateToAbout = { navigationState.navigateToAbout() }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Surface(
                color = MaterialTheme.colors.background,
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
    onClick: () -> Unit = {},
    onPlayPauseClick: () -> Unit = {}
) {
    // Define iOS system green color for play button
    val iosGreen = Color(0xFF34C759) // iOS system green
    val iosRed = MaterialTheme.colors.error // Using the iOS red from theme

    // State to track if the card is expanded
    var isExpanded by remember { mutableStateOf(false) }

    // iOS-inspired card with subtle styling and elevation
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp) // iOS uses more horizontal padding
            .clickable {
                // Toggle expanded state on click
                isExpanded = !isExpanded
                // Perform the original onClick action
                onClick()
            },
        elevation = 4.dp, // Added elevation as requested
        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp), // iOS typically uses 10dp corner radius
        backgroundColor = if (isSelected) 
            MaterialTheme.colors.primary.copy(alpha = 0.05f) // Very subtle selection highlight for iOS
        else 
            MaterialTheme.colors.surface,
        border = androidx.compose.foundation.BorderStroke(
            width = 0.5.dp, 
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f) // Very subtle border typical of iOS
        )
    ) {
        Column {
            // Station information layout
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // iOS standard padding
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                        style = MaterialTheme.typography.h6
                    )
                }

                Spacer(modifier = Modifier.width(12.dp)) // iOS uses tighter spacing

                // Display the station's information
                Column {
                    // Title with iOS typography
                    Text(
                        text = stationItem.label,
                        style = MaterialTheme.typography.h5, // Using iOS-inspired typography from Theme.kt
                        color = if (isSelected) 
                            MaterialTheme.colors.primary 
                        else 
                            MaterialTheme.colors.onSurface
                    )

                    Spacer(modifier = Modifier.height(2.dp)) // iOS uses tighter spacing

                    // Subtitle with iOS styling
                    Text(
                        text = stationItem.typeAsString(),
                        style = MaterialTheme.typography.body2, // Using iOS-inspired typography from Theme.kt
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f) // iOS uses slightly more transparent secondary text
                    )
                }
            }

            // Expanded player view
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Music-related placeholder image with randomized color theme
                    // Generate a deterministic random color based on the station index
                    val random = kotlin.random.Random(stationItem.index)

                    // Create a random color from a pleasing palette
                    val randomColor = when (stationItem.index % 5) {
                        0 -> Color(0xFFFF7EB3) // Pink
                        1 -> Color(0xFF7AFCFF) // Cyan
                        2 -> Color(0xFFFEFF9C) // Yellow
                        3 -> Color(0xFFFF9E7A) // Orange
                        else -> Color(0xFF9CFFBA) // Green
                    }.copy(alpha = 0.9f)

                    // Use a Box with gradient background to simulate albumcover.svg
                    Box(
                        modifier = Modifier
                            .size(180.dp) // Increased size from 120.dp to 180.dp
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
                                text = stationItem.label,
                                color = Color.White,
                                style = MaterialTheme.typography.h5,
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

                    Spacer(modifier = Modifier.height(8.dp))

                    // Play/Stop button in the expanded view
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(56.dp) // Slightly larger for better visibility
                            .background(if (isPlaying) iosRed else iosGreen)
                            .clickable {
                                // Call the onPlayPauseClick callback to handle play/stop functionality
                                onPlayPauseClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if(isPlaying) {
                            Icon(
                                imageVector = Icons.Filled.StopCircle,
                                contentDescription = "Stop Icon",
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier.size(32.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Rounded.PlayCircle,
                                contentDescription = "Play Icon",
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Show visualizer when playing
                    if (isPlaying && audioPlayer != null && settingsState != null) {
                        Spacer(modifier = Modifier.height(8.dp))

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

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
