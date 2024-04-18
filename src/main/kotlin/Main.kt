import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import com.google.gson.GsonBuilder
import com.russhwolf.settings.Settings
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val firebaseDatabaseAPI: FirebaseDatabaseAPI =
    Retrofit.Builder().baseUrl(Constants.DB_BASE_URL).client(
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        ).build()
    ).addConverterFactory(
        GsonConverterFactory.create(
            GsonBuilder().setLenient().serializeNulls().create()
        )
    ).build().create(FirebaseDatabaseAPI::class.java);

val firebaseAuthAPI: FirebaseAuthAPI = Retrofit.Builder().baseUrl(Constants.AUTH_BASE_URL).client(
    OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    ).build()
).addConverterFactory(
    GsonConverterFactory.create(
        GsonBuilder().setLenient().serializeNulls().create()
    )
).build().create(FirebaseAuthAPI::class.java);

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

@Composable
fun App() {

//    GlobalScope.launch { }

    if (Settings().getString(Constants.KEY_INSTITUTE_ID, "null") != "null") Navigator(
        DashboardScreen()
    )
    else Navigator(LoginScreen())
}
