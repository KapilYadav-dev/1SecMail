import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

actual val appFont: FontFamily
    get() = FontFamily(
        androidx.compose.ui.text.platform.Font(
            "fonts/font.ttf",
            FontWeight.SemiBold,
            FontStyle.Normal
        )
    )

actual object Clipboard {
    actual fun copyTextToClipboard(text: String) {
        TODO("Not yet implemented")
    }
}

@Composable
actual fun showToast(msg: String) {
}