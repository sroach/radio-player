package gy.roach.radio

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import gy.roach.radio.theme.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import radio_guyana_player.composeapp.generated.resources.*


@Composable
fun App(themeSettings: ThemeSettings = remember { ThemeSettings() }) {
    val systemIsDark = isSystemInDarkTheme()

    // Collect the saved preferences
    val savedIsDark by themeSettings.isDarkTheme.collectAsState()
    val savedColorTheme by themeSettings.colorTheme.collectAsState()


    // Convert saved string back to ColorTheme enum
    val initialColorTheme = remember(savedColorTheme) {
        savedColorTheme?.let { name ->
            try {
                ColorTheme.valueOf(name)
            } catch (e: IllegalArgumentException) {
                ColorTheme.GOLDEN_ARROWHEAD // Fallback if invalid
            }
        } ?: ColorTheme.GOLDEN_ARROWHEAD
    }

    // Initialize the state with both saved values
    val modernThemeState = remember(savedIsDark, savedColorTheme) {
        ModernThemeState(
            themeSettings = themeSettings,
            initialIsDark = savedIsDark ?: systemIsDark,
            initialTheme = initialColorTheme
        )
    }

    // Navigation state
    val navigationState = rememberNavigationState()


    val isDark = modernThemeState.isDarkTheme



    // Animated icon rotation
    val iconRotation by animateFloatAsState(
        targetValue = if (isDark) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "iconRotation"
    )

    val icon = remember(isDark) {
        if (isDark) Res.drawable.ic_light_mode
        else Res.drawable.ic_dark_mode
    }

    // Apply modern dynamic theme based on selected color and dark mode
    RadioGuyanaTheme(themeState = modernThemeState) {
        // Audio player state
        val audioPlayer = remember { getAudioPlayer() }
        var isPlaying by remember { mutableStateOf(false) }
        var selectedStationIndex by remember { mutableStateOf(0) }
        // Hero Transition State
        var isExpanded by remember { mutableStateOf(false) }

        val station = remember { Station() }
        val isRefreshing by station.isRefreshing.collectAsState()
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

        // Get the current gradient based on theme
        val currentGradient = GradientBackgrounds.getGradient(
            theme = modernThemeState.selectedTheme,
            isDark = modernThemeState.isDarkTheme
        )
// Use AnimatedContent for the Hero Transition
        AnimatedContent(
            targetState = isExpanded,
            transitionSpec = {
                if (targetState) {
                    (fadeIn(animationSpec = tween(300, delayMillis = 100)) +
                            scaleIn(
                                initialScale = 0.92f,
                                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
                            )) togetherWith
                            fadeOut(animationSpec = tween(200))
                } else {
                    fadeIn(animationSpec = tween(200)) togetherWith
                            (fadeOut(animationSpec = tween(300)) +
                                    scaleOut(targetScale = 0.92f, animationSpec = tween(300)))
                }
            },
            label = "HeroTransition"
        ) { expanded ->
            if (expanded) {
                ExpandedPlayerView(
                    station = selectedStation,
                    isPlaying = isPlaying,
                    onClose = { isExpanded = false },
                    onTogglePlay = {
                        if (isPlaying) audioPlayer.stop() else audioPlayer.play(selectedStation)
                        isPlaying = !isPlaying
                    }
                )
            } else {
                Scaffold(
                    topBar = {
                        when (navigationState.currentScreen) {
                            is Screen.Splash -> {
                                // No top bar for splash screen
                            }

                            is Screen.Main -> {

                                Surface(
                                    color = Color.Transparent,
                                    tonalElevation = 0.dp
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(currentGradient)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(Res.drawable.flag_gy),
                                                contentDescription = "Flag",
                                            )
                                            // Application title text
                                            Text(
                                                text = "GY Tunes",
                                                style = MaterialTheme.typography.titleLarge,
                                                color = MaterialTheme.colorScheme.onBackground
                                            )
                                            // Animated theme toggle
                                            IconButton(
                                                onClick = { modernThemeState.toggleDarkMode() },
                                                colors = IconButtonDefaults.iconButtonColors(
                                                    contentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                                                )
                                            ) {
                                                // Animated icon with crossfade and rotation
                                                AnimatedContent(
                                                    targetState = isDark,
                                                    transitionSpec = {
                                                        (scaleIn(initialScale = 0.8f) + fadeIn()) togetherWith
                                                                (scaleOut(targetScale = 0.8f) + fadeOut())
                                                    },
                                                    label = "themeIconTransition"
                                                ) { dark ->
                                                    Icon(
                                                        imageVector = vectorResource(
                                                            if (dark) Res.drawable.ic_light_mode
                                                            else Res.drawable.ic_dark_mode
                                                        ),
                                                        contentDescription = stringResource(Res.string.theme),
                                                        modifier = Modifier.rotate(iconRotation)
                                                    )
                                                }
                                            }
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
                                    onNavigateToSettings = { navigationState.navigateToSettings() },
                                    onNavigateToAbout = { navigationState.navigateToAbout() },
                                    onStopPlayback = {
                                        // Stop the currently playing station
                                        try {
                                            audioPlayer.stop()
                                        } catch (e: Throwable) {
                                            println("Error stopping playback: ${e.message}")
                                        }
                                        isPlaying = false
                                    },
                                    onPlayStation = {
                                        // Play the selected station
                                        if (selectedStation.url.isNotBlank()) {
                                            try {
                                                audioPlayer.play(selectedStation)
                                                isPlaying = true
                                            } catch (e: Throwable) {
                                                println("Error starting playback: ${e.message}")
                                                isPlaying = false
                                            }
                                        } else {
                                            println("Error: Station URL is blank")
                                        }
                                    },
                                    isRefreshing = isRefreshing,
                                    onRefreshStations = { station.refreshStations() }
                                )
                            }
                        }
                    },
                    containerColor = Color.Transparent
                ) { paddingValues ->
                    // Gradient background layer
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(currentGradient)
                            .padding(paddingValues)
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
                                    isExpanded = isExpanded,
                                    onToggleExpansion = { isExpanded = it },
                                    onStationSelected = { newIndex ->
                                        // If selecting a different station while one is playing,
                                        // stop the current one first
                                        if (isPlaying && selectedStationIndex != newIndex) {
                                            try {
                                                audioPlayer.stop()
                                            } catch (e: Throwable) {
                                                println("Error stopping playback on station change: ${e.message}")
                                            }
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
                                    themeState = modernThemeState,
                                    onNavigateToMain = { navigationState.navigateToMain() }
                                )
                            }

                            is Screen.ReleaseNotes -> {
                                ReleaseNotesScreen(

                                )
                            }
                        }
                    }
                }
            }
        }
    }
}