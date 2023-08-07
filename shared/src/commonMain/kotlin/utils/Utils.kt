package utils

object Utils {
    const val platformNameAndroid = "android"
    const val platformNameIOS = "ios"
    const val platformNameDesktop = "desktop"

    fun String.getNameFromEmail(): String {
        val atIndex = indexOf('@')
        // If "@" is present and it's not the first or last character
        if (atIndex in 1 until length - 1) {
            // Extract and return the name part of the email
            return substring(0, atIndex)
        }
        // Return an empty string if the email format is invalid
        return ""
    }

    fun String.getDomainFromEmail(): String {
        // Find the index of "@" symbol in the email
        val atIndex = indexOf('@')
        // If "@" is present and it's not the first or last character
        if (atIndex in 1 until length - 1) {
            // Extract and return the domain part of the email
            return substring(atIndex + 1)
        }
        // Return an empty string if the email format is invalid
        return ""
    }

    fun Logger(msg: String, isDebug: Boolean = true) {
        if (isDebug) println("Kapil ke logs. Message hai $msg")
    }
}