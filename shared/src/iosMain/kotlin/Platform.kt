import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import platform.UIKit.*

actual val appFont: FontFamily = FontFamily(
    loadFont("font")
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
@OptIn(ExperimentalResourceApi::class)
fun loadFont(res:String): Font {
    val byteArray = runBlocking {
        resource("fonts/$res.ttf").readBytes()
    }
    return androidx.compose.ui.text.platform.Font(res, byteArray,FontWeight.SemiBold, FontStyle.Normal)
}

actual object Platform {
    actual val platformName: String = "ios"
}