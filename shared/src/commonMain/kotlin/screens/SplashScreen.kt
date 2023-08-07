package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appFont
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import res.blackColor
import res.blueColor

@OptIn(ExperimentalResourceApi::class)
class SplashScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        LaunchedEffect(Unit) {
            delay(1500)
            navigator?.push(HomeScreen())
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource("icon.png"),
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "1SecMail",
                    color = blueColor,
                    fontSize = 22.sp,
                    fontFamily = appFont,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                "made with love by mrkaydev in KMP \uD83D\uDE80",
                color = blackColor,
                fontSize = 14.sp,
                fontFamily = appFont,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp)
            )
        }
    }
}