@file:OptIn(DelicateCoroutinesApi::class)

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.Serial

private val poppinsFont = FontFamily(
    Font(
        resource = "poppins.ttf", weight = FontWeight.W200, style = FontStyle.Normal
    )
)

class DashboardScreen() : Screen {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {

        MaterialTheme {
            Column(Modifier.fillMaxSize()) {

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                        text = "Connect - Be Present",
                        fontFamily = poppinsFont,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(Modifier.weight(1f))

                    Icon(
                        modifier = Modifier.padding(12.dp).size(48.dp).clip(CircleShape)
                            .padding(4.dp).align(Alignment.CenterVertically).clickable {
                                Settings().remove(Constants.KEY_INSTITUTE_ID)
                            },
                        painter = painterResource("account.png"),
                        contentDescription = "profile"
                    )
                }

                Column(
                    Modifier.fillMaxSize().padding(32.dp, 16.dp, 32.dp, 0.dp)
                        .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                        .background(Color(0xFFB5B7C0))
                ) {

                    Text(
                        modifier = Modifier.align(Alignment.Start).padding(16.dp).fillMaxWidth(0.5f)
                            .padding(16.dp, 8.dp, 16.dp, 0.dp),
                        text = "Classroom Configuration",
                        fontFamily = poppinsFont,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 2.sp
                    )

                    var classCount by remember { mutableStateOf("10") }
                    var divisionCount by remember { mutableStateOf("5") }

                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Spacer(Modifier.weight(1f))

                        OutlinedTextField(modifier = Modifier.padding(16.dp),
                            value = classCount,
                            onValueChange = {
                                if (it.isBlank()) classCount = "0"
                                else classCount = it
                            },
                            label = { Text("Number of Classes", fontFamily = poppinsFont) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            leadingIcon = @Composable {
                                Icon(
                                    painterResource("school.png"), "badge icon"
                                )
                            })

                        Spacer(Modifier.weight(1f))

                        OutlinedTextField(modifier = Modifier.padding(16.dp),
                            value = divisionCount,
                            onValueChange = {
                                if (it.isBlank()) divisionCount = "0"
                                else divisionCount = it
                            },
                            label = { Text("Number of Divisions", fontFamily = poppinsFont) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            leadingIcon = @Composable {
                                Icon(
                                    painterResource("school.png"), "badge icon"
                                )
                            })
                        Spacer(Modifier.weight(1f))
                    }

                    Row {

                        Spacer(Modifier.weight(1f))

                        Column {

                            var row = 1
                            var column = 0

                            while (row < classCount.toInt() + 1) {

                                Row {

                                    while (column < divisionCount.toInt()) {

                                        Chip(
                                            modifier = Modifier.padding(4.dp),
                                            onClick = {
                                            },
                                            content = {
                                                Icon(
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .padding(2.dp),
                                                    painter = painterResource("check.png"),
                                                    contentDescription = "check"
                                                )
                                                Text("$row ${(column + 65).toChar()}")
                                            },
                                        )
                                        column++
                                    }
                                }

                                column = 0
                                row++
                            }

                        }

                        Spacer(Modifier.weight(1f))

                    }

                    Spacer(Modifier.weight(1f))

                    Row {

                        Spacer(Modifier.weight(1f))

                        TextButton(modifier = Modifier.padding(32.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF292D32))
                            .padding(8.dp), onClick = {

                        }, content = {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(color = Color.White)) {
                                        append("Submit")
                                    }
                                },
                                fontFamily = poppinsFont,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                        }, shape = RoundedCornerShape(32.dp)
                        )
                    }
                }
            }
        }
    }

    companion object {
        @Serial
        private const val serialVersionUID: Long = -6432837658175213004L
    }
}
