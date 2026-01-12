package gy.roach.radio

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gy.roach.radio.ui.AnimatedEqualizer
import kotlin.math.roundToInt

@Composable
fun ExpandedPlayerView(
    station: StationItem,
    isPlaying: Boolean,
    onClose: () -> Unit,
    onTogglePlay: () -> Unit
) {
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(0, offsetY.roundToInt()) }
            .draggable(
                state = rememberDraggableState { delta ->
                    if (delta > 0 || offsetY > 0) offsetY += delta
                },
                orientation = Orientation.Vertical,
                onDragStopped = {
                    if (offsetY > 300f) onClose() else offsetY = 0f
                }
            )
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    )
                )
            )
    ) {
        // 1. Reactive Ambient Background
        val infiniteTransition = rememberInfiniteTransition(label = "bgGlow")
        val glowAlpha by infiniteTransition.animateFloat(
            initialValue = 0.1f,
            targetValue = 0.3f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = EaseInOutSine),
                repeatMode = RepeatMode.Reverse
            ),
            label = "glowAlpha"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = if (isPlaying) {
                            listOf(GuyanaColors.FlagGreen.copy(alpha = glowAlpha), Color.Transparent)
                        } else {
                            listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.surface)
                        },
                        center = Offset(500f, 500f),
                        radius = 1000f
                    )
                )
        )
        // Massive Background Typography (Aesthetic Choice)
        Text(
            text = station.label.uppercase(),
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(0.05f),
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 120.sp,
                fontWeight = FontWeight.Black
            ),
            softWrap = false
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.primary)
                }
            }

            // 2. The "Hero" Artwork with Floating Animation
            val floatAnim by infiniteTransition.animateFloat(
                initialValue = -10f,
                targetValue = 10f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = EaseInOutSine),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "float"
            )

            Surface(
                modifier = Modifier
                    .size(220.dp)
                    .graphicsLayer { translationY = if (isPlaying) floatAnim else 0f }
                    .shadow(32.dp, shape = RoundedCornerShape(40.dp), spotColor = GuyanaColors.FlagGreen),
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    // Big Station Initial or Icon
                    Text(
                        text = station.label.take(1),
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        )
                    )
                    // Overlay the actual radio icon if available
                }
            }
            // Visualizer or Large Station Name
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = station.label,
                    textAlign = TextAlign.Center, // In case of long names
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Streaming Now",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // 3. Playback Controls with "Halo"
            Box(contentAlignment = Alignment.Center) {
                if (isPlaying) {
                    // Expanding Halo Ring
                    val haloScale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 1.6f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1500, easing = EaseOutExpo),
                            repeatMode = RepeatMode.Restart
                        ),
                        label = "halo"
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .graphicsLayer {
                                scaleX = haloScale
                                scaleY = haloScale
                                alpha = 1f - (haloScale - 1f) / 0.6f
                            }
                            .border(2.dp, GuyanaColors.FlagGreen, CircleShape)
                    )
                }

                FilledIconButton(
                    onClick = onTogglePlay,
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }


            // Visualizer (Sharp version at bottom)
            Box(
                modifier = Modifier
                    .height(120.dp) // Give it a bit more breathing room
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp), // Add side padding so it doesn't touch edges
                contentAlignment = Alignment.Center
            ) {
                AnimatedEqualizer(
                    isPlaying = isPlaying,
                    modifier = Modifier.fillMaxWidth().height(100.dp), // Match the parent
                    barCount = 40, // Increase bars to fill the width
                    barWidth = 4.dp, // Thinner bars look more sophisticated in wide views
                    barSpacing = 4.dp
                    // Remove 'size' here to let it use available width
                )
            }

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}
