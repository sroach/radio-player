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
                                        contentColor = Color(0xFFBB86FC).copy(alpha = 0.85f)
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
    onClick: () -> Unit = {},
    onPlayPauseClick: () -> Unit = {}
) {
    // Define iOS system green color for play button
    val iosGreen = Color(0xFF34C759) // iOS system green
    val iosRed = MaterialTheme.colors.error // Using the iOS red from theme

    // iOS-inspired card with subtle styling
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp) // iOS uses more horizontal padding
            .clickable {
                // Perform the original onClick action
                onClick()
            },
        elevation = 0.dp, // iOS uses flat designs without elevation
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
        // Two-column layout
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // iOS standard padding
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // First column: Station information (icon and text)
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Display the station's icon with iOS-style sizing and theme-aware coloring
                Image(
                    painter = painterResource(Res.drawable.radio_icon),
                    contentDescription = "Radio Icon",
                    modifier = Modifier
                        .size(44.dp) // iOS standard icon size
                        .padding(2.dp), // Reduced padding for cleaner look
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                        if (MaterialTheme.colors.isLight) 
                            Color.Black.copy(alpha = 0.8f) // Slightly softer black for light mode
                        else 
                            Color.White.copy(alpha = 0.9f) // Slightly softer white for dark mode
                    )
                )

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

            // Second column: Play/Stop button (always visible)
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(44.dp) // iOS standard control size
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
                        tint = MaterialTheme.colors.onSurface
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.PlayCircle,
                        contentDescription = "Play Icon",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}
