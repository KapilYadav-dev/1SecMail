package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appFont
import model.EmailBody
import org.jetbrains.compose.resources.ExperimentalResourceApi
import res.blackColor
import res.blueColor
import res.whiteColor

@ExperimentalResourceApi
@Composable
fun MailView(item: EmailBody, idx: Int) {

    Row(
        modifier = Modifier
            .clickable(onClick = {})
            .padding(start = 8.dp, end = 16.dp, bottom = 24.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(end = 16.dp)
        ) {
            CircularViewWithInitials(item.from, backgroundColor = blueColor, initialsColor = whiteColor)
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.from,
                fontSize = 18.sp,
                fontFamily = appFont
            )
            Spacer(modifier =Modifier.height(5.dp))
            Text(
                text = item.subject,
                fontSize = 14.sp,
                fontFamily = appFont,
                color = blackColor.copy(alpha = 0.6f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier =Modifier.height(1.dp))
        }
    }
}

@Composable
fun CircularViewWithInitials(name: String,backgroundColor:Color,initialsColor:Color) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(backgroundColor, CircleShape)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.take(2).lowercase(),
            style = TextStyle(color = initialsColor, fontFamily = appFont, fontSize = 16.sp, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
    }
}