package gy.roach.radio

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import radio_guyana_player.composeapp.generated.resources.Res
import radio_guyana_player.composeapp.generated.resources.guyanese_flag_radio_wave_icon

/**
 * About screen with animated section reveals.
 */
@Composable
fun AboutScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToReleaseNotes: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    // Staggered animation trigger
    var animationTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        animationTriggered = true
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Animated logo with bounce
        AnimatedLogoSection(isVisible = animationTriggered)

        // Animated title
        AnimatedAboutSection(
            isVisible = animationTriggered,
            delay = 100
        ) {
            Text(
                text = "Radio Guyana Player",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Animated version
        AnimatedAboutSection(
            isVisible = animationTriggered,
            delay = 150
        ) {
            Text(
                text = "Version 1.2",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // About Card
        AnimatedAboutSection(
            isVisible = animationTriggered,
            delay = 200
        ) {
            AboutCard(
                title = "About",
                content = "Radio Guyana Player is a multiplatform application that allows you to listen to various Guyanese radio stations. The app provides access to a curated list of radio stations broadcasting Guyanese music, news, and entertainment."
            )
        }

        // Features Card
        AnimatedAboutSection(
            isVisible = animationTriggered,
            delay = 300
        ) {
            AboutCard(
                title = "Features",
                content = "• Listen to multiple Guyanese radio stations\n• Easy station selection\n• Audio visualization\n• Dark and light theme support\n• Guyanese-inspired design themes"
            )
        }

        // Release Notes Card
        AnimatedAboutSection(
            isVisible = animationTriggered,
            delay = 400
        ) {
            AboutCardWithAction(
                title = "Release Notes",
                content = "Check out the latest features and updates in our release notes.",
                actionText = "View Release Notes",
                onAction = onNavigateToReleaseNotes
            )
        }

        // Credits Card
        AnimatedAboutSection(
            isVisible = animationTriggered,
            delay = 500
        ) {
            AboutCard(
                title = "Credits",
                content = "Developed with Kotlin Multiplatform and Compose Multiplatform.\n\nAll radio stations are property of their respective owners."
            )
        }

        // Developer Card
        AnimatedAboutSection(
            isVisible = animationTriggered,
            delay = 600
        ) {
            DeveloperCard()
        }

        // Copyright
        AnimatedAboutSection(
            isVisible = animationTriggered,
            delay = 700
        ) {
            Text(
                text = "© 2025 Radio Guyana Player",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 24.dp, bottom = 32.dp)
            )
        }
    }
}

/**
 * Animated logo with bounce effect.
 */
@Composable
private fun AnimatedLogoSection(isVisible: Boolean) {
    var logoVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            logoVisible = true
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logoScale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0f,
        animationSpec = tween(400),
        label = "logoAlpha"
    )

    Box(
        modifier = Modifier
            .padding(top = 32.dp, bottom = 16.dp)
            .scale(scale)
            .graphicsLayer { this.alpha = alpha },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.guyanese_flag_radio_wave_icon),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp)
                .aspectRatio(5f/3f)
        )
    }
}

/**
 * Wrapper for animated about sections.
 */
@Composable
private fun AnimatedAboutSection(
    isVisible: Boolean,
    delay: Int,
    content: @Composable () -> Unit
) {
    var sectionVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(delay.toLong())
            sectionVisible = true
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (sectionVisible) 1f else 0f,
        animationSpec = tween(300, easing = EaseOutCubic),
        label = "sectionAlpha"
    )

    val offsetY by animateFloatAsState(
        targetValue = if (sectionVisible) 0f else 30f,
        animationSpec = tween(300, easing = EaseOutCubic),
        label = "sectionOffset"
    )

    Box(
        modifier = Modifier.graphicsLayer {
            this.alpha = alpha
            this.translationY = offsetY
        }
    ) {
        content()
    }
}

/**
 * Reusable about card component.
 */
@Composable
private fun AboutCard(
    title: String,
    content: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
            )
        }
    }
}

/**
 * About card with action button.
 */
@Composable
private fun AboutCardWithAction(
    title: String,
    content: String,
    actionText: String,
    onAction: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            TextButton(
                onClick = onAction,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = actionText,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/**
 * Developer info card.
 */
@Composable
private fun DeveloperCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Developer",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp),
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { openUrl("https://roach.gy") }
            ) {
                Text(
                    text = "Visit our website: ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
                Text(
                    text = "roach.gy",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

/**
 * Top bar for the About screen with a home button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutTopBar(
    onNavigateToMain: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "About",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateToMain) {
                Icon(
                    imageVector = Icons.Default.Home,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "Home",
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}