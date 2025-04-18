package gy.roach.radio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Release notes screen showing the latest features and updates of the app.
 *
 * @param onNavigateToAbout Callback to navigate back to the about screen
 */
@Preview
@Composable
fun ReleaseNotesScreen(
    onNavigateToAbout: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Screen title
        Text(
            text = "Release Notes",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        // Latest version
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
                    text = "Version 1.0",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Initial release of Radio Guyana Player with the following features:",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "• Multiple Guyanese radio stations\n• Easy station selection\n• Audio visualization with bouncing balls\n• Dark and light theme support\n• iOS-inspired design\n• Multi-platform support (iOS, Android, Desktop)",
                    style = MaterialTheme.typography.body1
                )
            }
        }

        // Previous updates
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
                    text = "Beta Version 0.9",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "• Added more radio stations\n• Improved audio playback stability\n• Enhanced visualization effects\n• Fixed UI issues on different screen sizes",
                    style = MaterialTheme.typography.body1
                )
            }
        }

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
                    text = "Beta Version 0.8",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "• First beta release\n• Basic audio playback functionality\n• Simple station list\n• Initial UI implementation",
                    style = MaterialTheme.typography.body1
                )
            }
        }

        // Footer
        Text(
            text = "Thank you for using Radio Guyana Player!",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 24.dp, bottom = 32.dp)
        )
    }
}

/**
 * Top bar for the Release Notes screen with a back button.
 *
 * @param onNavigateToAbout Callback to navigate back to the about screen
 */
@Composable
fun ReleaseNotesTopBar(
    onNavigateToAbout: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Release Notes",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
            )
        },
        navigationIcon = {
            // Back button - iOS style
            IconButton(onClick = onNavigateToAbout) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 8.dp
    )
}