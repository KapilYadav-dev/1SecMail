import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.HomeScreen
import viewModels.AppViewModel
import viewModels.UiState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        HomeScreen()
    }
}