package gy.roach.radio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import gy.roach.radio.visualizers.*

/**
 * Enum representing the available visualizer types.
 */
enum class VisualizerType {
    LAVA_LAMP,
    WAVE,
    EQUALIZER,
    BAR,
    CIRCLE,
    FIREWORKS,
    BOUNCING_BALLS,
    SPECTRUM,
    FREQUENCY
}

/**
 * Class to manage settings state in the app.
 */
class SettingsState {
    /**
     * The currently selected visualizer type.
     */
    var visualizerType by mutableStateOf(VisualizerType.FREQUENCY)
        private set

    /**
     * Update the visualizer type.
     */
    fun updateVisualizerType(type: VisualizerType) {
        visualizerType = type
    }
}

/**
 * Remember a settings state that can be used for settings management.
 */
@Composable
fun rememberSettingsState(): SettingsState {
    return remember { SettingsState() }
}

/**
 * Renders the appropriate visualizer based on the selected type.
 */
@Composable
fun SelectedVisualizer(
    type: VisualizerType,
    isPlaying: Boolean,
    audioPlayer: AudioPlayer,
    modifier: Modifier = Modifier
) {
    when (type) {
        VisualizerType.LAVA_LAMP -> LavaLampVisualizer(isPlaying = isPlaying, modifier = modifier)
        VisualizerType.WAVE -> WaveVisualizer(isPlaying = isPlaying, modifier = modifier)
        VisualizerType.EQUALIZER -> EqualizerVisualizer(audioPlayer = audioPlayer, modifier = modifier)
        VisualizerType.BAR -> BarVisualizer(isPlaying = isPlaying, modifier = modifier)
        VisualizerType.CIRCLE -> CircleVisualizer(isPlaying = isPlaying, modifier = modifier)
        VisualizerType.FIREWORKS -> FireworksVisualizer(isPlaying = isPlaying, modifier = modifier)
        VisualizerType.BOUNCING_BALLS -> BalloonsVisualizer(isPlaying = isPlaying, modifier = modifier)
        VisualizerType.SPECTRUM -> SpectrumVisualizer(isPlaying = isPlaying, modifier = modifier)
        VisualizerType.FREQUENCY -> FrequencyVisualizer(isPlaying = isPlaying, modifier = modifier)
    }
}
