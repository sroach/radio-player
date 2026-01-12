package gy.roach.radio

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import gy.roach.radio.theme.*
import org.jetbrains.compose.resources.vectorResource
import radio_guyana_player.composeapp.generated.resources.Res
import radio_guyana_player.composeapp.generated.resources.ic_check
import radio_guyana_player.composeapp.generated.resources.ic_home

/**
 * Settings screen with animated theme previews and micro-interactions.
 */
@Composable
fun SettingsScreen(
    themeState: ModernThemeState,
    onNavigateToMain: () -> Unit = {}
) {
    // Animation trigger for staggered reveal
    var animationTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        animationTriggered = true
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Animated Header
        item {
            AnimatedSettingsHeader(
                isVisible = animationTriggered,
                delay = 0
            )
        }

        // Dark Mode Toggle with animation
        item {
            AnimatedSettingsItem(
                isVisible = animationTriggered,
                delay = 50
            ) {
                AnimatedDarkModeToggle(
                    isDarkMode = themeState.isDarkTheme,
                    onToggle = { themeState.toggleDarkMode() }
                )
            }
        }

        // Section Header
        item {
            AnimatedSettingsItem(
                isVisible = animationTriggered,
                delay = 100
            ) {
                Text(
                    text = "Color Themes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }
        }

        // Color Theme Options with staggered animation
        itemsIndexed(ColorTheme.entries.toList()) { index, theme ->
            AnimatedSettingsItem(
                isVisible = animationTriggered,
                delay = 150 + (index * 60)
            ) {
                ThemePreviewCard(
                    theme = theme,
                    isSelected = themeState.selectedTheme == theme,
                    isDarkMode = themeState.isDarkTheme,
                    onSelect = { themeState.selectTheme(theme) }
                )
            }
        }

        // Footer spacing
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

/**
 * Animated header for settings screen.
 */
@Composable
private fun AnimatedSettingsHeader(
    isVisible: Boolean,
    delay: Int
) {
    var itemVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(delay.toLong())
            itemVisible = true
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (itemVisible) 1f else 0f,
        animationSpec = tween(300, easing = EaseOutCubic),
        label = "headerAlpha"
    )

    val offsetY by animateFloatAsState(
        targetValue = if (itemVisible) 0f else -20f,
        animationSpec = tween(300, easing = EaseOutCubic),
        label = "headerOffset"
    )

    Text(
        text = "Appearance",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .graphicsLayer {
                this.alpha = alpha
                this.translationY = offsetY
            }
    )
}

/**
 * Wrapper for animated settings items with stagger support.
 */
@Composable
private fun AnimatedSettingsItem(
    isVisible: Boolean,
    delay: Int,
    content: @Composable () -> Unit
) {
    var itemVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(delay.toLong())
            itemVisible = true
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (itemVisible) 1f else 0f,
        animationSpec = tween(300, easing = EaseOutCubic),
        label = "itemAlpha"
    )

    val offsetX by animateFloatAsState(
        targetValue = if (itemVisible) 0f else 30f,
        animationSpec = tween(300, easing = EaseOutCubic),
        label = "itemOffset"
    )

    Box(
        modifier = Modifier.graphicsLayer {
            this.alpha = alpha
            this.translationX = offsetX
        }
    ) {
        content()
    }
}

/**
 * Animated dark mode toggle with smooth switch transition.
 */
@Composable
private fun AnimatedDarkModeToggle(
    isDarkMode: Boolean,
    onToggle: () -> Unit
) {
    // Press animation
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessHigh),
        label = "cardPress"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                isPressed = true
                onToggle()
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Dark Mode",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Animated description text
                AnimatedContent(
                    targetState = isDarkMode,
                    transitionSpec = {
                        (fadeIn(tween(200)) + slideInVertically { -it / 2 }) togetherWith
                                (fadeOut(tween(200)) + slideOutVertically { it / 2 })
                    },
                    label = "modeDescription"
                ) { dark ->
                    Text(
                        text = if (dark) "Dark appearance" else "Light appearance",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Animated switch
            AnimatedSwitch(
                checked = isDarkMode,
                onCheckedChange = { onToggle() }
            )
        }
    }

    // Reset press state
    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }
}

/**
 * Animated switch with smooth thumb movement and color transition.
 */
@Composable
private fun AnimatedSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 20.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "thumbOffset"
    )

    val trackColor by animateColorAsState(
        targetValue = if (checked)
            GuyanaColors.FlagGreen
        else
            MaterialTheme.colorScheme.outline,
        animationSpec = tween(200),
        label = "trackColor"
    )

    Box(
        modifier = Modifier
            .size(width = 51.dp, height = 31.dp)
            .clip(RoundedCornerShape(15.5.dp))
            .background(trackColor)
            .clickable { onCheckedChange(!checked) }
            .padding(2.dp)
    ) {
        // Thumb with shadow effect
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(27.dp)
                .background(Color.White, CircleShape)
        )
    }
}

/**
 * Theme preview card with live color preview.
 */
@Composable
private fun ThemePreviewCard(
    theme: ColorTheme,
    isSelected: Boolean,
    isDarkMode: Boolean,
    onSelect: () -> Unit
) {
    // Selection animation
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardScale"
    )

    val borderWidth by animateDpAsState(
        targetValue = if (isSelected) 2.5.dp else 0.5.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "borderWidth"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) theme.accentColor else MaterialTheme.colorScheme.outline,
        animationSpec = tween(200),
        label = "borderColor"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = borderWidth,
            color = borderColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Live theme preview mini-card
            ThemeMiniPreview(
                theme = theme,
                isDark = isDarkMode,
                isSelected = isSelected
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Theme info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = theme.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = theme.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Animated checkmark
            AnimatedVisibility(
                visible = isSelected,
                enter = scaleIn(spring(dampingRatio = Spring.DampingRatioMediumBouncy)) + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(theme.accentColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_check),
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

/**
 * Mini preview card showing the theme's colors.
 */
@Composable
private fun ThemeMiniPreview(
    theme: ColorTheme,
    isDark: Boolean,
    isSelected: Boolean
) {
    val previewGradient = GradientBackgrounds.getGradient(theme, isDark)

    // Pulse animation for selected theme
    val infiniteTransition = rememberInfiniteTransition(label = "previewPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .size(56.dp)
            .scale(if (isSelected) pulseScale else 1f)
            .clip(RoundedCornerShape(12.dp))
            .background(previewGradient)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // Accent color circle in center
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(theme.accentColor, CircleShape)
                .border(
                    width = 2.dp,
                    color = Color.White.copy(alpha = 0.5f),
                    shape = CircleShape
                )
        )
    }
}

/**
 * Top bar for the settings screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(
    onNavigateToMain: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                "Settings",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateToMain) {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_home),
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
    )
}