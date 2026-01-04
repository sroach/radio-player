package gy.roach.radio.theme

import androidx.compose.ui.graphics.Color

/**
 * 🇬🇾 Culturally-rooted themes inspired by Guyana's identity.
 * Each theme tells a story of the land, people, and spirit of the nation.
 */
enum class ColorTheme(
    val displayName: String,
    val description: String,
    val accentColor: Color
) {
    GOLDEN_ARROWHEAD("Golden Arrowhead", "Pride of the flag – gold & green", GuyanaColors.GoldVibrant),
    RAINFOREST_DUSK("Rainforest Dusk", "Deep jungle greens with sunset warmth", GuyanaColors.ForestMid),
    KAIETEUR_MIST("Kaieteur Mist", "Refreshing teals of the great falls", GuyanaColors.KaieteurTeal),
    STABROEK_NIGHTS("Stabroek Nights", "Urban Georgetown after dark", GuyanaColors.StabroekNeon)
}

// === GOLDEN ARROWHEAD THEME ===
object GoldenArrowheadTheme {
    object Light {
        val background = Color(0xFFFFFDF5)        // Warm cream
        val surface = Color(0xFFFFFFFF)
        val surfaceVariant = Color(0xFFFFF9E6)    // Soft gold tint
        val primary = GuyanaColors.FlagGreen
        val onPrimary = Color(0xFFFFFFFF)
        val primaryContainer = Color(0xFFB8E6C4)  // Light green
        val onPrimaryContainer = Color(0xFF003314)
        val secondary = GuyanaColors.GoldVibrant
        val onSecondary = Color(0xFF1A1200)
        val tertiary = GuyanaColors.FlagRed
        val onTertiary = Color(0xFFFFFFFF)
        val onBackground = Color(0xFF1A1C1A)
        val onSurface = Color(0xFF1A1C1A)
        val onSurfaceVariant = Color(0xFF4A5247)
        val outline = Color(0xFFD4C896)           // Gold-tinged outline
        val outlineVariant = Color(0xFFF0E8D0)
    }

    object Dark {
        val background = Color(0xFF0A0F0C)        // Deep black-green
        val surface = Color(0xFF141F18)           // Forest surface
        val surfaceVariant = Color(0xFF1F2E24)
        val primary = GuyanaColors.GoldVibrant
        val onPrimary = Color(0xFF1A1200)
        val primaryContainer = Color(0xFF5C4D00)
        val onPrimaryContainer = Color(0xFFFFE066)
        val secondary = GuyanaColors.ForestLight
        val onSecondary = Color(0xFF003314)
        val tertiary = Color(0xFFFF6B6B)          // Softened red
        val onTertiary = Color(0xFF3D0A0A)
        val onBackground = Color(0xFFF0F5F2)
        val onSurface = Color(0xFFF0F5F2)
        val onSurfaceVariant = Color(0xFFA8B5AB)
        val outline = Color(0xFF4A5A4D)
        val outlineVariant = Color(0xFF2D3D32)
    }
}

// === RAINFOREST DUSK THEME ===
object RainforestDuskTheme {
    object Light {
        val background = GuyanaColors.ForestMist
        val surface = Color(0xFFFFFFFF)
        val surfaceVariant = Color(0xFFE0F2E5)
        val primary = GuyanaColors.ForestDeep
        val onPrimary = Color(0xFFFFFFFF)
        val primaryContainer = Color(0xFFA5D6B3)
        val onPrimaryContainer = Color(0xFF002110)
        val secondary = GuyanaColors.GoldDeep
        val onSecondary = Color(0xFFFFFFFF)
        val tertiary = Color(0xFFFF7043)          // Sunset orange
        val onTertiary = Color(0xFFFFFFFF)
        val onBackground = Color(0xFF161D18)
        val onSurface = Color(0xFF161D18)
        val onSurfaceVariant = Color(0xFF3D4A40)
        val outline = Color(0xFFC4D9CC)
        val outlineVariant = Color(0xFFDFEEE4)
    }

    object Dark {
        val background = GuyanaColors.ForestDusk
        val surface = Color(0xFF0F2918)
        val surfaceVariant = Color(0xFF1A3D26)
        val primary = Color(0xFF7BC47F)           // Soft luminous green
        val onPrimary = Color(0xFF002110)
        val primaryContainer = Color(0xFF004D26)
        val onPrimaryContainer = Color(0xFFA5D6B3)
        val secondary = GuyanaColors.GoldMuted
        val onSecondary = Color(0xFF2E2400)
        val tertiary = Color(0xFFFFAB91)          // Warm sunset
        val onTertiary = Color(0xFF3D1400)
        val onBackground = Color(0xFFDEECE2)
        val onSurface = Color(0xFFDEECE2)
        val onSurfaceVariant = Color(0xFF9DAA9F)
        val outline = Color(0xFF4D5F52)
        val outlineVariant = Color(0xFF2D3D32)
    }
}

