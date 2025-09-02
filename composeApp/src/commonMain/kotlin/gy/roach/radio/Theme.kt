package gy.roach.radio


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
val AppTypography = Typography(
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



// Convert Material3 ColorScheme to Material Colors for compatibility


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



    // Provide the theme state, colors, and typography to the composition
    CompositionLocalProvider(
        LocalThemeState provides themeState

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
