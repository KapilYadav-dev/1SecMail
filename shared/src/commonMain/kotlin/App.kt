import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import model.DialogProps
import screens.SplashScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    var isBackPressed by rememberSaveable { mutableStateOf(false) }
    if (isBackPressed) showDialog(
        "exit app ?",
        "do you really want to exit app ?",
        DialogProps("yes") {
            exitApp()
        },
        DialogProps("no") {
            isBackPressed = false
        }
    )
    MaterialTheme {
        Navigator(SplashScreen(), onBackPressed = {
            isBackPressed = it.key == "homeScreen"
            false
        }) {
            FadeTransition(it)
        }
    }
}