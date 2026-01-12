package gy.roach.radio

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Radio Guyana") {
        App() // ThemeSettings() is created with default constructor now
    }
}