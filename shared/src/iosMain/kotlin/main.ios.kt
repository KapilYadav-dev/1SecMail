import androidx.compose.ui.window.ComposeUIViewController
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Typeface

fun MainViewController() = ComposeUIViewController { App() }

fun loadCustomFont(name: String): Typeface {
    return Typeface.makeFromName(name, FontStyle.NORMAL)
}