package gy.roach.radio.theme

import androidx.compose.ui.graphics.Color

// Light Mode - Minimalist Palette
object LightMinimalistColors {
    val background = Color(0xFFFDFDFD)          // Almost white, slightly warm
    val surface = Color(0xFFFFFFFF)             // Pure white
    val surfaceVariant = Color(0xFFF8F8F8)      // Very light gray
    val surfaceContainer = Color(0xFFF2F2F2)    // Light gray container

    val onBackground = Color(0xFF1C1C1E)        // Near black (iOS style)
    val onSurface = Color(0xFF1C1C1E)          // Near black
    val onSurfaceVariant = Color(0xFF6A6A6A)   // Medium gray for secondary text

    val primary = Color(0xFF2C2C2E)            // Dark gray for primary actions
    val onPrimary = Color(0xFFFFFFFF)          // White on primary
    val primaryContainer = Color(0xFFE8E8E8)   // Light gray for primary containers
    val onPrimaryContainer = Color(0xFF1C1C1E) // Dark text on light containers

    val secondary = Color(0xFF8E8E93)          // Medium gray (iOS systemGray)
    val onSecondary = Color(0xFFFFFFFF)        // White on secondary
    val secondaryContainer = Color(0xFFF2F2F7) // Very light gray
    val onSecondaryContainer = Color(0xFF1C1C1E)

    val tertiary = Color(0xFF6A6A6A)           // Darker gray for tertiary elements
    val onTertiary = Color(0xFFFFFFFF)

    val error = Color(0xFF2C2C2E)              // Dark gray instead of red for minimalism
    val onError = Color(0xFFFFFFFF)
    val errorContainer = Color(0xFFE8E8E8)
    val onErrorContainer = Color(0xFF1C1C1E)

    val outline = Color(0xFFE5E5EA)            // Very light gray for borders
    val outlineVariant = Color(0xFFF2F2F7)     // Even lighter for subtle borders
    val scrim = Color(0x80000000)              // Semi-transparent black

    // Custom colors for minimalist design
    val cardBackground = Color(0xFFFFFFFF)
    val divider = Color(0xFFE5E5EA)
    val shadow = Color(0x08000000)             // Very subtle shadow
    val accent = Color(0xFF1C1C1E)             // Dark accent for highlights
}

// Dark Mode - Minimalist Palette
object DarkMinimalistColors {
    val background = Color(0xFF000000)          // Pure black
    val surface = Color(0xFF1C1C1E)            // Very dark gray
    val surfaceVariant = Color(0xFF2C2C2E)     // Dark gray
    val surfaceContainer = Color(0xFF3A3A3C)   // Medium dark gray

    val onBackground = Color(0xFFFFFFFF)       // Pure white
    val onSurface = Color(0xFFFFFFFF)          // Pure white
    val onSurfaceVariant = Color(0xFFAEAEB2)   // Light gray for secondary text

    val primary = Color(0xFFFFFFFF)            // White for primary actions
    val onPrimary = Color(0xFF000000)          // Black on primary
    val primaryContainer = Color(0xFF2C2C2E)   // Dark gray for primary containers
    val onPrimaryContainer = Color(0xFFFFFFFF) // White text on dark containers

    val secondary = Color(0xFF8E8E93)          // Medium gray (consistent with light)
    val onSecondary = Color(0xFF000000)        // Black on secondary
    val secondaryContainer = Color(0xFF1C1C1E) // Very dark gray
    val onSecondaryContainer = Color(0xFFFFFFFF)

    val tertiary = Color(0xFFAEAEB2)           // Light gray for tertiary elements
    val onTertiary = Color(0xFF000000)

    val error = Color(0xFFFFFFFF)              // White instead of red for minimalism
    val onError = Color(0xFF000000)
    val errorContainer = Color(0xFF2C2C2E)
    val onErrorContainer = Color(0xFFFFFFFF)

    val outline = Color(0xFF3A3A3C)            // Dark gray for borders
    val outlineVariant = Color(0xFF2C2C2E)     // Darker for subtle borders
    val scrim = Color(0x80FFFFFF)              // Semi-transparent white

    // Custom colors for minimalist design
    val cardBackground = Color(0xFF1C1C1E)
    val divider = Color(0xFF3A3A3C)
    val shadow = Color(0x40FFFFFF)             // Subtle white shadow
    val accent = Color(0xFFFFFFFF)             // White accent for highlights
}