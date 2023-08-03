import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import javax.swing.JOptionPane
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.DialogProps
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import kotlin.system.exitProcess

actual val appFont: FontFamily
    get() = FontFamily(
        androidx.compose.ui.text.platform.Font(
            "fonts/font.ttf",
            FontWeight.SemiBold,
            FontStyle.Normal
        )
    )

@Composable
actual fun showToast(msg: String) {
    JOptionPane.showMessageDialog(null, msg)
}

actual val platformName: String = "desktop"

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

@Composable
actual fun showDialog(
    msg: String,
    desc: String,
    positiveProps: DialogProps?,
    negativeProps: DialogProps?
) {
    val options = arrayOf(positiveProps?.text, negativeProps?.text)
    val result = JOptionPane.showOptionDialog(
        null,
        desc,
        msg,
        JOptionPane.YES_NO_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        options,
        options[0]
    )
    when (result) {
        0 -> {
            positiveProps?.onClick?.invoke()
        }
        1 -> {
            negativeProps?.onClick?.invoke()
        }
    }
}

actual fun exitApp() {
    exitProcess(0)
}

actual typealias AppSerializable = java.io.Serializable