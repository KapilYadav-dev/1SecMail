package utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import appFont
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Duration.Companion.seconds

object Utils {
    const val platformNameAndroid = "android"
    const val platformNameIOS = "ios"
    const val platformNameDesktop = "desktop"
    val TOAST_TIMER = 2.seconds

    fun String.getNameFromEmail(): String {
        val atIndex = indexOf('@')
        // If "@" is present and it's not the first or last character
        if (atIndex in 1 until length - 1) {
            // Extract and return the name part of the email
            return substring(0, atIndex)
        }
        // Return an empty string if the email format is invalid
        return ""
    }

    fun String.getDomainFromEmail(): String {
        // Find the index of "@" symbol in the email
        val atIndex = indexOf('@')
        // If "@" is present and it's not the first or last character
        if (atIndex in 1 until length - 1) {
            // Extract and return the domain part of the email
            return substring(atIndex + 1)
        }
        // Return an empty string if the email format is invalid
        return ""
    }

    fun Logger(msg: String, isDebug: Boolean = true) {
        if (isDebug) println("Kapil ke logs. Message hai $msg")
    }
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun Toast(message: String) {
        Popup {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Surface(
                    modifier = Modifier.padding(16.dp),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.8f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(2.dp))
                        Image(painter = painterResource("icon.png"), modifier = Modifier.size(24.dp).padding(2.dp), contentDescription = "")
                        Text(
                            text = message,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                            style = TextStyle(fontFamily = appFont)
                        )
                    }
                }
            }
        }
    }
}