// === KAIETEUR MIST THEME ===
object KaieteurMistTheme {
    object Light {
        val background = GuyanaColors.KaieteurMist
        val surface = Color(0xFFFFFFFF)
        val surfaceVariant = Color(0xFFD4F5F9)
        val primary = GuyanaColors.KaieteurDeep
        val onPrimary = Color(0xFFFFFFFF)
        val primaryContainer = Color(0xFF97E3EC)
        val onPrimaryContainer = Color(0xFF00262B)
        val secondary = GuyanaColors.ForestMid
        val onSecondary = Color(0xFFFFFFFF)
        val tertiary = GuyanaColors.GoldVibrant
        val onTertiary = Color(0xFF1A1200)
        val onBackground = Color(0xFF161D1E)
        val onSurface = Color(0xFF161D1E)
        val onSurfaceVariant = Color(0xFF3D494B)
        val outline = Color(0xFFB0D7DC)
        val outlineVariant = Color(0xFFD4ECEF)
    }

    object Dark {
        val background = Color(0xFF031517)        // Deep water
        val surface = Color(0xFF0A2529)
        val surfaceVariant = Color(0xFF153338)
        val primary = GuyanaColors.KaieteurCyan
        val onPrimary = Color(0xFF00262B)
        val primaryContainer = Color(0xFF006570)
        val onPrimaryContainer = Color(0xFF97E3EC)
        val secondary = Color(0xFF81C784)         // Soft green
        val onSecondary = Color(0xFF002110)
        val tertiary = Color(0xFFFFD54F)          // Warm gold
        val onTertiary = Color(0xFF2E2400)
        val onBackground = Color(0xFFDCECEE)
        val onSurface = Color(0xFFDCECEE)
        val onSurfaceVariant = Color(0xFF9DAAAC)
        val outline = Color(0xFF4D5A5C)
        val outlineVariant = Color(0xFF2D3A3D)
    }
}

// === STABROEK NIGHTS THEME ===
object StabroekNightsTheme {
    object Light {
        val background = Color(0xFFF5F5F5)
        val surface = Color(0xFFFFFFFF)
        val surfaceVariant = Color(0xFFEBEBEB)
        val primary = Color(0xFF1A1A1A)           // Deep black
        val onPrimary = Color(0xFFFFFFFF)
        val primaryContainer = Color(0xFFD4D4D4)
        val onPrimaryContainer = Color(0xFF1A1A1A)
        val secondary = GuyanaColors.StabroekNeon
        val onSecondary = Color(0xFF001A0D)
        val tertiary = GuyanaColors.StabroekGlow
        val onTertiary = Color(0xFF1A1200)
        val onBackground = Color(0xFF1A1A1A)
        val onSurface = Color(0xFF1A1A1A)
        val onSurfaceVariant = Color(0xFF5A5A5A)
        val outline = Color(0xFFD4D4D4)
        val outlineVariant = Color(0xFFE8E8E8)
    }

    object Dark {
        val background = GuyanaColors.StabroekNight
        val surface = Color(0xFF1A1A1A)
        val surfaceVariant = GuyanaColors.StabroekMetal
        val primary = GuyanaColors.StabroekNeon   // Vibrant neon green
        val onPrimary = Color(0xFF001A0D)
        val primaryContainer = Color(0xFF003D1F)
        val onPrimaryContainer = GuyanaColors.StabroekNeon
        val secondary = GuyanaColors.StabroekGlow
        val onSecondary = Color(0xFF1A1200)
        val tertiary = GuyanaColors.StabroekLamp
        val onTertiary = Color(0xFF1A0D00)
        val onBackground = Color(0xFFF0F0F0)
        val onSurface = Color(0xFFF0F0F0)
        val onSurfaceVariant = Color(0xFFB0B0B0)
        val outline = Color(0xFF404040)
        val outlineVariant = Color(0xFF2D2D2D)
    }
}

// Keep minimalist theme for compatibility but rename
object MinimalistTheme {
    val lightColors = LightMinimalistColors
    val darkColors = DarkMinimalistColors
}