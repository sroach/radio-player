package gy.roach.radio.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Animated sun/moon toggle button with smooth morphing transition.
 */
@Composable
fun AnimatedThemeToggle(
    isDark: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    // Capture background color in composable context (before Canvas)
    val backgroundColor = MaterialTheme.colorScheme.background

    // Rotation animation for the sun rays
    val infiniteTransition = rememberInfiniteTransition(label = "sunRotation")
    val rayRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rayRotation"
    )

    // Transition progress (0 = sun, 1 = moon)
    val transitionProgress by animateFloatAsState(
        targetValue = if (isDark) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "themeTransition"
    )

    // Scale bounce on toggle
    var justToggled by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (justToggled) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "toggleScale"
    )

    LaunchedEffect(justToggled) {
        if (justToggled) {
            kotlinx.coroutines.delay(150)
            justToggled = false
        }
    }

    IconButton(
        onClick = {
            justToggled = true
            onToggle()
        },
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = tint
        )
    ) {
        Canvas(
            modifier = Modifier.size(24.dp)
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            val baseRadius = size.minDimension / 4

            // Sun/Moon body
            val bodyRadius = baseRadius * (1f - transitionProgress * 0.1f) * scale

            drawCircle(
                color = tint,
                radius = bodyRadius,
                center = center
            )

            // Moon crater (appears as transition progresses)
            if (transitionProgress > 0.3f) {
                val craterAlpha = ((transitionProgress - 0.3f) / 0.7f).coerceIn(0f, 1f)
                val craterOffset = Offset(
                    center.x + bodyRadius * 0.3f,
                    center.y - bodyRadius * 0.2f
                )
                drawCircle(
                    color = backgroundColor.copy(alpha = craterAlpha),
                    radius = bodyRadius * 0.6f,
                    center = craterOffset
                )
            }

            // Sun rays (fade out as transition progresses)
            val rayAlpha = 1f - transitionProgress
            if (rayAlpha > 0.1f) {
                val rayCount = 8
                val rayLength = baseRadius * 0.6f
                val rayStart = baseRadius * 1.4f

                rotate(rayRotation, center) {
                    for (i in 0 until rayCount) {
                        val angle = (i * 360f / rayCount) * (PI / 180f).toFloat()
                        val startOffset = Offset(
                            center.x + cos(angle) * rayStart,
                            center.y + sin(angle) * rayStart
                        )
                        val endOffset = Offset(
                            center.x + cos(angle) * (rayStart + rayLength),
                            center.y + sin(angle) * (rayStart + rayLength)
                        )

                        drawLine(
                            color = tint.copy(alpha = rayAlpha * 0.8f),
                            start = startOffset,
                            end = endOffset,
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
            }

            // Stars around moon (appear as transition progresses)
            if (transitionProgress > 0.5f) {
                val starAlpha = ((transitionProgress - 0.5f) / 0.5f).coerceIn(0f, 1f)
                val starPositions = listOf(
                    Offset(center.x - bodyRadius * 1.8f, center.y - bodyRadius * 0.8f),
                    Offset(center.x + bodyRadius * 1.5f, center.y + bodyRadius * 1.2f),
                    Offset(center.x - bodyRadius * 1.2f, center.y + bodyRadius * 1.5f)
                )

                starPositions.forEach { pos ->
                    drawCircle(
                        color = tint.copy(alpha = starAlpha * 0.6f),
                        radius = 2.dp.toPx(),
                        center = pos
                    )
                }
            }
        }
    }
}