import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.JOptionPane
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

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
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val stringSelection = StringSelection(text)
        clipboard.setContents(stringSelection, null)
    }
}

@Composable
actual fun showToast(msg: String) {
    JOptionPane.showMessageDialog(null, msg)
}

actual object Platform {
    actual val platformName: String = "desktop"
}

actual class FileDownloader actual constructor() {
    actual suspend fun downloadFile(url: String, destination: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection()
                val inputStream = BufferedInputStream(connection.getInputStream())
                val outputStream = FileOutputStream(File(destination))

                val buffer = ByteArray(1024)
                var bytesRead: Int

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()

                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}