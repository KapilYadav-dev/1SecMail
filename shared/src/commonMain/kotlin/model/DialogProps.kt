package model

@kotlinx.serialization.Serializable
data class DialogProps(
    val text: String?=null,
    val onClick: () -> Unit
)