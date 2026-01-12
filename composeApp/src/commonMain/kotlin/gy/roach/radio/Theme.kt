package gy.roach.radio


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import gy.roach.radio.theme.ColorTheme
import gy.roach.radio.theme.GoldenArrowheadTheme
import gy.roach.radio.theme.KaieteurMistTheme
import gy.roach.radio.theme.RainforestDuskTheme
import gy.roach.radio.theme.StabroekNightsTheme
import gy.roach.radio.theme.ThemeSettings


fun buildLightColorScheme(theme: ColorTheme): ColorScheme = when (theme) {
    ColorTheme.GOLDEN_ARROWHEAD -> lightColorScheme(
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
    ColorTheme.RAINFOREST_DUSK -> lightColorScheme(
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
    ColorTheme.KAIETEUR_MIST -> lightColorScheme(
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
    ColorTheme.STABROEK_NIGHTS -> lightColorScheme(
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
}

fun buildDarkColorScheme(theme: ColorTheme): ColorScheme = when (theme) {
    ColorTheme.GOLDEN_ARROWHEAD -> darkColorScheme(
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
    ColorTheme.RAINFOREST_DUSK -> darkColorScheme(
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
    ColorTheme.KAIETEUR_MIST -> darkColorScheme(
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
    ColorTheme.STABROEK_NIGHTS -> darkColorScheme(
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
// === TYPOGRAPHY ===
// Using distinctive weights and sizes - will add custom fonts in a later phase
val AppTypography = Typography(
    // Display styles - bold and impactful
    displayLarge = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 36.sp,
        letterSpacing = (-0.5).sp
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = (-0.25).sp
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = 0.sp
    ),

    // Headline styles - strong presence
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = (-0.25).sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),

    // Title styles - clear hierarchy
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),

    // Body styles - readable and warm
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        lineHeight = 16.sp
    ),

    // Label styles - crisp and clear
    labelLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        letterSpacing = 0.5.sp
    )
)

// === THEME STATE ===
class ThemeState {
    var isDarkTheme by mutableStateOf(false)
    var currentTheme by mutableStateOf(ColorTheme.GOLDEN_ARROWHEAD)

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }

    fun setTheme(theme: ColorTheme) {
        currentTheme = theme
    }
}

// Composition local to provide theme state across the app
val LocalThemeState = compositionLocalOf { ThemeState() }

// Compatibility layer for Material Colors and Typography



// Convert Material3 ColorScheme to Material Colors for compatibility


@Composable
fun RadioGuyanaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorTheme: ColorTheme = ColorTheme.GOLDEN_ARROWHEAD,
    content: @Composable () -> Unit
) {
    val themeState = remember { ThemeState() }
    themeState.isDarkTheme = darkTheme
    themeState.currentTheme = colorTheme

    val colorScheme = if (themeState.isDarkTheme) {
        buildDarkColorScheme(themeState.currentTheme)
    } else {
        buildLightColorScheme(themeState.currentTheme)
    }

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
    themeSettings: ThemeSettings,
    content: @Composable () -> Unit
) {
    val systemIsDark = isSystemInDarkTheme()
    // Collect the preference flow. Use system setting as default if preference is null.
    val userPreference by themeSettings.isDarkTheme.collectAsState(initial = null)

    val isDark = userPreference ?: systemIsDark
    val isDarkState = remember(isDark) { mutableStateOf(isDark) }
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