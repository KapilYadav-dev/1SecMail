import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import model.DialogProps

expect val appFont: FontFamily

@Composable
expect fun showToast(msg: String)

expect val platformName: String

expect class FileDownloader() {
    suspend fun downloadFile(url: String, destination: String): Boolean
}

@Composable
expect fun showDialog(
    msg: String,
    desc: String,
    positiveProps: DialogProps? = null,
    negativeProps: DialogProps? = null,
)

expect fun exitApp()

expect interface AppSerializable