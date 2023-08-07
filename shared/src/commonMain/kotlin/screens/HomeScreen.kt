package screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appFont
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import components.CtaIconButton
import components.CtaIconButtonActions
import components.EmailBox
import components.MailView
import exitApp
import kotlinx.coroutines.launch
import model.DialogProps
import model.EmailBody
import model.EmailMessage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import platformName
import res.blackColor
import res.blueColor
import res.whiteColor
import showDialog
import showToast
import utils.Utils
import utils.Utils.platformNameAndroid
import viewModels.AppViewModel
import viewModels.UiState


@ExperimentalResourceApi
class HomeScreen : Screen {

    override val key: ScreenKey
        get() = "homeScreen"

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.current
        val viewModel = rememberScreenModel { AppViewModel() }
        val uiState by viewModel.uiState.collectAsState()
        var showToast by rememberSaveable { mutableStateOf("") }
        val clipboardManager = LocalClipboardManager.current
        val emailList: List<EmailBody> by viewModel.emailList.collectAsState()
        val errorState: String by viewModel.errorPipeLine.collectAsState()
        val topPadding = if (platformName == platformNameAndroid) 0.dp else 16.dp

        if (showToast.isNotEmpty()) {
            showToast(showToast)
            showToast=""
        }
        /*
         * Handling error state using dialog box implemented on each platforms
         */
        if (errorState.isNotEmpty()) {
            showDialog(
                msg = "error",
                desc = errorState,
                positiveProps = DialogProps(
                    "retry",
                    onClick = {
                        viewModel.resetError()
                    }
                ),
                negativeProps = DialogProps(
                    "exit",
                    onClick = {
                        exitApp()
                    }
                )
            )
        }
        when (uiState) {
            UiState.Loading -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp),
                            color = blueColor.copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(50.dp))
                        Text(
                            "stealing mail for you ☠",
                            fontFamily = appFont,
                            fontSize = 18.sp,
                            color = blueColor.copy(alpha = 0.3f)
                        )
                    }
                }
            }
            is UiState.Success -> {
                Column(modifier = Modifier.fillMaxSize().background(whiteColor)) {
                    Row(modifier = Modifier.padding(start = 16.dp, top = topPadding)) {
                        Text("TEMPER", fontFamily = appFont, fontSize = 24.sp, color = blueColor)
                        Text("MAIL", fontFamily = appFont, fontSize = 24.sp, color = blackColor)
                    }
                    Divider(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                    )
                    AnimatedVisibility(emailList.isEmpty()) {
                        Image(
                            painterResource("img-before.xml"),
                            modifier = Modifier.fillMaxWidth().height(160.dp),
                            contentScale = ContentScale.Fit,
                            contentDescription = null
                        )
                    }
                    AnimatedVisibility(emailList.isNotEmpty()) {
                        Image(
                            painterResource("img-after.xml"),
                            modifier = Modifier.fillMaxWidth().height(160.dp),
                            contentScale = ContentScale.Fit,
                            contentDescription = null
                        )
                    }
                    /*
                     * This is the email box
                     */
                    EmailBox(email = { viewModel.email.value }, emailCount = { emailList.size })
                    /*
                     * These are the copy and new mail buttons
                     */
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CtaIconButton(
                            "New Mail",
                            CtaIconButtonActions.RegenerateMail,
                            Modifier.weight(1f).padding(start = 16.dp)
                        ) {
                            viewModel.generateNewMail()
                            showToast = "generating new mail"
                        }
                        Spacer(modifier = Modifier.weight(0.1f))
                        CtaIconButton(
                            "Copy Mail",
                            CtaIconButtonActions.CopyMail,
                            Modifier.weight(1f).padding(end = 16.dp)
                        ) {
                            clipboardManager.setText(AnnotatedString(viewModel.email.value))
                            showToast = "copied to clipboard"
                        }
                    }
                    /*
                     * this is our mail box area where mails will appear
                     */
                    if (emailList.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                        ) {
                            itemsIndexed(
                                items = emailList,
                                key = { _, item -> item.id }) { idx, item ->
                                MailView(item, idx) {
                                    scope.launch {
                                        if (viewModel.cache.containsKey(it.id)) {
                                            val data = viewModel.cache[it.id]
                                            navigator?.push(EmailBodyScreen(data ?: EmailMessage()))
                                        } else {
                                            val data = viewModel.openMail(it.id)
                                            viewModel.cache[it.id] = data
                                            navigator?.push(EmailBodyScreen(data))
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        /*
                         * If there is no mail then we'll show fetching status
                         */
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(40.dp),
                                    color = blueColor.copy(alpha = 1f)
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    "you have 0 new messages",
                                    fontFamily = appFont,
                                    fontSize = 18.sp,
                                    color = blackColor.copy(alpha = 1f)
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    "waiting for incoming email",
                                    fontFamily = appFont,
                                    fontSize = 14.sp,
                                    color = blackColor.copy(alpha = 0.3f),
                                    modifier = Modifier.padding(horizontal = 24.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                val message = (uiState as UiState.Error).message
                Utils.Logger(message)
            }
        }

    }

}