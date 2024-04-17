@file:OptIn(DelicateCoroutinesApi::class)

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.google.gson.Gson
import com.russhwolf.settings.Settings
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serial

private val poppinsFont = FontFamily(
    Font(
        resource = "poppins.ttf", weight = FontWeight.W200, style = FontStyle.Normal
    )
)

private lateinit var isSuccess : MutableState<Boolean>

class LoginScreen() : Screen {

    @Composable
    override fun Content() {

        var isLogin by remember { mutableStateOf(true) }

        isSuccess = remember { mutableStateOf(false) }

        MaterialTheme {
            Column {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Connect - Be Present",
                    fontFamily = poppinsFont,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Medium
                )

                Row {
                    Image(
                        modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(),
                        contentScale = ContentScale.FillWidth,
                        painter = painterResource("undraw_two_factor_auth.png"),
                        contentDescription = "illustration"
                    )

                    if (isLogin) isLogin = Login(isLogin)
                    else isLogin = SignUp(isLogin)
                }
            }
        }

        if (isSuccess.value) moveToDashBoard()
    }

    @Composable
    fun SignUp(current: Boolean): Boolean {

        var isLogin by remember { mutableStateOf(current) }

        Column(
            Modifier.fillMaxWidth().fillMaxHeight(), verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Sign up",
                fontFamily = poppinsFont,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
            )

            var instituteID by remember { mutableStateOf("") }

            var password by remember { mutableStateOf("") }
            var passwordVisibility: Boolean by remember { mutableStateOf(false) }

            var confirmPassword by remember { mutableStateOf("") }
            var confirmPasswordVisibility: Boolean by remember { mutableStateOf(false) }

            OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(16.dp, 16.dp, 16.dp, 0.dp).fillMaxWidth(0.5f),
                value = instituteID,
                onValueChange = {
                    instituteID = it
                },
                label = { Text("School ID", fontFamily = poppinsFont) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = @Composable { Icon(painterResource("badge.png"), "badge icon") })

            OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(16.dp, 16.dp, 16.dp, 0.dp).fillMaxWidth(0.5f),
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text("Password", fontFamily = poppinsFont) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = @Composable { Icon(painterResource("padlock.png"), "padlock icon") },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = @Composable {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        if (passwordVisibility) Icon(painterResource("pass_on.png"), "")
                        else Icon(painterResource("pass_off.png"), "")
                    }
                })

            OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(16.dp, 16.dp, 16.dp, 0.dp).fillMaxWidth(0.5f),
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                label = { Text("Confirm password", fontFamily = poppinsFont) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = @Composable { Icon(painterResource("padlock.png"), "padlock icon") },
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = @Composable {
                    IconButton(onClick = {
                        confirmPasswordVisibility = !confirmPasswordVisibility
                    }) {
                        if (confirmPasswordVisibility) Icon(painterResource("pass_on.png"), "")
                        else Icon(painterResource("pass_off.png"), "")
                    }
                })

            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.5f)
                    .padding(16.dp, 16.dp, 16.dp, 0.dp),
                onClick = {

                    if (instituteID.isBlank() || password.isBlank() || confirmPassword.isBlank() || password != confirmPassword) {
                        return@TextButton
                    }

                    GlobalScope.launch {
                        val list = firebaseAPI.getInstitutionsList()

                        list.add(
                            Institution(
                                instituteID.trim(), password.trim()
                            )
                        )

                        firebaseAPI.setInstitutionsList(
                            list
                        )

                        isLogin = true
                    }

                },
                content = {
                    Text(text = buildAnnotatedString {
                        withStyle(SpanStyle(color = Color.White)) {
                            append("Register")
                        }
                    }, fontFamily = poppinsFont, fontSize = 16.sp, fontWeight = FontWeight.Light)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF292D32)),
                shape = RoundedCornerShape(32.dp)
            )

            Row(modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.5f)) {

                TextButton(
                    modifier = Modifier.align(Alignment.CenterVertically)
                        .padding(16.dp, 0.dp, 0.dp, 16.dp),
                    onClick = {
                        isLogin = true
                    },
                    content = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color(0xFF292D32)
                                    )
                                ) {
                                    append("Have an account?")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold, color = Color(0xFF292D32)
                                    )
                                ) {
                                    append(" Login!")
                                }
                            },
                            fontFamily = poppinsFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    },
                )
            }
        }
        return isLogin
    }

    @Composable
    fun Login(current: Boolean): Boolean {

        var isLogin by remember { mutableStateOf(current) }

        Column(
            Modifier.fillMaxWidth().fillMaxHeight(), verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Sign in",
                fontFamily = poppinsFont,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
            )

            var instituteID by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordVisibility: Boolean by remember { mutableStateOf(false) }

            OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(16.dp, 16.dp, 16.dp, 0.dp).fillMaxWidth(0.5f),
                value = instituteID,
                onValueChange = {
                    instituteID = it
                },
                label = { Text("School ID", fontFamily = poppinsFont) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = @Composable { Icon(painterResource("badge.png"), "message icon") })

            OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(16.dp, 16.dp, 16.dp, 0.dp).fillMaxWidth(0.5f),
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text("Password", fontFamily = poppinsFont) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = @Composable { Icon(painterResource("padlock.png"), "padlock icon") },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = @Composable {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        if (passwordVisibility) Icon(painterResource("pass_on.png"), "")
                        else Icon(painterResource("pass_off.png"), "")
                    }
                })

            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.5f)
                    .padding(16.dp, 16.dp, 16.dp, 0.dp),
                onClick = {

                    if (instituteID.isBlank() || password.isBlank()) {
                        return@TextButton
                    }

                    GlobalScope.launch {
                        val list = firebaseAPI.getInstitutionsList()

                        for (institution in list) {
                            if (institution.instituteID.trim() == instituteID) {
                                if (institution.institutePassword.trim() == password) {
                                    Settings().putString(Constants.KEY_INSTITUTE_ID, instituteID)
                                    println("Success!")

                                    if (institution.classList != null) {
                                        Settings().putString(
                                            Constants.KEY_CLASS_LIST,
                                            Gson().toJson(institution.classList)
                                        )
                                    }

                                    isSuccess.value = true

                                    return@launch
                                } else {
                                    println("Wrong password")
                                    return@launch
                                }
                            }
                        }

                        println("No such institution")
                    }
                },
                content = {
                    Text(text = buildAnnotatedString {
                        withStyle(SpanStyle(color = Color.White)) {
                            append("Login")
                        }
                    }, fontFamily = poppinsFont, fontSize = 16.sp, fontWeight = FontWeight.Light)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF292D32)),
                shape = RoundedCornerShape(32.dp)
            )

            Row(modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.5f)) {

                TextButton(
                    modifier = Modifier.align(Alignment.CenterVertically)
                        .padding(16.dp, 0.dp, 0.dp, 16.dp),
                    onClick = {
                        isLogin = false
                    },
                    content = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color(0xFF292D32)
                                    )
                                ) {
                                    append("New here?")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold, color = Color(0xFF292D32)
                                    )
                                ) {
                                    append(" Register!")
                                }
                            },
                            fontFamily = poppinsFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    },
                )

                Spacer(Modifier.weight(1.0f))

                TextButton(
                    modifier = Modifier.align(Alignment.CenterVertically)
                        .padding(16.dp, 0.dp, 0.dp, 16.dp),
                    onClick = {},
                    content = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = Color(0xFF292D32))) {
                                    append("Forgot Password?")
                                }
                            },
                            fontFamily = poppinsFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    },
                )
            }
        }

        return isLogin
    }

    @Composable
    private fun moveToDashBoard() {
        Navigator(DashboardScreen())
    }

    companion object {
        @Serial
        private const val serialVersionUID: Long = 8803730521172732869L
    }

}