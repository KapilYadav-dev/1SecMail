import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        this.window.title="1SecApi ( Unofficial App)"
        MainView()
    }
}