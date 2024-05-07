import androidx.compose.ui.graphics.Color
import okhttp3.logging.HttpLoggingInterceptor

object Constants {

    val SURFACE_COLOR = Color(0x706750A4)

    val LOGLEVEL: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BASIC

    const val AUTH_BASE_URL = "https://identitytoolkit.googleapis.com/"

    const val KEY_INSTITUTE_ID: String = "institute_id"
    const val KEY_CLASS_LIST: String = "class_list"

    const val DASH_CLASS_LIST: String = "dash_class_list_screen";
    const val DASH_ATTENDANCE_HISTORY: String = "dash_class_attendance_history";


    const val DB_BASE_URL = "https://connect-7bdb2-default-rtdb.firebaseio.com/"
}