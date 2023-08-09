package components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import showToast
import utils.Utils
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun CtaIconButton(
    text: String,
    action: CtaIconButtonActions,
    modifier: Modifier,
    shouldAnimate: Boolean = false,
    debounceTimer: Duration = 2.seconds,
    onClick: (CtaIconButtonActions) -> Unit = {}
) {
    val rotationState = rememberSaveable { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var isBtnEnabled by rememberSaveable { mutableStateOf(true) }
    var showToast by rememberSaveable { mutableStateOf("") }

    if(showToast.isNotEmpty()) {
        showToast("please wait, we are on free api. Click after ${debounceTimer.inWholeSeconds} seconds")
        scope.launch {
            delay(Utils.TOAST_TIMER)
            showToast=""
        }
    }

    Row(
        modifier = modifier.border(
            2.dp,
            MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
            RoundedCornerShape(12.dp)
        ).padding(horizontal = 16.dp, vertical = 16.dp).clickable {
            if (isBtnEnabled) {

                isBtnEnabled = false

                onClick(action)

                if (shouldAnimate)
                    scope.launch {
                        rotationState.animateTo(
                            targetValue = rotationState.targetValue + 360f,
                            animationSpec = tween(durationMillis = 1000)
                        )
                    }

                scope.launch {
                    delay(debounceTimer)
                    isBtnEnabled = true
                }
            } else showToast = "please wait, we are on free api. Click after ${debounceTimer.inWholeSeconds} seconds"
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = getIcon(action),
            "",
            tint = MaterialTheme.colors.onSurface.copy(alpha = 0.40f),
            modifier = Modifier.size(24.dp).graphicsLayer(rotationZ = rotationState.value)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text,
            fontFamily = appFont,
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.40f)
        )
    }
}

fun getIcon(action: CtaIconButtonActions): ImageVector {
    return when (action) {
        CtaIconButtonActions.CopyMail -> {
            Icons.Outlined.AccountBox
        }
        CtaIconButtonActions.RegenerateMail -> {
            Icons.Outlined.Refresh
        }
    }
}

sealed class CtaIconButtonActions {
    object CopyMail : CtaIconButtonActions()
    object RegenerateMail : CtaIconButtonActions()
}