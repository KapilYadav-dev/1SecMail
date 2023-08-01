import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.common.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.DialogProps
import res.blueColor
import res.whiteColor
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL


actual val appFont: FontFamily = FontFamily(
    Font(R.font.font)
)

actual object Clipboard {
    actual fun copyTextToClipboard(text: String) {

    }
}

@Composable
actual fun showToast(msg: String) {
    Toast.makeText(LocalContext.current, msg, Toast.LENGTH_LONG).show()
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

@Composable
actual fun showDialog(
    msg: String,
    desc: String,
    positiveProps: DialogProps?,
    negativeProps: DialogProps?
) {
    AlertDialog(
        onDismissRequest = { /* Handle dialog dismiss request if needed */ },
        title = {
            Text(text = msg, fontFamily = appFont)
        },
        text = {
            Text(desc, fontFamily = appFont)
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                positiveProps?.let {
                    Button(
                        onClick = {
                            it.onClick.invoke()
                        },
                        modifier = Modifier.padding(16.dp).weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = blueColor
                        )
                    ) {
                        Text(
                            text = it.text.toString(),
                            fontSize = 18.sp,
                            fontFamily = appFont,
                            color = whiteColor
                        )
                    }
                }

                negativeProps?.let {
                    Button(
                        onClick = {
                            it.onClick.invoke()
                        },
                        modifier = Modifier.padding(16.dp).weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = blueColor
                        )
                    ) {
                        Text(
                            text = it.text.toString(),
                            fontSize = 18.sp,
                            fontFamily = appFont,
                            color = whiteColor
                        )
                    }
                }
            }
        }
    )
}
