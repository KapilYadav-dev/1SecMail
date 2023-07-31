import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

expect val appFont: FontFamily

expect object Clipboard {
    fun copyTextToClipboard(text: String)
}
@Composable
expect fun showToast(msg:String)

expect object Platform {
    val platformName: String
}