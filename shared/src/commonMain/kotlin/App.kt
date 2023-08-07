import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import screens.SplashScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    MaterialTheme {
        Navigator(SplashScreen(), onBackPressed = {
            false
        }) {
            FadeTransition(it)
        }
    }
}