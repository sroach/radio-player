package gy.roach.radio.theme

import androidx.compose.ui.graphics.Color

/**
 * 🇬🇾 Guyanese-inspired color palette
 * 
 * Colors derived from:
 * - The Golden Arrowhead flag (green, gold, red, black, white)
 * - Tropical rainforest and Kaieteur Falls
 * - Georgetown's colonial architecture and Stabroek market
 */
object GuyanaColors {
    // === Flag Colors (Primary Identity) ===
    val FlagGreen = Color(0xFF009739)       // Official flag green
    val FlagGold = Color(0xFFFFD100)        // Golden arrowhead
    val FlagRed = Color(0xFFCE1126)         // Inner triangle
    val FlagBlack = Color(0xFF000000)       // Border trim
    val FlagWhite = Color(0xFFFFFFFF)       // Border trim
    
    // === Golden Arrowhead Theme ===
    val GoldLight = Color(0xFFFFF8DC)       // Cornsilk - soft gold background
    val GoldVibrant = Color(0xFFFFB800)     // Vibrant gold accent
    val GoldDeep = Color(0xFFD4A000)        // Deep gold for text
    val GoldMuted = Color(0xFFE6C84A)       // Muted gold for surfaces
    
    // === Rainforest Theme ===
    val ForestDeep = Color(0xFF0D3B1E)      // Deep jungle green
    val ForestMid = Color(0xFF1B5E3A)       // Mid canopy green
    val ForestLight = Color(0xFF2E7D4E)     // Sunlit leaves
    val ForestMist = Color(0xFFE8F5EE)      // Morning mist
    val ForestDusk = Color(0xFF051F10)      // Dusk in the canopy
    
    // === Kaieteur Theme (Waterfalls) ===
    val KaieteurMist = Color(0xFFE0F7FA)    // Waterfall mist
    val KaieteurTeal = Color(0xFF00838F)    // Deep pool teal
    val KaieteurCyan = Color(0xFF26C6DA)    // Cascading water
    val KaieteurSpray = Color(0xFFB2EBF2)   // Light spray
    val KaieteurDeep = Color(0xFF004D54)    // Deep water
    
    // === Stabroek Nights (Urban Georgetown) ===
    val StabroekNight = Color(0xFF0D0D0D)   // Deep night black
    val StabroekNeon = Color(0xFF00FF87)    // Neon accent
    val StabroekGlow = Color(0xFFFFE66D)    // Warm streetlight
    val StabroekMetal = Color(0xFF2D2D2D)   // Market corrugated iron
    val StabroekLamp = Color(0xFFFFA500)    // Lamp orange
    
    // === Semantic Accents ===
    val SuccessGreen = Color(0xFF00C853)    // Bright success
    val WarningAmber = Color(0xFFFFAB00)    // Attention amber
    val ErrorRed = Color(0xFFD50000)        // Error state
    
    // === Surface Tones ===
    val SurfaceLight = Color(0xFFFAFDF8)    // Warm white with green tint
    val SurfaceDark = Color(0xFF121A14)     // Dark with green undertone
    val SurfaceMid = Color(0xFF1E2820)      // Mid-dark surface
}
