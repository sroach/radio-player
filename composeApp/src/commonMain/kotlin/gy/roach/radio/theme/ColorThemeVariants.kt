package gy.roach.radio.theme

import androidx.compose.ui.graphics.Color

/**
 * Modern color themes inspired by iOS design guidelines.
 * Each theme maintains accessibility standards and provides both light and dark variants.
 */
enum class ColorTheme(
    val displayName: String,
    val description: String,
    val accentColor: Color
) {
    MINIMALIST("Minimalist", "Clean blacks, whites, and grays", Color(0xFF1C1C1E)),
    OCEAN("Ocean", "Calming blues and teals", Color(0xFF007AFF)),
    FOREST("Forest", "Natural greens", Color(0xFF34C759)),
    SUNSET("Sunset", "Warm oranges and corals", Color(0xFFFF9500)),
    LAVENDER("Lavender", "Soft purples", Color(0xFFAF52DE)),
    ROSE("Rose", "Elegant pinks", Color(0xFFFF2D92))
}

// Minimalist Theme (existing)
object MinimalistTheme {
    val lightColors = LightMinimalistColors
    val darkColors = DarkMinimalistColors
}

// Ocean Theme
object OceanTheme {
    object Light {
        val background = Color(0xFFFAFCFF)
        val surface = Color(0xFFFFFFFF)
        val surfaceVariant = Color(0xFFF5F9FF)
        val primary = Color(0xFF007AFF)
        val onPrimary = Color(0xFFFFFFFF)
        val primaryContainer = Color(0xFFD1E7FF)
        val onPrimaryContainer = Color(0xFF001D36)
        val secondary = Color(0xFF5AC8FA)
        val onSecondary = Color(0xFF003547)
        val tertiary = Color(0xFF32D3E0)
        val onTertiary = Color(0xFF00363D)
        val onBackground = Color(0xFF1A1C1E)
        val onSurface = Color(0xFF1A1C1E)
        val onSurfaceVariant = Color(0xFF42474E)
        val outline = Color(0xFFD4E7F7)
        val outlineVariant = Color(0xFFEBF4FD)
    }
    
    object Dark {
        val background = Color(0xFF0A1929)
        val surface = Color(0xFF1E293B)
        val surfaceVariant = Color(0xFF334155)
        val primary = Color(0xFF0EA5E9)
        val onPrimary = Color(0xFFFFFFFF)
        val primaryContainer = Color(0xFF0369A1)
        val onPrimaryContainer = Color(0xFFBAE6FD)
        val secondary = Color(0xFF38BDF8)
        val onSecondary = Color(0xFF0F172A)
        val tertiary = Color(0xFF22D3EE)
        val onTertiary = Color(0xFF164E63)
        val onBackground = Color(0xFFE2E8F0)
        val onSurface = Color(0xFFE2E8F0)
        val onSurfaceVariant = Color(0xFF94A3B8)
        val outline = Color(0xFF475569)
        val outlineVariant = Color(0xFF334155)
    }
}

// Forest Theme
object ForestTheme {
    object Light {
        val background = Color(0xFFF7FDF7)
        val surface = Color(0xFFFFFFFF)
        val surfaceVariant = Color(0xFFF0F9F0)
        val primary = Color(0xFF34C759)
        val onPrimary = Color(0xFFFFFFFF)
        val primaryContainer = Color(0xFFBBF7D0)
        val onPrimaryContainer = Color(0xFF052E16)
        val secondary = Color(0xFF10B981)
        val onSecondary = Color(0xFFFFFFFF)
        val tertiary = Color(0xFF059669)
        val onTertiary = Color(0xFFFFFFFF)
        val onBackground = Color(0xFF1A1C1A)
        val onSurface = Color(0xFF1A1C1A)
        val onSurfaceVariant = Color(0xFF424940)
        val outline = Color(0xFFD1F5D3)
        val outlineVariant = Color(0xFFE8F8EA)
    }
    
    object Dark {
        val background = Color(0xFF0F1B0F)
        val surface = Color(0xFF1A2E1A)
        val surfaceVariant = Color(0xFF2D4A2D)
        val primary = Color(0xFF22C55E)
        val onPrimary = Color(0xFF052E16)
        val primaryContainer = Color(0xFF166534)
        val onPrimaryContainer = Color(0xFFBBF7D0)
        val secondary = Color(0xFF10B981)
        val onSecondary = Color(0xFF064E3B)
        val tertiary = Color(0xFF059669)
        val onTertiary = Color(0xFF064E3B)
        val onBackground = Color(0xFFE0F2E0)
        val onSurface = Color(0xFFE0F2E0)
        val onSurfaceVariant = Color(0xFF9CA69C)
        val outline = Color(0xFF4A5A4A)
        val outlineVariant = Color(0xFF2D4A2D)
    }
}

