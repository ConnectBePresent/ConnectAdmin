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
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val poppinsFont = FontFamily(
    Font(
        resource = "poppins.ttf", weight = FontWeight.W200, style = FontStyle.Normal
    )
)

val firebaseAPI: FirebaseAPI = Retrofit.Builder().baseUrl(Constants.DB_BASE_URL)
//    .client(
//        OkHttpClient.Builder().addInterceptor(
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//        ).build()
//    )
    .addConverterFactory(
        GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        )
    ).build().create(FirebaseAPI::class.java);

@Composable
fun App() {

    var is_login by remember { mutableStateOf(true) }

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

                if (is_login) is_login = Login(is_login)
                else is_login = SignUp(is_login)
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SignUp(current: Boolean): Boolean {

    var is_login by remember { mutableStateOf(current) }

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

        var email by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }

        var password by remember { mutableStateOf("") }
        var passwordVisibility: Boolean by remember { mutableStateOf(false) }

        var confirmPassword by remember { mutableStateOf("") }
        var confirmPasswordVisibility: Boolean by remember { mutableStateOf(false) }

        var isError: Boolean by remember { mutableStateOf(false) }

        OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally)
            .padding(16.dp, 16.dp, 16.dp, 0.dp).fillMaxWidth(0.5f),
            value = username,
            onValueChange = {
                username = it
            },
            label = { Text("Username", fontFamily = poppinsFont) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = @Composable { Icon(painterResource("person.png"), "person icon") })

        OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally)
            .padding(16.dp, 16.dp, 16.dp, 0.dp).fillMaxWidth(0.5f),
            value = email,
            onValueChange = {
                email = it
            },
            label = { Text("E-mail", fontFamily = poppinsFont) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = @Composable { Icon(painterResource("mail.png"), "message icon") })

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

                if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    isError = true
                    return@TextButton
                }

                isError = false
            },
            content = {
                Text(text = buildAnnotatedString {
                    if (isError) withStyle(SpanStyle(color = Color.White)) {
                        append("Register")
                    }
                    else withStyle(SpanStyle(color = Color.Red)) {
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
                    is_login = true
                },
                content = {
                    Text(text = buildAnnotatedString {
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
                    }, fontFamily = poppinsFont, fontSize = 12.sp, fontWeight = FontWeight.Light)
                },
            )
        }
    }
    return is_login
}

@Composable
fun Login(current: Boolean): Boolean {

    var is_login by remember { mutableStateOf(current) }

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

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisibility: Boolean by remember { mutableStateOf(false) }

        OutlinedTextField(modifier = Modifier.align(Alignment.CenterHorizontally)
            .padding(16.dp, 16.dp, 16.dp, 0.dp).fillMaxWidth(0.5f),
            value = email,
            onValueChange = {
                email = it
            },
            label = { Text("E-mail", fontFamily = poppinsFont) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = @Composable { Icon(painterResource("mail.png"), "message icon") })

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

                GlobalScope.launch {

                    println(
                        firebaseAPI.getInstitutionsList()
                    )
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
                    is_login = false
                },
                content = {
                    Text(text = buildAnnotatedString {
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
                    }, fontFamily = poppinsFont, fontSize = 12.sp, fontWeight = FontWeight.Light)
                },
            )

            Spacer(Modifier.weight(1.0f))

            TextButton(
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(16.dp, 0.dp, 0.dp, 16.dp),
                onClick = {

                },
                content = {
                    Text(text = buildAnnotatedString {
                        withStyle(SpanStyle(color = Color(0xFF292D32))) {
                            append("Forgot Password?")
                        }
                    }, fontFamily = poppinsFont, fontSize = 12.sp, fontWeight = FontWeight.Light)
                },
            )
        }
    }

    return is_login
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}