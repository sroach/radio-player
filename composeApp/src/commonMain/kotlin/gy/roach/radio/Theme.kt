package gy.roach.radio

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Light theme colors - iOS aesthetic inspired
private val LightColorScheme = lightColorScheme(
    primary = IOSColors.LightPrimary,
    onPrimary = IOSColors.LightOnPrimary,
    primaryContainer = IOSColors.LightPrimaryContainer,
    onPrimaryContainer = IOSColors.LightOnPrimaryContainer,
    secondary = IOSColors.LightSecondary,
    onSecondary = IOSColors.LightOnSecondary,
    secondaryContainer = IOSColors.LightSecondaryContainer,
    onSecondaryContainer = IOSColors.LightOnSecondaryContainer,
    tertiary = IOSColors.LightTertiary,
    onTertiary = IOSColors.LightOnTertiary,
    tertiaryContainer = IOSColors.LightTertiaryContainer,
    onTertiaryContainer = IOSColors.LightOnTertiaryContainer,
    error = IOSColors.LightError,
    errorContainer = IOSColors.LightErrorContainer,
    onError = IOSColors.LightOnError,
    onErrorContainer = IOSColors.LightOnErrorContainer,
    background = IOSColors.LightBackground,
    onBackground = IOSColors.LightOnBackground,
    surface = IOSColors.LightSurface,
    onSurface = IOSColors.LightOnSurface,
    surfaceVariant = IOSColors.LightSurfaceVariant,
    onSurfaceVariant = IOSColors.LightOnSurfaceVariant,
    outline = IOSColors.LightOutline,
    outlineVariant = IOSColors.LightOutlineVariant,
    inverseSurface = IOSColors.LightInverseSurface,
    inverseOnSurface = IOSColors.LightInverseOnSurface,
    inversePrimary = IOSColors.LightInversePrimary,
    surfaceTint = IOSColors.LightSurfaceTint,
    scrim = IOSColors.LightScrim
)

// Dark theme colors - iOS aesthetic inspired
private val DarkColorScheme = darkColorScheme(
    primary = IOSColors.DarkPrimary,
    onPrimary = IOSColors.DarkOnPrimary,
    primaryContainer = IOSColors.DarkPrimaryContainer,
    onPrimaryContainer = IOSColors.DarkOnPrimaryContainer,
    secondary = IOSColors.DarkSecondary,
    onSecondary = IOSColors.DarkOnSecondary,
    secondaryContainer = IOSColors.DarkSecondaryContainer,
    onSecondaryContainer = IOSColors.DarkOnSecondaryContainer,
    tertiary = IOSColors.DarkTertiary,
    onTertiary = IOSColors.DarkOnTertiary,
    tertiaryContainer = IOSColors.DarkTertiaryContainer,
    onTertiaryContainer = IOSColors.DarkOnTertiaryContainer,
    error = IOSColors.DarkError,
    errorContainer = IOSColors.DarkErrorContainer,
    onError = IOSColors.DarkOnError,
    onErrorContainer = IOSColors.DarkOnErrorContainer,
    background = IOSColors.DarkBackground,
    onBackground = IOSColors.DarkOnBackground,
    surface = IOSColors.DarkSurface,
    onSurface = IOSColors.DarkOnSurface,
    surfaceVariant = IOSColors.DarkSurfaceVariant,
    onSurfaceVariant = IOSColors.DarkOnSurfaceVariant,
    outline = IOSColors.DarkOutline,
    outlineVariant = IOSColors.DarkOutlineVariant,
    inverseSurface = IOSColors.DarkInverseSurface,
    inverseOnSurface = IOSColors.DarkInverseOnSurface,
    inversePrimary = IOSColors.DarkInversePrimary,
    surfaceTint = IOSColors.DarkSurfaceTint,
    scrim = IOSColors.DarkScrim
)

// iOS-inspired typography for Material3
private val AppTypography = Typography(
    // Display styles
    displayLarge = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 34.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 30.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 28.sp,
        letterSpacing = 0.sp
    ),

    // Headline styles (mapping from h1-h6)
    headlineLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),

    // Title styles (mapping from h5-h6)
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        letterSpacing = 0.sp
    ),

    // Body styles (mapping from body1, body2)
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        letterSpacing = 0.sp
    ),

    // Label styles (mapping from button, caption, overline)
    labelLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.sp
    )
)

