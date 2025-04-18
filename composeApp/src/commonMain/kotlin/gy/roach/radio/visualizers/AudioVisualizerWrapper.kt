package gy.roach.radio.visualizers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gy.roach.radio.AudioSpectrum

/**
 * A wrapper for the AudioSpectrum function from the original package.
 * This allows us to use the function from the visualizers package.
 */
@Composable
fun AudioSpectrumWrapper(
    isPlaying: Boolean,
    modifier: Modifier
) {
    AudioSpectrum(isPlaying, modifier)
}