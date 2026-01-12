package gy.roach.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gy.roach.radio.theme.GuyanaColors
import gy.roach.radio.ui.MiniEqualizer
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun StationItemCard(
    stationItem: StationItem,
    isSelected: Boolean = false,
    isPlaying: Boolean = false,
    onClick: () -> Unit = {}
) {
    AtmosphericCard(
        number = stationItem.index + 1,
        title = stationItem.label,
        body = stationItem.typeAsString(),
        isSelected = isSelected,
        isPlaying = isPlaying,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    )
}

@Composable
fun GlassCard(
    number: Int,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    isPlaying: Boolean = false
) {
    val isDarkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f

    // Adaptive colors based on theme
    val glassColor = if (isDarkTheme) {
        Color.White.copy(alpha = 0.25f)
    } else {
        Color.Black.copy(alpha = 0.08f)
    }

    val borderColor = if (isDarkTheme) {
        Color.White.copy(alpha = 0.3f)
    } else {
        Color.Black.copy(alpha = 0.15f)
    }

    val textColor = if (isDarkTheme) {
        Color.White
    } else {
        Color.Black.copy(alpha = 0.87f)
    }

    val circleGlassColors = if (isDarkTheme) {
        listOf(
            Color.White.copy(alpha = 0.4f),
            Color.White.copy(alpha = 0.1f)
        )
    } else {
        listOf(
            Color.Black.copy(alpha = 0.12f),
            Color.Black.copy(alpha = 0.06f)
        )
    }

    val circleBorderColor = if (isDarkTheme) {
        Color.White.copy(alpha = 0.4f)
    } else {
        Color.Black.copy(alpha = 0.2f)
    }

    // Animated border color for selected/playing states
    val animatedBorderColor by animateColorAsState(
        targetValue = when {
            isPlaying -> GuyanaColors.FlagGreen
            isSelected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.outline
        },
        animationSpec = tween(300),
        label = "borderColor"
    )

    // Animated border width
    val animatedBorderWidth by animateDpAsState(
        targetValue = when {
            isPlaying -> 2.5.dp
            isSelected -> 2.dp
            else -> 0.5.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "borderWidth"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = glassColor
        ),
        border = BorderStroke(
            width = animatedBorderWidth,
            color = animatedBorderColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Animated circle with number
            AnimatedNumberCircle(
                number = number,
                isSelected = isSelected,
                isPlaying = isPlaying,
                circleGlassColors = circleGlassColors,
                circleBorderColor = circleBorderColor,
                textColor = textColor
            )

            // Column with title and body
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                )
                Text(
                    text = body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.7f),
                    lineHeight = 20.sp
                )
            }

            // Replace pulsing dot with animated equalizer when playing
            if (isPlaying) {
                PulsingPlayingIndicator()
            }
        }
    }
}

/**
 * Animated number circle that responds to selection and playing states.
 */
@Composable
fun AnimatedNumberCircle(
    number: Int,
    isSelected: Boolean,
    isPlaying: Boolean,
    circleGlassColors: List<Color>,
    circleBorderColor: Color,
    textColor: Color
) {
    // Scale animation for selection
    val scale by animateFloatAsState(
        targetValue = if (isSelected || isPlaying) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "circleScale"
    )

    // Animated background for playing state
    val animatedCircleColors = if (isPlaying) {
        listOf(
            GuyanaColors.FlagGreen.copy(alpha = 0.6f),
            GuyanaColors.FlagGreen.copy(alpha = 0.3f)
        )
    } else if (isSelected) {
        listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
    } else {
        circleGlassColors
    }

    Box(
        modifier = Modifier
            .size(50.dp)
            .scale(scale)
            .background(
                brush = Brush.linearGradient(colors = animatedCircleColors),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = if (isPlaying) GuyanaColors.FlagGreen else circleBorderColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$number",
            style = MaterialTheme.typography.titleMedium,
            color = if (isPlaying) Color.White else textColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Pulsing indicator that shows when a station is playing.
 */
@Composable
fun PulsingPlayingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "playingPulse")

    // Pulsing scale
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    // Pulsing alpha
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    // Outer glow
    Box(
        modifier = Modifier.size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        // Glow effect
        Box(
            modifier = Modifier
                .size(20.dp)
                .scale(pulseScale)
                .graphicsLayer { alpha = pulseAlpha * 0.3f }
                .background(
                    color = GuyanaColors.FlagGreen,
                    shape = CircleShape
                )
        )

        // Inner solid dot
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    color = GuyanaColors.FlagGreen,
                    shape = CircleShape
                )
        )
    }
}

