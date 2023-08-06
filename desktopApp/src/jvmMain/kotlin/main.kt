import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "1SecApi ( Unofficial App)",
        icon = painterResource("icon.png"),
        alwaysOnTop = false,
        state = WindowState(
            size = DpSize(800.dp, 900.dp),
            position = WindowPosition(Alignment.Center)
        ),
        resizable = false
    ) {
        MainView()
    }
}