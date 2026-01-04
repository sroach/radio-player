package gy.roach.radio

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import radio_guyana_player.composeapp.generated.resources.Res
import radio_guyana_player.composeapp.generated.resources.app_icon

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "GY Tunes",
        icon = painterResource((Res.drawable.app_icon))
    ) {
        App()
    }
}