package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appFont
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import components.MailView
import model.EmailBody
import model.EmailMessage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import res.whiteColor


class EmailBodyScreen(private val body: EmailMessage) : Screen {
    override val key: ScreenKey
        get() = "emailBodyScreen"

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val scrollState = rememberScrollState()

        Column(
            Modifier.background(whiteColor).padding(8.dp).fillMaxSize().verticalScroll(scrollState)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier.padding(16.dp).size(
                    24.dp
                ).clickable {
                    navigator?.pop()
                },
                contentDescription = "back"
            )
            Text(
                text = body.subject.toString(),
                fontFamily = appFont,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            MailView(
                item = EmailBody(
                    body.date.toString(),
                    body.from.toString(),
                    body.id ?: 1,
                    "to me"
                ), onClick = {

                }, idx = 1
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = body.textBody.toString(),
                fontFamily = appFont,
                fontSize = 18.sp
            )
        }
    }

}