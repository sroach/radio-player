package gy.roach.radio.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Provides dynamic color schemes based on the selected theme.
 * Implements iOS-style color generation patterns.
 */
object DynamicColorSchemeProvider {

    @Composable
    fun getColorScheme(theme: ColorTheme, isDark: Boolean): ColorScheme {
        return when (theme) {
            ColorTheme.GOLDEN_ARROWHEAD -> if (isDark) {
                createGoldenArrowheadDarkScheme()
            } else {
                createGoldenArrowheadLightScheme()
            }

            ColorTheme.RAINFOREST_DUSK -> if (isDark) {
                createRainforestDuskDarkScheme()
            } else {
                createRainforestDuskLightScheme()
            }

            ColorTheme.KAIETEUR_MIST -> if (isDark) {
                createKaieteurMistDarkScheme()
            } else {
                createKaieteurMistLightScheme()
            }

            ColorTheme.STABROEK_NIGHTS -> if (isDark) {
                createStabroekNightsDarkScheme()
            } else {
                createStabroekNightsLightScheme()
            }
        }
    }

    // === GOLDEN ARROWHEAD THEME ===
    private fun createGoldenArrowheadLightScheme() = lightColorScheme(
        primary = GoldenArrowheadTheme.Light.primary,
        onPrimary = GoldenArrowheadTheme.Light.onPrimary,
        primaryContainer = GoldenArrowheadTheme.Light.primaryContainer,
        onPrimaryContainer = GoldenArrowheadTheme.Light.onPrimaryContainer,
        secondary = GoldenArrowheadTheme.Light.secondary,
        onSecondary = GoldenArrowheadTheme.Light.onSecondary,
        tertiary = GoldenArrowheadTheme.Light.tertiary,
        onTertiary = GoldenArrowheadTheme.Light.onTertiary,
        background = GoldenArrowheadTheme.Light.background,
        onBackground = GoldenArrowheadTheme.Light.onBackground,
        surface = GoldenArrowheadTheme.Light.surface,
        onSurface = GoldenArrowheadTheme.Light.onSurface,
        surfaceVariant = GoldenArrowheadTheme.Light.surfaceVariant,
        onSurfaceVariant = GoldenArrowheadTheme.Light.onSurfaceVariant,
        outline = GoldenArrowheadTheme.Light.outline,
        outlineVariant = GoldenArrowheadTheme.Light.outlineVariant
    )

    private fun createGoldenArrowheadDarkScheme() = darkColorScheme(
        primary = GoldenArrowheadTheme.Dark.primary,
        onPrimary = GoldenArrowheadTheme.Dark.onPrimary,
        primaryContainer = GoldenArrowheadTheme.Dark.primaryContainer,
        onPrimaryContainer = GoldenArrowheadTheme.Dark.onPrimaryContainer,
        secondary = GoldenArrowheadTheme.Dark.secondary,
        onSecondary = GoldenArrowheadTheme.Dark.onSecondary,
        tertiary = GoldenArrowheadTheme.Dark.tertiary,
        onTertiary = GoldenArrowheadTheme.Dark.onTertiary,
        background = GoldenArrowheadTheme.Dark.background,
        onBackground = GoldenArrowheadTheme.Dark.onBackground,
        surface = GoldenArrowheadTheme.Dark.surface,
        onSurface = GoldenArrowheadTheme.Dark.onSurface,
        surfaceVariant = GoldenArrowheadTheme.Dark.surfaceVariant,
        onSurfaceVariant = GoldenArrowheadTheme.Dark.onSurfaceVariant,
        outline = GoldenArrowheadTheme.Dark.outline,
        outlineVariant = GoldenArrowheadTheme.Dark.outlineVariant
    )

    // === RAINFOREST DUSK THEME ===
    private fun createRainforestDuskLightScheme() = lightColorScheme(
        primary = RainforestDuskTheme.Light.primary,
        onPrimary = RainforestDuskTheme.Light.onPrimary,
        primaryContainer = RainforestDuskTheme.Light.primaryContainer,
        onPrimaryContainer = RainforestDuskTheme.Light.onPrimaryContainer,
        secondary = RainforestDuskTheme.Light.secondary,
        onSecondary = RainforestDuskTheme.Light.onSecondary,
        tertiary = RainforestDuskTheme.Light.tertiary,
        onTertiary = RainforestDuskTheme.Light.onTertiary,
        background = RainforestDuskTheme.Light.background,
        onBackground = RainforestDuskTheme.Light.onBackground,
        surface = RainforestDuskTheme.Light.surface,
        onSurface = RainforestDuskTheme.Light.onSurface,
        surfaceVariant = RainforestDuskTheme.Light.surfaceVariant,
        onSurfaceVariant = RainforestDuskTheme.Light.onSurfaceVariant,
        outline = RainforestDuskTheme.Light.outline,
        outlineVariant = RainforestDuskTheme.Light.outlineVariant
    )