// Theme state holder
class ThemeState {
    var isDarkTheme by mutableStateOf(false)

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }
}

// Composition local to provide theme state across the app
val LocalThemeState = compositionLocalOf { ThemeState() }

// Compatibility layer for Material Colors and Typography
private val LocalColors = staticCompositionLocalOf<Colors> {
    error("No Colors provided")
}

private val LocalTypography = staticCompositionLocalOf<androidx.compose.material.Typography> {
    error("No Typography provided")
}

// Extension properties to access Colors and Typography from MaterialTheme
// This allows existing code to continue using MaterialTheme.colors and MaterialTheme.typography
object MaterialTheme {
    val colors: Colors
        @Composable
        get() = LocalColors.current

    val typography: androidx.compose.material.Typography
        @Composable
        get() = LocalTypography.current
}

// Convert Material3 ColorScheme to Material Colors for compatibility
@Composable
private fun colorSchemeToColors(colorScheme: ColorScheme, isDark: Boolean): Colors {
    return if (isDark) {
        darkColors(
            primary = colorScheme.primary,
            primaryVariant = colorScheme.primaryContainer,
            secondary = colorScheme.secondary,
            secondaryVariant = colorScheme.secondaryContainer,
            background = colorScheme.background,
            surface = colorScheme.surface,
            error = colorScheme.error,
            onPrimary = colorScheme.onPrimary,
            onSecondary = colorScheme.onSecondary,
            onBackground = colorScheme.onBackground,
            onSurface = colorScheme.onSurface,
            onError = colorScheme.onError
        )
    } else {
        lightColors(
            primary = colorScheme.primary,
            primaryVariant = colorScheme.primaryContainer,
            secondary = colorScheme.secondary,
            secondaryVariant = colorScheme.secondaryContainer,
            background = colorScheme.background,
            surface = colorScheme.surface,
            error = colorScheme.error,
            onPrimary = colorScheme.onPrimary,
            onSecondary = colorScheme.onSecondary,
            onBackground = colorScheme.onBackground,
            onSurface = colorScheme.onSurface,
            onError = colorScheme.onError
        )
    }
}

// Convert Material3 Typography to Material Typography for compatibility
@Composable
private fun typographyToMaterialTypography(typography: Typography): androidx.compose.material.Typography {
    return androidx.compose.material.Typography(
        h1 = typography.displayLarge,
        h2 = typography.displayMedium,
        h3 = typography.displaySmall,
        h4 = typography.headlineLarge,
        h5 = typography.titleLarge,
        h6 = typography.titleMedium,
        subtitle1 = typography.bodyLarge,
        subtitle2 = typography.bodyMedium,
        body1 = typography.bodyLarge,
        body2 = typography.bodyMedium,
        button = typography.labelLarge,
        caption = typography.labelMedium,
        overline = typography.labelSmall
    )
}

@Composable
fun RadioGuyanaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Create or remember theme state
    val themeState = remember { ThemeState() }
    // Initialize with system theme or provided value
    themeState.isDarkTheme = darkTheme

    // Use the appropriate color scheme based on the theme state
    val colorScheme = if (themeState.isDarkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    // Create compatible objects for Material
    val colors = colorSchemeToColors(colorScheme, themeState.isDarkTheme)
    val materialTypography = typographyToMaterialTypography(AppTypography)

    // Provide the theme state, colors, and typography to the composition
    CompositionLocalProvider(
        LocalThemeState provides themeState,
        LocalColors provides colors,
        LocalTypography provides materialTypography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}

// Helper function to toggle theme
@Composable
fun rememberThemeState(): ThemeState {
    return LocalThemeState.current
}

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
internal fun AppTheme(
    content: @Composable () -> Unit
) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember(systemIsDark) { mutableStateOf(systemIsDark) }
    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState
    ) {
        val isDark by isDarkState
        SystemAppearance(!isDark)
        MaterialTheme(
            colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
            content = { Surface(content = content) }
        )
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)