/**
 * A Brutalist-inspired alternative to the Glass Card.
 * Focuses on high-contrast geometry and distinctive typography per pro.md rules.
 */
@Composable
fun NeoGridCard(
    number: Int,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    isPlaying: Boolean = false
) {
    val containerColor = if (isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
    val accentColor = if (isPlaying) GuyanaColors.FlagGreen else MaterialTheme.colorScheme.primary

    // Animate the "Accent Ribbon" width
    val ribbonWidth by animateDpAsState(
        targetValue = if (isPlaying) 8.dp else if (isSelected) 4.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "ribbonWidth"
    )

    Box(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .height(IntrinsicSize.Min)
            .background(containerColor, RoundedCornerShape(4.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
    ) {
        // 1. Sharp Accent Ribbon (High Impact Moment)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(ribbonWidth)
                .background(accentColor)
                .align(Alignment.CenterStart)
        )

        // 2. Brutalist Number Background (Typography focus)
        Text(
            text = "$number".padStart(2, '0'),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 10.dp, y = 15.dp)
                .graphicsLayer { alpha = 0.07f },
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Black,
                fontSize = 80.sp
            ),
            softWrap = false
        )

        Row(
            modifier = Modifier
                .padding(start = ribbonWidth + 16.dp, top = 20.dp, bottom = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // 3. Distinctive Typography label
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = body,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            if (isPlaying) {
                MiniEqualizer(
                    isPlaying = isPlaying,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun AtmosphericCard(
    number: Int,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    isPlaying: Boolean = false
) {
    // Rule #4: 8-point grid constants
    val outerPadding = 16.dp
    val verticalGap = 8.dp
    val outerRadius = 24.dp
    val innerRadius = 8.dp // Rule #4: Outer(24) - Gap(16) = 8

    // Rule #5 & #6: Depth without shadows
    val baseSurface = MaterialTheme.colorScheme.surface
    val cardBackground = if (isSelected) {
        Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
            )
        )
    } else {
        // Rule #7: Layered gradient for atmosphere
        Brush.verticalGradient(
            colors = listOf(
                baseSurface,
                baseSurface.copy(alpha = 0.95f)
            )
        )
    }

    Box(
        modifier = modifier
            .padding(horizontal = outerPadding, vertical = verticalGap)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(outerRadius),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            border = BorderStroke(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isPlaying) GuyanaColors.FlagGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
            )
        ) {
            // Rule #7: Geometric Pattern Layer (Using a simple Canvas for the "Pattern")
            Box(modifier = Modifier.background(cardBackground)) {
                Canvas(modifier = Modifier.matchParentSize().graphicsLayer { alpha = 0.03f }) {
                    val dotSpacing = 16.dp.toPx()
                    val horizontalDots = (size.width / dotSpacing).toInt()
                    val verticalDots = (size.height / dotSpacing).toInt()

                    for (x in 0..horizontalDots) {
                        for (y in 0..verticalDots) {
                            drawCircle(
                                color = Color.Gray,
                                radius = 1.dp.toPx(),
                                center = Offset(x * dotSpacing, y * dotSpacing)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Rule #4: Mathematically nested corners for the number box
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = if (isPlaying) GuyanaColors.FlagGreen else MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(innerRadius)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$number",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (isPlaying) Color.White else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.sp
                            ),
                            // FIX: Use onSurface to ensure it's dark in light mode and light in dark mode
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        // Rule #6: Visual grouping (Genre text)
                        Text(
                            text = body,
                            style = MaterialTheme.typography.bodyMedium,
                            // Sub-text should be slightly muted but still legible
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    if (isPlaying) {
                        MiniEqualizer(isPlaying = true, modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}