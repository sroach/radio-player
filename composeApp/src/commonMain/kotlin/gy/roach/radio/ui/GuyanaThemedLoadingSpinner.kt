package gy.roach.radio.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gy.roach.radio.theme.GuyanaColors

/**
 * A culturally-themed loading spinner using Guyanese flag colors.
 */
@Composable
fun GuyanaLoadingSpinner(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    strokeWidth: Dp = 4.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "spinner")
    
    // Rotation animation
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // Sweep angle animation (expanding and contracting arc)
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 30f,
        targetValue = 270f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sweep"
    )

    // Color cycling through flag colors
    val colorIndex by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "colorCycle"
    )

    val colors = listOf(
        GuyanaColors.FlagGreen,
        GuyanaColors.FlagGold,
        GuyanaColors.FlagRed,
        GuyanaColors.FlagGreen // Loop back
    )

    val currentColor = when {
        colorIndex < 1f -> lerpColor(colors[0], colors[1], colorIndex)
        colorIndex < 2f -> lerpColor(colors[1], colors[2], colorIndex - 1f)
        else -> lerpColor(colors[2], colors[3], colorIndex - 2f)
    }

    Canvas(modifier = modifier.size(size)) {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            cap = StrokeCap.Round
        )

        rotate(rotation) {
            drawArc(
                color = currentColor,
                startAngle = 0f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = stroke,
                size = this.size.copy(
                    width = this.size.width - strokeWidth.toPx(),
                    height = this.size.height - strokeWidth.toPx()
                ),
                topLeft = Offset(strokeWidth.toPx() / 2, strokeWidth.toPx() / 2)
            )
        }
    }
}

/**
 * Linear interpolation between two colors.
 */
private fun lerpColor(start: Color, end: Color, fraction: Float): Color {
    return Color(
        red = start.red + (end.red - start.red) * fraction,
        green = start.green + (end.green - start.green) * fraction,
        blue = start.blue + (end.blue - start.blue) * fraction,
        alpha = start.alpha + (end.alpha - start.alpha) * fraction
    )
}
