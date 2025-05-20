package gy.roach.radio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Settings screen that allows users to configure app preferences.
 *
 * @param settingsState The state object that holds settings preferences
 * @param onNavigateToMain Callback to navigate back to the main screen
 */
@Preview
@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    onNavigateToMain: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Settings content
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Visualizer Settings",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // List of available visualizers
            val visualizers = listOf(
                VisualizerOption("Balloons", VisualizerType.BOUNCING_BALLS),
                VisualizerOption("Lava Lamp", VisualizerType.LAVA_LAMP),
                VisualizerOption("Wave", VisualizerType.WAVE),
                VisualizerOption("Equalizer", VisualizerType.EQUALIZER),
                VisualizerOption("Bar", VisualizerType.BAR),
                VisualizerOption("Circle", VisualizerType.CIRCLE),
                VisualizerOption("Fireworks", VisualizerType.FIREWORKS),
                VisualizerOption("Spectrum", VisualizerType.SPECTRUM)
            )

            items(visualizers) { option ->
                VisualizerSelectionItem(
                    name = option.name,
                    isSelected = settingsState.visualizerType == option.type,
                    onClick = { settingsState.updateVisualizerType(option.type) }
                )
            }
        }

        // Bottom button to return to main screen
        Button(
            onClick = onNavigateToMain,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Save and Return")
        }
    }
}

/**
 * Data class to hold visualizer option information.
 */
private data class VisualizerOption(
    val name: String,
    val type: VisualizerType
)

/**
 * A selectable item for choosing a visualizer.
 */
@Composable
private fun VisualizerSelectionItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Selection indicator
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surface
                    )
                    .border(
                        width = 2.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                               else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Visualizer name
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * Top bar for the settings screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(
    onNavigateToMain: () -> Unit
) {
    TopAppBar(
        title = { Text("Settings",color = MaterialTheme.colorScheme.onSurface,) },
        navigationIcon = {
            IconButton(onClick = onNavigateToMain) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(),
    )
}
