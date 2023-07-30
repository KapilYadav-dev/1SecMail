package components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appFont
import res.blueColor

@Composable
fun EmailBox(email: () -> String) {

    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
    horizontalArrangement = Arrangement.Center) {
        Row(
            modifier = Modifier.border(
                2.dp,
                blueColor.copy(alpha = 0.2f),
                RoundedCornerShape(12.dp)
            ).padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.MailOutline,
                "",
                tint = blueColor,
                modifier = Modifier.size(24.dp)
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    email(),
                    fontFamily = appFont,
                    fontSize = 18.sp,
                    color = blueColor
                )
            }
        }
    }
}