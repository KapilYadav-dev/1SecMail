import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

actual val appFont: FontFamily
    get() = TODO("Not yet implemented")

actual object Clipboard {
    actual fun copyTextToClipboard(text: String) {
        TODO("Not yet implemented")
    }
}

@Composable
actual fun showToast(msg: String) {
}