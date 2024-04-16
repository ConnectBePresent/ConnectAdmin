@file:OptIn(DelicateCoroutinesApi::class)

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.Serial

private val poppinsFont = FontFamily(
    Font(
        resource = "poppins.ttf", weight = FontWeight.W200, style = FontStyle.Normal
    )
)

class DashboardScreen() : Screen {

    @Composable
    override fun Content() {

        MaterialTheme {
            Column(Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Connect - Be Present",
                    fontFamily = poppinsFont,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.weight(1f))
            }
        }
    }

    companion object {
        @Serial
        private const val serialVersionUID: Long = -6432837658175213004L
    }
}
