package gy.roach.radio

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import radio_guyana_player.composeapp.generated.resources.Res
import radio_guyana_player.composeapp.generated.resources.favicon_xml

/**
 * About screen of the app showing information about the app.
 *
 * @param onNavigateToMain Callback to navigate back to the main screen
 */
@Preview
@Composable
fun AboutScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToReleaseNotes: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App logo
        Box(
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.favicon_xml),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .aspectRatio(5f/3f)
            )
        }

        // App title
        Text(
            text = "Radio Guyana Player",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // App version
        Text(
            text = "Version 1.0",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // App description
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "About",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Radio Guyana Player is a multiplatform application that allows you to listen to various Guyanese radio stations. The app provides access to a curated list of radio stations broadcasting Guyanese music, news, and entertainment.",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

        // Features
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Features",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "• Listen to multiple Guyanese radio stations\n• Easy station selection\n• Audio visualization\n• Dark and light theme support\n• iOS-inspired design",
                    style = MaterialTheme.typography.body1
                )
            }
        }

        // Release Notes
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Release Notes",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Check out the latest features and updates in our release notes.",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Button(
                    onClick = onNavigateToReleaseNotes,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary
                    )
                ) {
                    Text("View Release Notes")
                }
            }
        }

        // Credits
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Credits",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Developed with Kotlin Multiplatform and Compose Multiplatform.\n\nAll radio stations are property of their respective owners.",
                    style = MaterialTheme.typography.body1
                )
            }
        }

        // Developer section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Developer",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Clickable website link
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { openUrl("https://roach.gy") }
                ) {
                    Text(
                        text = "Visit our website: ",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "https://roach.gy",
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        // Copyright
        Text(
            text = "© 2025 Radio Guyana Player",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 24.dp, bottom = 32.dp)
        )
    }
}

/**
 * Top bar for the About screen with a home button.
 *
 * @param onNavigateToMain Callback to navigate back to the main screen
 */
@Composable
fun AboutTopBar(
    onNavigateToMain: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "About",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
            )
        },
        navigationIcon = {
            // Home button - iOS style
            IconButton(onClick = onNavigateToMain) {
                // iOS-style home icon

                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 8.dp
    )
}
