package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appFont
import model.EmailBody
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import res.blackColor

@ExperimentalResourceApi
@Composable
fun MailView(item: EmailBody, idx: Int) {

    Row(
        modifier = Modifier
            .clickable(onClick = {})
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp)
        ) {
            // Placeholder for sender's avatar/image
            Image(
                painterResource("compose-multiplatform.xml"),
                modifier = Modifier.fillMaxWidth().size(48.dp).clip(CircleShape),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
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