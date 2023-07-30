package model

@kotlinx.serialization.Serializable
data class Attachment(
    val contentType: String,
    val filename: String,
    val size: Long
)