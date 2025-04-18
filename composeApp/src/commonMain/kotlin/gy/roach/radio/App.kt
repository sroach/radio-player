package gy.roach.radio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import radio_guyana_player.composeapp.generated.resources.Res
import radio_guyana_player.composeapp.generated.resources.favicon
import radio_guyana_player.composeapp.generated.resources.flag_gy
import radio_guyana_player.composeapp.generated.resources.radio_icon

@Preview
@Composable
fun App(themeState: ThemeState? = null) {
    // Use provided theme state or get the current one from composition
    val currentThemeState = themeState ?: rememberThemeState()

    // Navigation state
    val navigationState = rememberNavigationState()

    // Settings state
    val settingsState = rememberSettingsState()

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
                        TopAppBar(
                            title = {
                                // Application Title with Favicon in TopAppBar
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Favicon image with iOS-style theme awareness
                                    Image(
                                        painter = painterResource(Res.drawable.favicon),
                                        contentDescription = "Favicon",
                                        modifier = Modifier.size(32.dp).padding(end = 8.dp)
                                            .aspectRatio(5f/3f),
                                        colorFilter = if (currentThemeState.isDarkTheme) {
                                            // In dark mode, use a subtle color filter to make the icon more visible
                                            androidx.compose.ui.graphics.ColorFilter.lighting(
                                                add = Color(0xFF0A84FF).copy(alpha = 0.2f), // iOS dark mode blue tint
                                                multiply = Color.White.copy(alpha = 0.9f)    // Slightly soften
                                            )
                                        } else {
                                            // In light mode, use the original colors
                                            null
                                        }
                                    )

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
                                }
                            },
                            actions = {
                                // Settings button in TopAppBar actions area
                                IconButton(onClick = { navigationState.navigateToSettings() }) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Settings",
                                        tint = MaterialTheme.colors.onSurface
                                    )
                                }

                                // About button in TopAppBar actions area
                                IconButton(onClick = { navigationState.navigateToAbout() }) {
                                    // iOS-style info icon

                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "About",
                                        tint = MaterialTheme.colors.onSurface
                                    )

                                }

                                // Theme toggle in TopAppBar actions area - iOS style
                                IconButton(
                                    onClick = { currentThemeState.toggleTheme() }
                                ) {
                                    // Use a surface with a circular shape to create a nice toggle button
                                    Surface(
                                        shape = androidx.compose.foundation.shape.CircleShape,
                                        color = if (currentThemeState.isDarkTheme)
                                                   MaterialTheme.colors.surface
                                                else
                                                   MaterialTheme.colors.surface,
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        // Center the icon
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            // iOS-style theme toggle icon
                                            if (currentThemeState.isDarkTheme) {
                                                // Sun icon for light mode (when in dark mode)
                                                Box(
                                                    modifier = Modifier.size(24.dp),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    // Sun circle
                                                    Box(
                                                        modifier = Modifier
                                                            .size(12.dp)
                                                            .background(Color(0xFFFF9500), CircleShape)
                                                    )

                                                    // Sun rays
                                                    Box(modifier = Modifier.fillMaxSize()) {
                                                        // Top ray
                                                        Box(
                                                            modifier = Modifier
                                                                .width(2.dp)
                                                                .height(5.dp)
                                                                .background(Color(0xFFFF9500))
                                                                .align(Alignment.TopCenter)
                                                        )
                                                        // Bottom ray
                                                        Box(
                                                            modifier = Modifier
                                                                .width(2.dp)
                                                                .height(5.dp)
                                                                .background(Color(0xFFFF9500))
                                                                .align(Alignment.BottomCenter)
                                                        )
                                                        // Left ray
                                                        Box(
                                                            modifier = Modifier
                                                                .width(5.dp)
                                                                .height(2.dp)
                                                                .background(Color(0xFFFF9500))
                                                                .align(Alignment.CenterStart)
                                                        )
                                                        // Right ray
                                                        Box(
                                                            modifier = Modifier
                                                                .width(5.dp)
                                                                .height(2.dp)
                                                                .background(Color(0xFFFF9500))
                                                                .align(Alignment.CenterEnd)
                                                        )
                                                    }
                                                }
                                            } else {
                                                // Moon icon for dark mode (when in light mode)
                                                Box(
                                                    modifier = Modifier
                                                        .size(24.dp)
                                                        .padding(2.dp)
                                                ) {
                                                    // Crescent moon shape using overlapping circles
                                                    Box(
                                                        modifier = Modifier
                                                            .size(20.dp)
                                                            .background(Color(0xFF8E8E93), CircleShape)
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .size(16.dp)
                                                            .background(MaterialTheme.colors.surface, CircleShape)
                                                            .align(Alignment.CenterEnd)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            backgroundColor = MaterialTheme.colors.surface,
                            elevation = 8.dp
                        )
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
                            onPlayPauseClick = {
                                try {
                                    if (isPlaying) {
                                        audioPlayer.stop()
                                        isPlaying = false
                                    } else {
                                        // Make sure we have a valid URL
                                        if (selectedStation.url.isNotBlank()) {
                                            audioPlayer.play(selectedStation.url)
                                            isPlaying = true
                                        } else {
                                            println("Error: Station URL is blank")
                                        }
                                    }
                                } catch (e: Exception) {
                                    println("Error toggling playback: ${e.message}")
                                    // Reset state to ensure UI is consistent
                                    isPlaying = audioPlayer.isPlaying()
                                }
                            }
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
    onClick: () -> Unit = {}
) {
    // iOS-inspired card with subtle styling
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp) // iOS uses more horizontal padding
            .clickable(onClick = onClick),
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
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp), // iOS standard padding
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
                Column(
                    modifier = Modifier.weight(1f)
                ) {
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
                        text = stationItem.type,
                        style = MaterialTheme.typography.body2, // Using iOS-inspired typography from Theme.kt
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f) // iOS uses slightly more transparent secondary text
                    )
                }

                // iOS-style chevron indicator
                Text(
                    text = "›",
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Light
                    ),
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Equalizer has been moved to the bottom bar
        }
    }
}
