package model

import AppSerializable

@kotlinx.serialization.Serializable
data class EmailMessage(
    val attachments: List<Attachment>?=null,
    val body: String?="Explore The Newest Fits Of The Season \uD83C\uDF42",
    val date: String?=null,
    val from: String?="updates@myntra.com",
    val htmlBody: String?= "",
    val id: Int?=null,
    val subject: String?="Just Dropped ⬇️ The Latest Styles On Myntra \uD83E\uDD70",
    val textBody: String?="Explore The Newest Fits Of The Season \uD83C\uDF42"
):AppSerializable