class Utils {
    companion object {
        fun generatePassword(): String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

            return List(6) { allowedChars.random() }.joinToString("")
        }
    }
}