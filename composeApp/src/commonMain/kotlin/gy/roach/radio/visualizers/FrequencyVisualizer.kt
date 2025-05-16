package gy.roach.radio.visualizers

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.random.Random

/**
 * A frequency visualizer that displays bars with colors from left to right:
 * red, orange, yellow, green, blue, purple.
 * Each color has 6 bars with decreasing tint from left to right.
 */
@Composable
fun FrequencyVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    // Define the colors for the visualizer
    val red = Color(0xFFFF0000)
    val orange = Color(0xFFFF8800)
    val yellow = Color(0xFFFFFF00)
    val green = Color(0xFF00FF00)
    val blue = Color(0xFF0000FF)
    val purple = Color(0xFF8800FF)
    
    // List of base colors
    val baseColors = listOf(red, orange, yellow, green, blue, purple)
    
    // Number of bars per color
    val barsPerColor = 6
    
    // Total number of bars
    val barCount = baseColors.size * barsPerColor
    
    val infiniteTransition = rememberInfiniteTransition()
    
    // Create animated heights for each bar
    val barHeights = List(barCount) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.1f, // Start with a small height
            targetValue = 0.8f + (Random.nextFloat() * 0.2f), // Random max height between 0.8 and 1.0
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 500 + Random.nextInt(1000),
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }
    
    Canvas(modifier = modifier.height(100.dp).fillMaxWidth()) {
        // Draw the border around the visualization area
        drawRect(
            color = Color.Gray,
            topLeft = Offset(0f, 0f),
            size = Size(size.width, size.height),
            style = Stroke(width = 2f)
        )
        
        if (isPlaying) {
            val barWidth = size.width / barCount
            
            barHeights.forEachIndexed { index, heightState ->
                // Calculate which color group this bar belongs to
                val colorIndex = index / barsPerColor
                val barInColorGroup = index % barsPerColor
                
                // Get the base color for this group
                val baseColor = baseColors[colorIndex]
                
                // Calculate the tint factor (decreasing from left to right within each color group)
                val tintFactor = 1.0f - (barInColorGroup * 0.25f)
                
                // Apply the tint to the base color
                val barColor = Color(
                    red = baseColor.red * tintFactor,
                    green = baseColor.green * tintFactor,
                    blue = baseColor.blue * tintFactor,
                    alpha = baseColor.alpha
                )
                
                // Calculate the height of this bar
                val height = size.height * heightState.value
                
                // Draw the bar
                drawRect(
                    color = barColor,
                    topLeft = Offset(index * barWidth, size.height - height),
                    size = Size(barWidth * 0.8f, height) // Make bars slightly narrower than their space
                )
            }
        }
    }
}