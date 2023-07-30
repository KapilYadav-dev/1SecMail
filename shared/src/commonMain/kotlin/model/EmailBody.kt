package model

@kotlinx.serialization.Serializable
data class EmailBody(
    val date: String,
    val from: String,
    val id: Int,
    val subject: String
)