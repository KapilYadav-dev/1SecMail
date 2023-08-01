import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import io.ktor.client.request.*
import kotlinx.coroutines.*
import model.DialogProps
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import platform.Foundation.*
import platform.UIKit.*
import platform.posix.exit


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
    showAlert(msg, "", DialogProps("ok") {}, null)
}

@OptIn(ExperimentalResourceApi::class)
fun loadFont(res: String): Font {
    val byteArray = runBlocking {
        resource("fonts/$res.ttf").readBytes()
    }
    return androidx.compose.ui.text.platform.Font(
        res,
        byteArray,
        FontWeight.SemiBold,
        FontStyle.Normal
    )
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

fun showAlert(
    title: String,
    desc: String,
    positiveProps: DialogProps?,
    negativeProps: DialogProps?
) {
    val alertController = UIAlertController.alertControllerWithTitle(
        title = title,
        message = desc,
        preferredStyle = 1
    )
    positiveProps?.let {
        alertController.addAction(
            UIAlertAction.actionWithTitle(it.text, style = 0) { _ ->
                it.onClick.invoke()
            }
        )
    }
    negativeProps?.let {
        alertController.addAction(
            UIAlertAction.actionWithTitle(it.text, style = 0) { _ ->
                it.onClick.invoke()
            }
        )
    }
    val self = UIApplication.sharedApplication.keyWindow?.rootViewController
    self?.presentViewController(alertController, animated = true, completion = null)
}

@Composable
actual fun showDialog(
    msg: String,
    desc: String,
    positiveProps: DialogProps?,
    negativeProps: DialogProps?
) {
    showAlert(msg, desc, positiveProps, negativeProps)
}

actual fun ExitApp() {
    exit(0)
}