package model

@kotlinx.serialization.Serializable
data class EmailMessage(
    val attachments: List<Attachment>,
    val body: String,
    val date: String,
    val from: String,
    val htmlBody: String,
    val id: Int,
    val subject: String,
    val textBody: String
)