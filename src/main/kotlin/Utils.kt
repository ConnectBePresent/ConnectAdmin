class Utils {
    companion object {
        fun generatePassword(): String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

            return List(5) { allowedChars.random() }.joinToString("")
        }
    }
}