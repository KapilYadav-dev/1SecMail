import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.JOptionPane

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