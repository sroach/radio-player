package gy.roach.radio.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * 🌈 Atmospheric gradient backgrounds for each cultural theme.
 * Creates depth and visual interest beyond flat solid colors.
 */
object GradientBackgrounds {

    // === GOLDEN ARROWHEAD GRADIENTS ===
    fun goldenArrowheadLight(): Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFDF5),  // Warm cream top
            Color(0xFFFFF9E6),  // Soft gold middle
            Color(0xFFFFFBF0)   // Light gold bottom
        )
    )

    fun goldenArrowheadDark(): Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0A0F0C),  // Deep black-green top
            Color(0xFF0D1208),  // Forest black middle
            Color(0xFF141A0A)   // Gold-tinged black bottom
        )
    )

    // === RAINFOREST DUSK GRADIENTS ===
    fun rainforestDuskLight(): Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE8F5EE),  // Morning mist top
            Color(0xFFF0FAF4),  // Light forest middle
            Color(0xFFFFF8F0)   // Sunset warmth at bottom
        )
    )

    fun rainforestDuskDark(): Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF051F10),  // Deep canopy top
            Color(0xFF0A2818),  // Mid forest
            Color(0xFF0F1A0A)   // Warm earth bottom
        )
    )

    // === KAIETEUR MIST GRADIENTS ===
    fun kaieteurMistLight(): Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE0F7FA),  // Mist spray top
            Color(0xFFE8F5F5),  // Light cyan middle
            Color(0xFFF0FAFA)   // Soft water bottom
        )
    )

    fun kaieteurMistDark(): Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF031517),  // Deep water top
            Color(0xFF061E20),  // Teal depths middle
            Color(0xFF0A2528)   // Pool bottom
        )
    )

    // === STABROEK NIGHTS GRADIENTS ===
    fun stabroekNightsLight(): Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF5F5F5),  // Clean gray top
            Color(0xFFEEEEEE),  // Mid gray
            Color(0xFFE8E8E8)   // Darker gray bottom
        )
    )

    fun stabroekNightsDark(): Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0D0D0D),  // Deep night top
            Color(0xFF121212),  // Urban black middle
            Color(0xFF1A1A1A)   // Charcoal bottom
        )
    )

    /**
     * Returns the appropriate gradient for the current theme and mode.
     */
    fun getGradient(theme: ColorTheme, isDark: Boolean): Brush {
        return when (theme) {
            ColorTheme.GOLDEN_ARROWHEAD -> if (isDark) goldenArrowheadDark() else goldenArrowheadLight()
            ColorTheme.RAINFOREST_DUSK -> if (isDark) rainforestDuskDark() else rainforestDuskLight()
            ColorTheme.KAIETEUR_MIST -> if (isDark) kaieteurMistDark() else kaieteurMistLight()
            ColorTheme.STABROEK_NIGHTS -> if (isDark) stabroekNightsDark() else stabroekNightsLight()
        }
    }

    /**
     * Returns a radial glow gradient for splash screens and hero sections.
     */
    fun getSplashGlow(theme: ColorTheme, isDark: Boolean): Brush {
        val accentColor = theme.accentColor
        return if (isDark) {
            Brush.radialGradient(
                colors = listOf(
                    accentColor.copy(alpha = 0.3f),
                    accentColor.copy(alpha = 0.1f),
                    Color.Transparent
                )
            )
        } else {
            Brush.radialGradient(
                colors = listOf(
                    accentColor.copy(alpha = 0.15f),
                    accentColor.copy(alpha = 0.05f),
                    Color.Transparent
                )
            )
        }
    }
}

/**
 * A composable that wraps content in an atmospheric gradient background.
 */
@Composable
fun GradientBackground(
    theme: ColorTheme,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GradientBackgrounds.getGradient(theme, isDark))
    ) {
        content()
    }
}

/**
 * A composable that adds a radial glow effect behind content.
 * Perfect for splash screens and hero sections.
 */
@Composable
fun GlowBackground(
    theme: ColorTheme,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GradientBackgrounds.getGradient(theme, isDark))
    ) {
        // Glow layer
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GradientBackgrounds.getSplashGlow(theme, isDark))
        )
        content()
    }
}
