import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import java.io.Serial

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings = Settings()

        if (settings.getString(Constants.KEY_INSTITUTE_ID, "null") != "null") {
            if (settings.getString(Constants.KEY_CLASS_LIST, "null") != "null") navigator.push(
                DashboardScreen(navigator)
            )
            else navigator.push(ClassConfigScreen(navigator))
        } else navigator.push(LoginScreen(navigator))

    }

    companion object {
        @Serial
        private const val serialVersionUID: Long = 5308020114394980219L
    }

}
