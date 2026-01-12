package gy.roach.radio

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gy.roach.radio.theme.GuyanaColors
import gy.roach.radio.ui.CompactRadioWaves
import gy.roach.radio.ui.GuyanaLoadingSpinner
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Main screen of the app showing the radio stations list with staggered animations.
 */
@Composable
fun MainScreen(
    audioPlayer: AudioPlayer,
    selectedStationIndex: Int = 0,
    isPlaying: Boolean = false,
    isExpanded: Boolean,
    onToggleExpansion: (Boolean) -> Unit,
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

    // FIX 1: Preserve scroll position across recompositions
    val listState = rememberLazyListState()


    // FIX 2: Only trigger animation ONCE on initial composition,
    // or when station COUNT changes (e.g., after API refresh)
    var animationTriggered by remember { mutableStateOf(false) }
    var lastStationCount by remember { mutableStateOf(0) }


    LaunchedEffect(filteredStations.size) {
        // Only re-animate if the list size actually changed (new data loaded)
        if (filteredStations.size != lastStationCount) {
            lastStationCount = filteredStations.size
            animationTriggered = false
            delay(30)
            animationTriggered = true
        } else if (!animationTriggered) {
            // First time load
            delay(30)
            animationTriggered = true
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
                    state = listState, // FIX 3: Apply the remembered state
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    itemsIndexed(
                        items = filteredStations,
                        key = { _, item -> item.index }
                    ) { index, stationItem ->
                        // Staggered animation for each item
                        AnimatedStationItem(
                            stationItem = stationItem,
                            isSelected = stationItem.index == selectedStationIndex,
                            isPlaying = isPlaying && stationItem.index == selectedStationIndex,
                            animationDelay = index * 50, // 50ms stagger between items
                            isVisible = animationTriggered,
                            onClick = {
                                onStationSelected(stationItem.index)
                                onToggleExpansion(true) // Trigger expansion here
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
                            AnimatedFilterChip(
                                text = "All",
                                selected = selectedStationTypes.isEmpty(),
                                onClick = { selectedStationTypes = emptySet() }
                            )
                        }

                        // Type filter chips
                        items(allStationTypes.size) { index ->
                            val type = allStationTypes[index]
                            AnimatedFilterChip(
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
 * Animated station item with staggered reveal and selection effects.
 */
@Composable
fun AnimatedStationItem(
    stationItem: StationItem,
    isSelected: Boolean,
    isPlaying: Boolean,
    animationDelay: Int,
    isVisible: Boolean,
    onClick: () -> Unit
) {
    // Staggered fade and slide animation
    var itemVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(animationDelay.toLong())
            itemVisible = true
        } else {
            itemVisible = false
        }
    }

    // Animated values for reveal
    val alpha by animateFloatAsState(
        targetValue = if (itemVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = EaseOutCubic
        ),
        label = "itemAlpha"
    )

    val offsetY by animateFloatAsState(
        targetValue = if (itemVisible) 0f else 20f,
        animationSpec = tween(
            durationMillis = 300,
            easing = EaseOutCubic
        ),
        label = "itemOffset"
    )

    // Selection scale animation with bounce
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "selectionScale"
    )

    Box(
        modifier = Modifier
            .graphicsLayer {
                this.alpha = alpha
                this.translationY = offsetY
                this.scaleX = scale
                this.scaleY = scale
            }
    ) {
        StationItemCard(
            stationItem = stationItem,
            isSelected = isSelected,
            isPlaying = isPlaying,
            onClick = onClick
        )
    }
}

/**
 * Animated filter chip with press effect.
 */
@Composable
fun AnimatedFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    // Scale animation on selection
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "chipScale"
    )

    // Background color animation
    val backgroundColor by animateColorAsState(
        targetValue = if (selected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surface,
        animationSpec = tween(200),
        label = "chipBg"
    )

    val contentColor by animateColorAsState(
        targetValue = if (selected)
            MaterialTheme.colorScheme.onPrimary
        else
            MaterialTheme.colorScheme.onSurface,
        animationSpec = tween(200),
        label = "chipContent"
    )

    Surface(
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        color = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (selected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
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
 * Features a frosted glass effect for visual depth.
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
        // Gradient divider line at the top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0f)
                        )
                    )
                )
        )

        // Bottom app bar with glass effect
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
            tonalElevation = 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Radio waves indicator (shows when playing)
                CompactRadioWaves(
                    isPlaying = isPlaying,
                    modifier = Modifier.padding(end = 8.dp)
                )

                // Display selected station info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = selectedStation.label,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = selectedStation.typeAsString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                // Animated Play/Stop button
                AnimatedPlayStopButton(
                    isPlaying = isPlaying,
                    onStop = onStopPlayback,
                    onPlay = onPlayStation
                )

                IconButton(onClick = onNavigateToSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                // Refresh button
                IconButton(
                    onClick = onRefreshStations,
                    enabled = !isRefreshing,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    if (isRefreshing) {
                        GuyanaLoadingSpinner(
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
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

/**
 * Animated play/stop button with scale bounce effect.
 */
@Composable
fun AnimatedPlayStopButton(
    isPlaying: Boolean,
    onStop: () -> Unit,
    onPlay: () -> Unit
) {
    // Scale animation on state change
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = when {
            pressed -> 0.85f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "playButtonScale"
    )

    IconButton(
        onClick = {
            pressed = true
            if (isPlaying) onStop() else onPlay()
        },
        modifier = Modifier
            .padding(end = 4.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        // Reset pressed state after animation
        LaunchedEffect(pressed) {
            if (pressed) {
                delay(100)
                pressed = false
            }
        }

        AnimatedContent(
            targetState = isPlaying,
            transitionSpec = {
                (scaleIn(initialScale = 0.8f) + fadeIn()) togetherWith
                        (scaleOut(targetScale = 0.8f) + fadeOut())
            },
            label = "playStopTransition"
        ) { playing ->
            if (playing) {
                Icon(
                    imageVector = Icons.Filled.StopCircle,
                    contentDescription = "Stop",
                    tint = GuyanaColors.FlagRed,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.PlayCircle,
                    contentDescription = "Play",
                    tint = GuyanaColors.FlagGreen,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}