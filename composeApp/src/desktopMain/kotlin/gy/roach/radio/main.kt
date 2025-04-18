package gy.roach.radio

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "radio_guyana_player",
    ) {
        App()
    }
}