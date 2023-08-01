package screens

import FileDownloader
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appFont
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import components.MailView
import kotlinx.coroutines.launch
import model.EmailBody
import model.EmailMessage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import res.whiteColor
import showToast
import viewModels.AppViewModel


class EmailBodyScreen(private val body: EmailMessage) : Screen {
    override val key: ScreenKey
        get() = "emailBodyScreen"

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { AppViewModel() }
        val navigator = LocalNavigator.current
        val scrollState = rememberScrollState()
        val scope = rememberCoroutineScope()
        var showToast by rememberSaveable{ mutableStateOf("") }

        if(showToast.isNotEmpty()) {
            showToast(showToast)
            showToast=""
        }

        Column(
            Modifier.background(whiteColor).padding(8.dp).fillMaxSize().verticalScroll(scrollState)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier.padding(16.dp).size(
                        24.dp
                    ).clickable {
                        navigator?.push(HomeScreen())
                    },
                    contentDescription = "back"
                )
                if (body.attachments?.isNotEmpty() == true) {
                    Icon(
                        painter = painterResource("attachment.xml"),
                        tint = Color.Black.copy(alpha = 0.8f),
                        modifier = Modifier.padding(16.dp).size(
                            24.dp
                        ).clickable {
                            body.attachments.forEach {
                                val url = viewModel.getDownloadUrl(body.id.toString(), it.filename)
                                val path = "../${it.filename}"
                                scope.launch {
                                    val isSuccess = FileDownloader().downloadFile(url, path)
                                    println(isSuccess)
                                }
                            }
                            showToast="Downloading finished"
                        },
                        contentDescription = "download"
                    )
                }

            }
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