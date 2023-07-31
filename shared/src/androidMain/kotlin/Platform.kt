import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import com.myapplication.common.R


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