    private fun createRainforestDuskDarkScheme() = darkColorScheme(
        primary = RainforestDuskTheme.Dark.primary,
        onPrimary = RainforestDuskTheme.Dark.onPrimary,
        primaryContainer = RainforestDuskTheme.Dark.primaryContainer,
        onPrimaryContainer = RainforestDuskTheme.Dark.onPrimaryContainer,
        secondary = RainforestDuskTheme.Dark.secondary,
        onSecondary = RainforestDuskTheme.Dark.onSecondary,
        tertiary = RainforestDuskTheme.Dark.tertiary,
        onTertiary = RainforestDuskTheme.Dark.onTertiary,
        background = RainforestDuskTheme.Dark.background,
        onBackground = RainforestDuskTheme.Dark.onBackground,
        surface = RainforestDuskTheme.Dark.surface,
        onSurface = RainforestDuskTheme.Dark.onSurface,
        surfaceVariant = RainforestDuskTheme.Dark.surfaceVariant,
        onSurfaceVariant = RainforestDuskTheme.Dark.onSurfaceVariant,
        outline = RainforestDuskTheme.Dark.outline,
        outlineVariant = RainforestDuskTheme.Dark.outlineVariant
    )

    // === KAIETEUR MIST THEME ===
    private fun createKaieteurMistLightScheme() = lightColorScheme(
        primary = KaieteurMistTheme.Light.primary,
        onPrimary = KaieteurMistTheme.Light.onPrimary,
        primaryContainer = KaieteurMistTheme.Light.primaryContainer,
        onPrimaryContainer = KaieteurMistTheme.Light.onPrimaryContainer,
        secondary = KaieteurMistTheme.Light.secondary,
        onSecondary = KaieteurMistTheme.Light.onSecondary,
        tertiary = KaieteurMistTheme.Light.tertiary,
        onTertiary = KaieteurMistTheme.Light.onTertiary,
        background = KaieteurMistTheme.Light.background,
        onBackground = KaieteurMistTheme.Light.onBackground,
        surface = KaieteurMistTheme.Light.surface,
        onSurface = KaieteurMistTheme.Light.onSurface,
        surfaceVariant = KaieteurMistTheme.Light.surfaceVariant,
        onSurfaceVariant = KaieteurMistTheme.Light.onSurfaceVariant,
        outline = KaieteurMistTheme.Light.outline,
        outlineVariant = KaieteurMistTheme.Light.outlineVariant
    )

    private fun createKaieteurMistDarkScheme() = darkColorScheme(
        primary = KaieteurMistTheme.Dark.primary,
        onPrimary = KaieteurMistTheme.Dark.onPrimary,
        primaryContainer = KaieteurMistTheme.Dark.primaryContainer,
        onPrimaryContainer = KaieteurMistTheme.Dark.onPrimaryContainer,
        secondary = KaieteurMistTheme.Dark.secondary,
        onSecondary = KaieteurMistTheme.Dark.onSecondary,
        tertiary = KaieteurMistTheme.Dark.tertiary,
        onTertiary = KaieteurMistTheme.Dark.onTertiary,
        background = KaieteurMistTheme.Dark.background,
        onBackground = KaieteurMistTheme.Dark.onBackground,
        surface = KaieteurMistTheme.Dark.surface,
        onSurface = KaieteurMistTheme.Dark.onSurface,
        surfaceVariant = KaieteurMistTheme.Dark.surfaceVariant,
        onSurfaceVariant = KaieteurMistTheme.Dark.onSurfaceVariant,
        outline = KaieteurMistTheme.Dark.outline,
        outlineVariant = KaieteurMistTheme.Dark.outlineVariant
    )

    // === STABROEK NIGHTS THEME ===
    private fun createStabroekNightsLightScheme() = lightColorScheme(
        primary = StabroekNightsTheme.Light.primary,
        onPrimary = StabroekNightsTheme.Light.onPrimary,
        primaryContainer = StabroekNightsTheme.Light.primaryContainer,
        onPrimaryContainer = StabroekNightsTheme.Light.onPrimaryContainer,
        secondary = StabroekNightsTheme.Light.secondary,
        onSecondary = StabroekNightsTheme.Light.onSecondary,
        tertiary = StabroekNightsTheme.Light.tertiary,
        onTertiary = StabroekNightsTheme.Light.onTertiary,
        background = StabroekNightsTheme.Light.background,
        onBackground = StabroekNightsTheme.Light.onBackground,
        surface = StabroekNightsTheme.Light.surface,
        onSurface = StabroekNightsTheme.Light.onSurface,
        surfaceVariant = StabroekNightsTheme.Light.surfaceVariant,
        onSurfaceVariant = StabroekNightsTheme.Light.onSurfaceVariant,
        outline = StabroekNightsTheme.Light.outline,
        outlineVariant = StabroekNightsTheme.Light.outlineVariant
    )

    private fun createStabroekNightsDarkScheme() = darkColorScheme(
        primary = StabroekNightsTheme.Dark.primary,
        onPrimary = StabroekNightsTheme.Dark.onPrimary,
        primaryContainer = StabroekNightsTheme.Dark.primaryContainer,
        onPrimaryContainer = StabroekNightsTheme.Dark.onPrimaryContainer,
        secondary = StabroekNightsTheme.Dark.secondary,
        onSecondary = StabroekNightsTheme.Dark.onSecondary,
        tertiary = StabroekNightsTheme.Dark.tertiary,
        onTertiary = StabroekNightsTheme.Dark.onTertiary,
        background = StabroekNightsTheme.Dark.background,
        onBackground = StabroekNightsTheme.Dark.onBackground,
        surface = StabroekNightsTheme.Dark.surface,
        onSurface = StabroekNightsTheme.Dark.onSurface,
        surfaceVariant = StabroekNightsTheme.Dark.surfaceVariant,
        onSurfaceVariant = StabroekNightsTheme.Dark.onSurfaceVariant,
        outline = StabroekNightsTheme.Dark.outline,
        outlineVariant = StabroekNightsTheme.Dark.outlineVariant
    )
}