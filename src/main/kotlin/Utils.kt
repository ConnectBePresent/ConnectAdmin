import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Utils {
    companion object {
        fun generatePassword(): String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

            return List(6) { allowedChars.random() }.joinToString("")
        }

        fun getDate(millis: Long): String {
            return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(Date(millis))
        }
    }
}