import android.annotation.SuppressLint
import android.webkit.WebView
import android.widget.Toast
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
import androidx.compose.ui.viewinterop.AndroidView
import com.myapplication.common.R
import model.DialogProps
import res.blueColor
import res.whiteColor
import utils.Utils.platformNameAndroid
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import kotlin.system.exitProcess


actual val appFont: FontFamily = FontFamily(
    Font(R.font.font)
)

@Composable
actual fun showToast(msg: String) {
    Toast.makeText(LocalContext.current, msg, Toast.LENGTH_LONG).show()
}

actual val platformName: String = platformNameAndroid

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

actual fun exitApp() {
    exitProcess(0)
}

actual typealias AppSerializable = java.io.Serializable

actual fun downloadFile(url: String, destination: String): Boolean {
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

        return true
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun RenderHtml(htmlCode: String, modifier: Modifier) {
    AndroidView(modifier = modifier, factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.builtInZoomControls = true
            loadDataWithBaseURL(null, htmlCode, "text/html", "UTF-8", null)
        }
    })
}