import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import model.DialogProps

expect val appFont: FontFamily

expect object Clipboard {
    fun copyTextToClipboard(text: String)
}
@Composable
expect fun showToast(msg:String)

expect object Platform {
    val platformName: String
}

expect class FileDownloader() {
    suspend fun downloadFile(url: String, destination: String): Boolean
}

@Composable
expect fun showDialog(
    msg: String,
    desc: String,
    positiveProps:DialogProps?=null,
    negativeProps:DialogProps?=null,
)
