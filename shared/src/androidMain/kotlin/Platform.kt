import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.myapplication.common.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL


actual val appFont: FontFamily  = FontFamily(
    Font(R.font.font)
)

actual object Clipboard {
    actual fun copyTextToClipboard(text: String) {

    }
}

@Composable
actual fun showToast(msg: String) {
    Toast.makeText(LocalContext.current,msg,Toast.LENGTH_LONG).show()
}

actual object Platform {
    actual val platformName: String = "android"
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
