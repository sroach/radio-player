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
            ColorTheme.MINIMALIST -> if (isDark) {
                createMinimalistDarkScheme()
            } else {
                createMinimalistLightScheme()
            }

            ColorTheme.OCEAN -> if (isDark) {
                createOceanDarkScheme()
            } else {
                createOceanLightScheme()
            }

            ColorTheme.FOREST -> if (isDark) {
                createForestDarkScheme()
            } else {
                createForestLightScheme()
            }

            ColorTheme.SUNSET -> if (isDark) {
                createSunsetDarkScheme()
            } else {
                createSunsetLightScheme()
            }

            ColorTheme.LAVENDER -> if (isDark) {
                createLavenderDarkScheme()
            } else {
                createLavenderLightScheme()
            }

            ColorTheme.ROSE -> if (isDark) {
                createRoseDarkScheme()
            } else {
                createRoseLightScheme()
            }
        }
    }

    // Minimalist schemes
    private fun createMinimalistLightScheme() = lightColorScheme(
        primary = LightMinimalistColors.primary,
        onPrimary = LightMinimalistColors.onPrimary,
        primaryContainer = LightMinimalistColors.primaryContainer,
        onPrimaryContainer = LightMinimalistColors.onPrimaryContainer,
        secondary = LightMinimalistColors.secondary,
        onSecondary = LightMinimalistColors.onSecondary,
        background = LightMinimalistColors.background,
        onBackground = LightMinimalistColors.onBackground,
        surface = LightMinimalistColors.surface,
        onSurface = LightMinimalistColors.onSurface,
        surfaceVariant = LightMinimalistColors.surfaceVariant,
        onSurfaceVariant = LightMinimalistColors.onSurfaceVariant,
        outline = LightMinimalistColors.outline,
        outlineVariant = LightMinimalistColors.outlineVariant
    )

    private fun createMinimalistDarkScheme() = darkColorScheme(
        primary = DarkMinimalistColors.primary,
        onPrimary = DarkMinimalistColors.onPrimary,
        primaryContainer = DarkMinimalistColors.primaryContainer,
        onPrimaryContainer = DarkMinimalistColors.onPrimaryContainer,
        secondary = DarkMinimalistColors.secondary,
        onSecondary = DarkMinimalistColors.onSecondary,
        background = DarkMinimalistColors.background,
        onBackground = DarkMinimalistColors.onBackground,
        surface = DarkMinimalistColors.surface,
        onSurface = DarkMinimalistColors.onSurface,
        surfaceVariant = DarkMinimalistColors.surfaceVariant,
        onSurfaceVariant = DarkMinimalistColors.onSurfaceVariant,
        outline = DarkMinimalistColors.outline,
        outlineVariant = DarkMinimalistColors.outlineVariant
    )

    // Ocean schemes
    private fun createOceanLightScheme() = lightColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFF007AFF),
        onPrimary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        primaryContainer = androidx.compose.ui.graphics.Color(0xFFD1E7FF),
        onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF001D36),
        background = androidx.compose.ui.graphics.Color(0xFFFAFCFF),
        onBackground = androidx.compose.ui.graphics.Color(0xFF1A1C1E),
        surface = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        onSurface = androidx.compose.ui.graphics.Color(0xFF1A1C1E),
        surfaceVariant = androidx.compose.ui.graphics.Color(0xFFF5F9FF),
        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF42474E),
        outline = androidx.compose.ui.graphics.Color(0xFFD4E7F7)
    )

    private fun createOceanDarkScheme() = darkColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFF0EA5E9),
        onPrimary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        primaryContainer = androidx.compose.ui.graphics.Color(0xFF0369A1),
        onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFFBAE6FD),
        background = androidx.compose.ui.graphics.Color(0xFF0A1929),
        onBackground = androidx.compose.ui.graphics.Color(0xFFE2E8F0),
        surface = androidx.compose.ui.graphics.Color(0xFF1E293B),
        onSurface = androidx.compose.ui.graphics.Color(0xFFE2E8F0),
        surfaceVariant = androidx.compose.ui.graphics.Color(0xFF334155),
        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF94A3B8),
        outline = androidx.compose.ui.graphics.Color(0xFF475569)
    )

    // Forest schemes
    private fun createForestLightScheme() = lightColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFF34C759),
        onPrimary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        primaryContainer = androidx.compose.ui.graphics.Color(0xFFBBF7D0),
        onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF052E16),
        background = androidx.compose.ui.graphics.Color(0xFFF7FDF7),
        onBackground = androidx.compose.ui.graphics.Color(0xFF1A1C1A),
        surface = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        onSurface = androidx.compose.ui.graphics.Color(0xFF1A1C1A),
        surfaceVariant = androidx.compose.ui.graphics.Color(0xFFF0F9F0),
        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF424940),
        outline = androidx.compose.ui.graphics.Color(0xFFD1F5D3)
    )

    private fun createForestDarkScheme() = darkColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFF22C55E),
        onPrimary = androidx.compose.ui.graphics.Color(0xFF052E16),
        primaryContainer = androidx.compose.ui.graphics.Color(0xFF166534),
        onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFFBBF7D0),
        background = androidx.compose.ui.graphics.Color(0xFF0F1B0F),
        onBackground = androidx.compose.ui.graphics.Color(0xFFE0F2E0),
        surface = androidx.compose.ui.graphics.Color(0xFF1A2E1A),
        onSurface = androidx.compose.ui.graphics.Color(0xFFE0F2E0),
        surfaceVariant = androidx.compose.ui.graphics.Color(0xFF2D4A2D),
        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF9CA69C),
        outline = androidx.compose.ui.graphics.Color(0xFF4A5A4A)
    )

    // Sunset schemes
    private fun createSunsetLightScheme() = lightColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFFFF9500),
        onPrimary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        primaryContainer = androidx.compose.ui.graphics.Color(0xFFFFE4CC),
        onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF2E1B00),
        background = androidx.compose.ui.graphics.Color(0xFFFFFAF5),
        onBackground = androidx.compose.ui.graphics.Color(0xFF1C1A1A),
        surface = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        onSurface = androidx.compose.ui.graphics.Color(0xFF1C1A1A),
        surfaceVariant = androidx.compose.ui.graphics.Color(0xFFFFF7F0),
        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF4A4240),
        outline = androidx.compose.ui.graphics.Color(0xFFFDE4CC)
    )

    private fun createSunsetDarkScheme() = darkColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFFFF9500),
        onPrimary = androidx.compose.ui.graphics.Color(0xFF2E1B00),
        primaryContainer = androidx.compose.ui.graphics.Color(0xFF8B4000),
        onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFFFFE4CC),
        background = androidx.compose.ui.graphics.Color(0xFF1F0F0A),
        onBackground = androidx.compose.ui.graphics.Color(0xFFF0E8E0),
        surface = androidx.compose.ui.graphics.Color(0xFF2E1A12),
        onSurface = androidx.compose.ui.graphics.Color(0xFFF0E8E0),
        surfaceVariant = androidx.compose.ui.graphics.Color(0xFF4A2C1F),
        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFA69C94),
        outline = androidx.compose.ui.graphics.Color(0xFF5A4A3F)
    )

    // Lavender schemes
    private fun createLavenderLightScheme() = lightColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFFAF52DE),
        onPrimary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        primaryContainer = androidx.compose.ui.graphics.Color(0xFFE9D5FF),
        onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF2E0A4A),
        background = androidx.compose.ui.graphics.Color(0xFFFAF9FF),
        onBackground = androidx.compose.ui.graphics.Color(0xFF1A1A1C),
        surface = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        onSurface = androidx.compose.ui.graphics.Color(0xFF1A1A1C),
        surfaceVariant = androidx.compose.ui.graphics.Color(0xFFF5F3FF),
        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF4A424E),
        outline = androidx.compose.ui.graphics.Color(0xFFE9D5FF)
    )

    private fun createLavenderDarkScheme() = darkColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFFAF52DE),
        onPrimary = androidx.compose.ui.graphics.Color(0xFF2E0A4A),
        primaryContainer = androidx.compose.ui.graphics.Color(0xFF6B2C91),
        onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFFE9D5FF),
        background = androidx.compose.ui.graphics.Color(0xFF15101F),
        onBackground = androidx.compose.ui.graphics.Color(0xFFEDE8F0),
        surface = androidx.compose.ui.graphics.Color(0xFF1F1A2E),
        onSurface = androidx.compose.ui.graphics.Color(0xFFEDE8F0),
        surfaceVariant = androidx.compose.ui.graphics.Color(0xFF332D4A),
        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF9C94A6),
        outline = androidx.compose.ui.graphics.Color(0xFF4A4250)
    )

    // Rose schemes
    private fun createRoseLightScheme() = lightColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFFFF2D92),
        onPrimary = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        primaryContainer = androidx.compose.ui.graphics.Color(0xFFFFDAE6),
        onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF3D0A1E),
        background = androidx.compose.ui.graphics.Color(0xFFFFF7F8),
        onBackground = androidx.compose.ui.graphics.Color(0xFF1C1A1B),
        surface = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        onSurface = androidx.compose.ui.graphics.Color(0xFF1C1A1B),
        surfaceVariant = androidx.compose.ui.graphics.Color(0xFFFFF0F2),
        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF4A4245),
        outline = androidx.compose.ui.graphics.Color(0xFFFFDAE6)
    )

    private fun createRoseDarkScheme() = darkColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFFFF2D92),
        onPrimary = androidx.compose.ui.graphics.Color(0xFF3D0A1E),
        primaryContainer = androidx.compose.ui.graphics.Color(0xFF91204A),
        onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFFFFDAE6),
        background = androidx.compose.ui.graphics.Color(0xFF1F0A14),
        onBackground = androidx.compose.ui.graphics.Color(0xFFF0E8EB),
        surface = androidx.compose.ui.graphics.Color(0xFF2E1220),
        onSurface = androidx.compose.ui.graphics.Color(0xFFF0E8EB),
        surfaceVariant = androidx.compose.ui.graphics.Color(0xFF4A1F33),
        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFA6949C),
        outline = androidx.compose.ui.graphics.Color(0xFF5A424A)
    )
}