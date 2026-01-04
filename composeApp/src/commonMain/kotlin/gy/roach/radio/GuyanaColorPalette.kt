
package gy.roach.radio

import androidx.compose.ui.graphics.Color

// ═══════════════════════════════════════════════════════════════════════════════
// 🇬🇾 GUYANESE-INSPIRED COLOR PALETTE
// Inspired by the Golden Arrowhead flag, Kaieteur Falls, and tropical landscapes
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Primary cultural colors derived from Guyana's national identity
 */
object GuyanaColors {
    // Flag Colors - The Golden Arrowhead 🇬🇾
    val FlagGreen = Color(0xFF009739)       // Rich jungle green
    val FlagGold = Color(0xFFFFCD00)        // Golden arrowhead
    val FlagRed = Color(0xFFCE1126)         // Crimson border
    val FlagBlack = Color(0xFF000000)       // Unity
    val FlagWhite = Color(0xFFFFFFFF)       // Waters

    // Rainforest Palette
    val DeepJungle = Color(0xFF004B23)      // Dense canopy
    val Canopy = Color(0xFF006633)          // Mid-forest green
    val Emerald = Color(0xFF2D6A4F)         // Sunlit leaves
    val Fern = Color(0xFF40916C)            // Young growth
    val MossLight = Color(0xFF74C69D)       // Morning dew

    // Kaieteur Falls Palette
    val FallsMist = Color(0xFF64B5C4)       // Misty spray
    val TealDepth = Color(0xFF006D77)       // Deep pool
    val AquaGlow = Color(0xFF83C5BE)        // Sunlit water
    val SkyReflection = Color(0xFFB8E0D4)   // Light reflection

    // Golden Hour / Savannah
    val SunsetGold = Color(0xFFF9C74F)      // Evening sun
    val AmberWarm = Color(0xFFF8961E)       // Warm amber
    val CoralBlush = Color(0xFFF3722C)      // Horizon glow
    val RupununiSand = Color(0xFFE8D5B7)    // Savannah earth

    // Urban Georgetown
    val StabroekCharcoal = Color(0xFF1A1A2E) // Night market
    val WoodRich = Color(0xFF3D2914)         // Colonial wood
    val CopperAccent = Color(0xFFD4A574)     // Heritage copper
    val NeonTeal = Color(0xFF00FFC6)         // Modern accent
}

/**
 * Semantic color mappings for the app themes
 */
object RadioColors {
    // Dynamic accent based on current theme
    val AccentPrimary = GuyanaColors.FlagGold
    val AccentSecondary = GuyanaColors.FlagGreen
    val AccentTertiary = GuyanaColors.TealDepth

    // Status colors (culturally adapted)
    val Playing = GuyanaColors.FlagGreen
    val Paused = GuyanaColors.AmberWarm
    val Error = GuyanaColors.FlagRed
    val Loading = GuyanaColors.FallsMist
}

// ═══════════════════════════════════════════════════════════════════════════════
// Legacy iOS Colors (kept for backward compatibility during migration)
// ═══════════════════════════════════════════════════════════════════════════════

object IOSColors {
    // Light theme colors
    val LightPrimary = Color(0xFF007AFF)
    val LightOnPrimary = Color(0xFFFFFFFF)
    val LightPrimaryContainer = Color(0xFFD1E7FF)
    val LightOnPrimaryContainer = Color(0xFF001D36)
    val LightSecondary = Color(0xFF5AC8FA)
    val LightOnSecondary = Color(0xFFFFFFFF)
    val LightSecondaryContainer = Color(0xFFD1F2FF)
    val LightOnSecondaryContainer = Color(0xFF003547)
    val LightTertiary = Color(0xFF32D3E0)
    val LightOnTertiary = Color(0xFFFFFFFF)
    val LightTertiaryContainer = Color(0xFFD1F7FF)
    val LightOnTertiaryContainer = Color(0xFF00363D)
    val LightError = Color(0xFFFF3B30)
    val LightErrorContainer = Color(0xFFFFDAD6)
    val LightOnError = Color(0xFFFFFFFF)
    val LightOnErrorContainer = Color(0xFF410002)
    val LightBackground = Color(0xFFF2F2F7)
    val LightOnBackground = Color(0xFF1C1C1E)
    val LightSurface = Color(0xFFFFFFFF)
    val LightOnSurface = Color(0xFF1C1C1E)
    val LightSurfaceVariant = Color(0xFFE5E5EA)
    val LightOnSurfaceVariant = Color(0xFF48484A)
    val LightOutline = Color(0xFFC7C7CC)
    val LightOutlineVariant = Color(0xFFE5E5EA)
    val LightInverseSurface = Color(0xFF2C2C2E)
    val LightInverseOnSurface = Color(0xFFF2F2F7)
    val LightInversePrimary = Color(0xFF32D74B)
    val LightSurfaceTint = Color(0xFF007AFF)
    val LightScrim = Color(0xFF000000)

    // Dark theme colors
    val DarkPrimary = Color(0xFF0A84FF)
    val DarkOnPrimary = Color(0xFF000000)
    val DarkPrimaryContainer = Color(0xFF004481)
    val DarkOnPrimaryContainer = Color(0xFFD1E7FF)
    val DarkSecondary = Color(0xFF64D2FF)
    val DarkOnSecondary = Color(0xFF000000)
    val DarkSecondaryContainer = Color(0xFF003547)
    val DarkOnSecondaryContainer = Color(0xFFD1F2FF)
    val DarkTertiary = Color(0xFF40E0D0)
    val DarkOnTertiary = Color(0xFF000000)
    val DarkTertiaryContainer = Color(0xFF00363D)
    val DarkOnTertiaryContainer = Color(0xFFD1F7FF)
    val DarkError = Color(0xFFFF453A)
    val DarkErrorContainer = Color(0xFF93000A)
    val DarkOnError = Color(0xFF000000)
    val DarkOnErrorContainer = Color(0xFFFFDAD6)
    val DarkBackground = Color(0xFF000000)
    val DarkOnBackground = Color(0xFFF2F2F7)
    val DarkSurface = Color(0xFF1C1C1E)
    val DarkOnSurface = Color(0xFFF2F2F7)
    val DarkSurfaceVariant = Color(0xFF2C2C2E)
    val DarkOnSurfaceVariant = Color(0xFF8E8E93)
    val DarkOutline = Color(0xFF48484A)
    val DarkOutlineVariant = Color(0xFF2C2C2E)
    val DarkInverseSurface = Color(0xFFF2F2F7)
    val DarkInverseOnSurface = Color(0xFF1C1C1E)
    val DarkInversePrimary = Color(0xFF007AFF)
    val DarkSurfaceTint = Color(0xFF0A84FF)
    val DarkScrim = Color(0xFF000000)
}
