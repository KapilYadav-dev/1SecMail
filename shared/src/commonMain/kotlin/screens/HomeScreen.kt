package screens

import Platform
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appFont
import components.CtaIconButton
import components.CtaIconButtonActions
import components.EmailBox
import components.MailView
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import res.blackColor
import res.blueColor
import res.whiteColor
import showToast
import utils.Utils
import viewModels.AppViewModel
import viewModels.UiState


@ExperimentalResourceApi
@Composable
fun HomeScreen() {
    val viewModel = getViewModel(Unit, viewModelFactory { AppViewModel() })
    val uiState by viewModel.uiState.collectAsState()
    var showToast by rememberSaveable{ mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current

    val topPadding = if(Platform.platformName == "android") 0.dp else 16.dp

    if(showToast.isNotEmpty()) {
        showToast(showToast)
        showToast=""
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
                Image(
                    painterResource("compose-multiplatform.xml"),
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )
                /*
                 * This is the email box
                 */
                EmailBox(email = { viewModel.email.value },emailCount={ viewModel.emailList.size })
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
                        showToast="generating new mail"
                    }
                    Spacer(modifier = Modifier.weight(0.1f))
                    CtaIconButton(
                        "Copy Mail",
                        CtaIconButtonActions.CopyMail,
                        Modifier.weight(1f).padding(end = 16.dp)
                    ) {
                        viewModel.copyMail(viewModel.email.value)
                        clipboardManager.setText(AnnotatedString(viewModel.email.value))
                        showToast="copied to clipboard"
                    }
                }
                /*
                 *
                 */
                if(viewModel.emailList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                    ) {
                        itemsIndexed(
                            items = viewModel.emailList,
                            key = { _, item -> item.id }) { idx, item ->
                            MailView(item, idx) {
                                viewModel.openMail(it.id)
                            }
                        }
                    }
                } else {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(40.dp),
                                color = blueColor.copy(alpha = 1f)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                "fetching mail",
                                fontFamily = appFont,
                                fontSize = 18.sp,
                                color = blueColor.copy(alpha = 1f)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                "new mail will automatically appear here",
                                fontFamily = appFont,
                                fontSize = 14.sp,
                                color = blueColor.copy(alpha = 0.3f)
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