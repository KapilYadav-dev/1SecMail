import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Typeface
import platform.UIKit.UIPasteboard

actual val appFont: FontFamily = FontFamily(
    Typeface(loadCustomFont("font"))
)

actual object Clipboard {
    actual fun copyTextToClipboard(text: String) {
        val pasteboard = UIPasteboard.generalPasteboard()
        pasteboard.string = text
    }
}

@Composable
actual fun showToast(msg: String) {
}