// Sunset Theme
object SunsetTheme {
    object Light {
        val background = Color(0xFFFFFAF5)
        val surface = Color(0xFFFFFFFF)
        val surfaceVariant = Color(0xFFFFF7F0)
        val primary = Color(0xFFFF9500)
        val onPrimary = Color(0xFFFFFFFF)
        val primaryContainer = Color(0xFFFFE4CC)
        val onPrimaryContainer = Color(0xFF2E1B00)
        val secondary = Color(0xFFFF8C42)
        val onSecondary = Color(0xFFFFFFFF)
        val tertiary = Color(0xFFFF6B6B)
        val onTertiary = Color(0xFFFFFFFF)
        val onBackground = Color(0xFF1C1A1A)
        val onSurface = Color(0xFF1C1A1A)
        val onSurfaceVariant = Color(0xFF4A4240)
        val outline = Color(0xFFFDE4CC)
        val outlineVariant = Color(0xFFFDF2E6)
    }
    
    object Dark {
        val background = Color(0xFF1F0F0A)
        val surface = Color(0xFF2E1A12)
        val surfaceVariant = Color(0xFF4A2C1F)
        val primary = Color(0xFFFF9500)
        val onPrimary = Color(0xFF2E1B00)
        val primaryContainer = Color(0xFF8B4000)
        val onPrimaryContainer = Color(0xFFFFE4CC)
        val secondary = Color(0xFFFF8C42)
        val onSecondary = Color(0xFF2E1B00)
        val tertiary = Color(0xFFFF6B6B)
        val onTertiary = Color(0xFF2E0A0A)
        val onBackground = Color(0xFFF0E8E0)
        val onSurface = Color(0xFFF0E8E0)
        val onSurfaceVariant = Color(0xFFA69C94)
        val outline = Color(0xFF5A4A3F)
        val outlineVariant = Color(0xFF4A2C1F)
    }
}

// Lavender Theme
object LavenderTheme {
    object Light {
        val background = Color(0xFFFAF9FF)
        val surface = Color(0xFFFFFFFF)
        val surfaceVariant = Color(0xFFF5F3FF)
        val primary = Color(0xFFAF52DE)
        val onPrimary = Color(0xFFFFFFFF)
        val primaryContainer = Color(0xFFE9D5FF)
        val onPrimaryContainer = Color(0xFF2E0A4A)
        val secondary = Color(0xFF8B5CF6)
        val onSecondary = Color(0xFFFFFFFF)
        val tertiary = Color(0xFFA855F7)
        val onTertiary = Color(0xFFFFFFFF)
        val onBackground = Color(0xFF1A1A1C)
        val onSurface = Color(0xFF1A1A1C)
        val onSurfaceVariant = Color(0xFF4A424E)
        val outline = Color(0xFFE9D5FF)
        val outlineVariant = Color(0xFFF3EAFF)
    }
    
    object Dark {
        val background = Color(0xFF15101F)
        val surface = Color(0xFF1F1A2E)
        val surfaceVariant = Color(0xFF332D4A)
        val primary = Color(0xFFAF52DE)
        val onPrimary = Color(0xFF2E0A4A)
        val primaryContainer = Color(0xFF6B2C91)
        val onPrimaryContainer = Color(0xFFE9D5FF)
        val secondary = Color(0xFF8B5CF6)
        val onSecondary = Color(0xFF2E0A4A)
        val tertiary = Color(0xFFA855F7)
        val onTertiary = Color(0xFF2E0A4A)
        val onBackground = Color(0xFFEDE8F0)
        val onSurface = Color(0xFFEDE8F0)
        val onSurfaceVariant = Color(0xFF9C94A6)
        val outline = Color(0xFF4A4250)
        val outlineVariant = Color(0xFF332D4A)
    }
}

// Rose Theme
object RoseTheme {
    object Light {
        val background = Color(0xFFFFF7F8)
        val surface = Color(0xFFFFFFFF)
        val surfaceVariant = Color(0xFFFFF0F2)
        val primary = Color(0xFFFF2D92)
        val onPrimary = Color(0xFFFFFFFF)
        val primaryContainer = Color(0xFFFFDAE6)
        val onPrimaryContainer = Color(0xFF3D0A1E)
        val secondary = Color(0xFFEC4899)
        val onSecondary = Color(0xFFFFFFFF)
        val tertiary = Color(0xFFF43F5E)
        val onTertiary = Color(0xFFFFFFFF)
        val onBackground = Color(0xFF1C1A1B)
        val onSurface = Color(0xFF1C1A1B)
        val onSurfaceVariant = Color(0xFF4A4245)
        val outline = Color(0xFFFFDAE6)
        val outlineVariant = Color(0xFFFFF0F2)
    }
    
    object Dark {
        val background = Color(0xFF1F0A14)
        val surface = Color(0xFF2E1220)
        val surfaceVariant = Color(0xFF4A1F33)
        val primary = Color(0xFFFF2D92)
        val onPrimary = Color(0xFF3D0A1E)
        val primaryContainer = Color(0xFF91204A)
        val onPrimaryContainer = Color(0xFFFFDAE6)
        val secondary = Color(0xFFEC4899)
        val onSecondary = Color(0xFF3D0A1E)
        val tertiary = Color(0xFFF43F5E)
        val onTertiary = Color(0xFF3D0A14)
        val onBackground = Color(0xFFF0E8EB)
        val onSurface = Color(0xFFF0E8EB)
        val onSurfaceVariant = Color(0xFFA6949C)
        val outline = Color(0xFF5A424A)
        val outlineVariant = Color(0xFF4A1F33)
    }
}
