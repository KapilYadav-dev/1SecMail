import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import platform.UIKit.*
import kotlinx.coroutines.*
import platform.Foundation.*
import platform.darwin.NSObject

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

actual class FileDownloader actual constructor() {
    actual suspend fun downloadFile(url: String, destination: String): Boolean {
        return try {
            val nsUrl = NSURL(string = url)
            val data = NSData.dataWithContentsOfURL(nsUrl)
            data?.writeToFile(destination, true)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}