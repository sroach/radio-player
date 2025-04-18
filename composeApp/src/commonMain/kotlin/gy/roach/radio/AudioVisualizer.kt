package gy.roach.radio

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

/**
 * A Composable function that displays an audio spectrum visualization.
 * 
 * @param isPlaying Whether audio is currently playing
 * @param modifier Modifier for the visualization
 */
@Composable
expect fun AudioSpectrum(
    isPlaying: Boolean,
    modifier: Modifier = Modifier.fillMaxWidth().height(80.dp)
)