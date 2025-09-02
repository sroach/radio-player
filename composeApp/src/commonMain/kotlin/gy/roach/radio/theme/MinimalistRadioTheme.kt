package gy.roach.radio.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Minimalist Typography for clean design
private val MinimalistTypography = Typography(
    // Display styles - clean and minimal
    displayLarge = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 32.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 28.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),

    // Headline styles - minimal hierarchy
    headlineLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),

    // Title styles - clean and readable
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.1.sp
    ),

    // Body styles - optimal readability
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),

    // Label styles - subtle and clean
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
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
        fontSize = 10.sp,
        letterSpacing = 0.5.sp
    )
)

private val MinimalistLightColorScheme = lightColorScheme(
    primary = LightMinimalistColors.primary,
    onPrimary = LightMinimalistColors.onPrimary,
    primaryContainer = LightMinimalistColors.primaryContainer,
    onPrimaryContainer = LightMinimalistColors.onPrimaryContainer,
    
    secondary = LightMinimalistColors.secondary,
    onSecondary = LightMinimalistColors.onSecondary,
    secondaryContainer = LightMinimalistColors.secondaryContainer,
    onSecondaryContainer = LightMinimalistColors.onSecondaryContainer,
    
    tertiary = LightMinimalistColors.tertiary,
    onTertiary = LightMinimalistColors.onTertiary,
    
    error = LightMinimalistColors.error,
    onError = LightMinimalistColors.onError,
    errorContainer = LightMinimalistColors.errorContainer,
    onErrorContainer = LightMinimalistColors.onErrorContainer,
    
    background = LightMinimalistColors.background,
    onBackground = LightMinimalistColors.onBackground,
    
    surface = LightMinimalistColors.surface,
    onSurface = LightMinimalistColors.onSurface,
    surfaceVariant = LightMinimalistColors.surfaceVariant,
    onSurfaceVariant = LightMinimalistColors.onSurfaceVariant,
    
    outline = LightMinimalistColors.outline,
    outlineVariant = LightMinimalistColors.outlineVariant,
    scrim = LightMinimalistColors.scrim
)

private val MinimalistDarkColorScheme = darkColorScheme(
    primary = DarkMinimalistColors.primary,
    onPrimary = DarkMinimalistColors.onPrimary,
    primaryContainer = DarkMinimalistColors.primaryContainer,
    onPrimaryContainer = DarkMinimalistColors.onPrimaryContainer,
    
    secondary = DarkMinimalistColors.secondary,
    onSecondary = DarkMinimalistColors.onSecondary,
    secondaryContainer = DarkMinimalistColors.secondaryContainer,
    onSecondaryContainer = DarkMinimalistColors.onSecondaryContainer,
    
    tertiary = DarkMinimalistColors.tertiary,
    onTertiary = DarkMinimalistColors.onTertiary,
    
    error = DarkMinimalistColors.error,
    onError = DarkMinimalistColors.onError,
    errorContainer = DarkMinimalistColors.errorContainer,
    onErrorContainer = DarkMinimalistColors.onErrorContainer,
    
    background = DarkMinimalistColors.background,
    onBackground = DarkMinimalistColors.onBackground,
    
    surface = DarkMinimalistColors.surface,
    onSurface = DarkMinimalistColors.onSurface,
    surfaceVariant = DarkMinimalistColors.surfaceVariant,
    onSurfaceVariant = DarkMinimalistColors.onSurfaceVariant,
    
    outline = DarkMinimalistColors.outline,
    outlineVariant = DarkMinimalistColors.outlineVariant,
    scrim = DarkMinimalistColors.scrim
)

@Composable
fun MinimalistRadioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> MinimalistDarkColorScheme
        else -> MinimalistLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MinimalistTypography,
        content = content
    )
}
