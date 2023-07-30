package viewModels

import Clipboard
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.EmailBody
import networking.BASE_URL
import networking.httpClient
import utils.Utils.Logger
import utils.Utils.getDomainFromEmail
import utils.Utils.getNameFromEmail

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> get() = _uiState
    var email = mutableStateOf("")
    var emailList by mutableStateOf(mutableListOf<EmailBody>())
    private var job: Job? = null

    init {
        fetchMail()
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close()
        if (job != null && job?.isActive == true) job?.cancel()
    }

    private fun fetchMail() {
        viewModelScope.launch {
            try {
                _uiState.emit(UiState.Loading)
                getNewMail().getOrNull(0)?.let {
                    email.value = it
                    _uiState.emit(UiState.Success(it))
                } ?: run {
                    _uiState.emit(UiState.Error("Email can't be fetch"))
                }
            } catch (e: Exception) {
                _uiState.emit(UiState.Error("Error loading data"))
            }
        }
    }

    private suspend fun getNewMail(): List<String> {
        val response = httpClient.get {
            url {
                takeFrom(BASE_URL)
                parameters.append("action", "genRandomMailbox")
                parameters.append("count", "1")
            }
        }
        if (response.status == HttpStatusCode.OK) {
            if (job != null && job!!.isActive) job?.cancel("Already a email fetching job was running so closing this one.")
            job = repeatApiCall()
        }
        Logger("Email is ${response.body<List<String>>()[0]}")
        return response.body()
    }

    private fun repeatApiCall(): Job {
        // Use the CoroutineScope to launch the coroutine
        val scope = CoroutineScope(Dispatchers.Default)
        return scope.launch {
            var isRunning = true

            while (isRunning) {
                // Perform your API call here (replace with your actual API call code)
                emailList = getEmailList().toMutableList()
                // Delay for 5 seconds before the next iteration
                delay(5000L)
                // Check if the coroutine should be stopped
                if (!isActive) {
                    isRunning = false
                }
            }
        }
    }

    private suspend fun getEmailList(): List<EmailBody> {
        val response = httpClient.get {
            url {
                takeFrom(BASE_URL)
                parameters.append("action", "getMessages")
                parameters.append("login", email.value.getNameFromEmail())
                parameters.append("domain", email.value.getDomainFromEmail())
            }
        }
        Logger("Email list is ${response.body<List<EmailBody>>()}")
        return response.body()
    }

    fun generateNewMail() {
        clearEmailList()
        viewModelScope.launch {
            getNewMail().getOrNull(0)?.let {
                email.value = it
            } ?: kotlin.run {
                TODO("SET A PROMPT TO SHOW email can't be fetch")
            }
        }
    }

    private fun clearEmailList() {
        if (job != null && job!!.isActive) job?.cancel()
        emailList.clear()
        Logger("Clear ho jana chiye tha")
    }

    fun copyMail(email: String) {
        Clipboard.copyTextToClipboard(email)
    }

}

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: Any) : UiState()
    data class Error(val message: String) : UiState()
}

fun getFakeMailList() = listOf<EmailBody>(
    EmailBody("","infokaydev@gmail.vom",1,"Method boolean androidx.compose.runtime.snapshots.SnapshotStateList.conditionalUpdate(kotlin.jvm.functions.Function1) failed lock verification"),
    EmailBody("","infokaydev@gmail.vom",2,"Method boolean androidx.compose.runtime.snapshots.SnapshotStateList.conditionalUpdate(kotlin.jvm.functions.Function1) failed lock verification"),
    EmailBody("","infokaydev@gmail.vom",3,"Method boolean androidx.compose.runtime.snapshots.SnapshotStateList.conditionalUpdate(kotlin.jvm.functions.Function1) failed lock verification"),
    EmailBody("","infokaydev@gmail.vom",4,"Method boolean androidx.compose.runtime.snapshots.SnapshotStateList.conditionalUpdate(kotlin.jvm.functions.Function1) failed lock verification"),
    EmailBody("","infokaydev@gmail.vom",5,"Method boolean androidx.compose.runtime.snapshots.SnapshotStateList.conditionalUpdate(kotlin.jvm.functions.Function1) failed lock verification"),
    EmailBody("","infokaydev@gmail.vom",6,"Method boolean androidx.compose.runtime.snapshots.SnapshotStateList.conditionalUpdate(kotlin.jvm.functions.Function1) failed lock verification"),
)