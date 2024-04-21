@file:OptIn(DelicateCoroutinesApi::class)

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.russhwolf.settings.Settings
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serial
import kotlin.system.exitProcess

private val poppinsFont = FontFamily(Font(resource = "poppins.ttf"))

//private lateinit var currentScreen: MutableState<String>

class DashboardScreen() : Screen {
    @Composable
    override fun Content() {

        val settings = Settings()

//        currentScreen = remember { mutableStateOf(Constants.DASH_CLASS_CONFIG) }

//        if (settings.getString(Constants.KEY_CLASS_LIST, "null") != "null") currentScreen.value =
//            Constants.DASH_CLASS_LIST

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
                                settings.remove(Constants.KEY_INSTITUTE_ID)
                                settings.remove(Constants.KEY_CLASS_LIST)
                                exitProcess(0)
                            },
                        painter = painterResource("logout.png"),
                        contentDescription = "logout"
                    )
                }

                ClassList()

            }
        }
    }

    private @Composable
    fun ClassList() {
        Column(
            Modifier.fillMaxSize().padding(32.dp, 16.dp, 32.dp, 0.dp)
                .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)).background(Color(0xFFB5B7C0))
        ) {

            var currentSubScreen: MutableState<String> =
                remember { mutableStateOf(Constants.DASH_CLASS_LIST) }

            val data = Settings().getString(
                Constants.KEY_CLASS_LIST, "null"
            )

            val typeToken = object : TypeToken<ArrayList<Class>>() {}
            val classList: ArrayList<Class> = Gson().fromJson(data, typeToken.type)

            var currentClassList = remember { mutableStateListOf<Class>() }

            currentClassList = classList.toMutableStateList()

            Row {

                Text(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(0.5f)
                        .padding(16.dp, 8.dp, 8.dp, 0.dp),
                    text = "Classes",
                    fontFamily = poppinsFont,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp
                )

                Spacer(Modifier.weight(1f))

                var buttonText by remember { mutableStateOf("Submit") }

                TextButton(modifier = Modifier.padding(16.dp).align(Alignment.CenterVertically)
                    .clip(RoundedCornerShape(12.dp)).background(Color(0xFF292D32)).padding(8.dp),
                    onClick = {

                        GlobalScope.launch {

                            buttonText = "Updating..."

                            val instituteID = Settings().getString(
                                Constants.KEY_INSTITUTE_ID, "null"
                            )

                            firebaseDatabaseAPI.setClassList(
                                instituteID, classList
                            )

                            buttonText = "Done!"
                            delay(2000)
                            buttonText = "Submit"
                        }

                    },
                    content = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = Color.White)) {
                                    append(buttonText)
                                }
                            },
                            fontFamily = poppinsFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    },
                    shape = RoundedCornerShape(32.dp)
                )

            }
            var selectedClass by remember {
                mutableStateOf(
                    classList.get(0)
                )
            }

            var currentStudentList = remember { mutableStateListOf<Student>() }

            Row(Modifier.fillMaxSize()) {
                Column(
                    Modifier.fillMaxHeight().fillMaxWidth(0.35f).padding(16.dp, 8.dp, 16.dp, 0.dp)
                        .clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colors.background)
                ) {
                    LazyColumn(Modifier.weight(1f)) {
                        items(currentClassList) {

                            Row(modifier = Modifier.fillMaxWidth().padding(8.dp, 8.dp, 8.dp, 0.dp)
                                .clip(RoundedCornerShape(8.dp)).background(Color(0xFFB5B7C0))
                                .clickable {
                                    selectedClass = it

                                    if (selectedClass.studentList == null) selectedClass.studentList =
                                        ArrayList()

                                    currentStudentList.clear()
                                    currentStudentList.addAll(selectedClass.studentList!!.toMutableStateList())
                                }) {
                                Column {
                                    Text(
                                        "${it.standard}${it.division}",
                                        modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp),
                                        style = MaterialTheme.typography.body1,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )

                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("Email: ")
                                            }
                                            append(it.teacherEmail)
                                        },
                                        modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp),
                                        letterSpacing = 2.sp,
                                        style = MaterialTheme.typography.caption,
                                    )

                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("Password: ")
                                            }
                                            append(it.teacherPassword)
                                        },
                                        modifier = Modifier.padding(all = 8.dp),
                                        letterSpacing = 2.sp,
                                        style = MaterialTheme.typography.caption,
                                    )
                                }

                                Spacer(Modifier.weight(1f))

                                Icon(
                                    modifier = Modifier.padding(8.dp).size(24.dp)
                                        .align(Alignment.CenterVertically).clickable {

                                            currentClassList.remove(it)

                                            classList.remove(it)

                                            Settings().putString(
                                                Constants.KEY_CLASS_LIST, Gson().toJson(classList)
                                            )

                                        },
                                    painter = painterResource("delete.png"),
                                    contentDescription = "class delete icon"
                                )

                            }
                        }
                    }

                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        var newClass by remember { mutableStateOf(0) }
                        var newDivision by remember { mutableStateOf("") }

                        OutlinedTextField(modifier = Modifier.padding(16.dp).weight(1f),
                            value = newClass.toString(),
                            onValueChange = {
                                try {
                                    newClass = it.trim().toInt()
                                } catch (e: NumberFormatException) {
                                    newClass = 0
                                }
                            },
                            label = { Text("Class", fontFamily = poppinsFont) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            leadingIcon = @Composable {
                                Icon(
                                    painterResource("person.png"), "person icon"
                                )
                            })

                        OutlinedTextField(modifier = Modifier.padding(16.dp).weight(1f),
                            value = newDivision,
                            onValueChange = {
                                newDivision = it
                            },
                            label = {
                                Text(
                                    "Division", fontFamily = poppinsFont
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            leadingIcon = @Composable {
                                Icon(
                                    painterResource("school.png"), "name icon"
                                )
                            })

                        TextButton(modifier = Modifier.padding(32.dp)
                            .align(Alignment.CenterVertically).clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF292D32)).padding(8.dp).weight(1f), onClick = {

                            val instituteID =
                                Settings().getString(Constants.KEY_INSTITUTE_ID, "null")

                            val element = Class(
                                newClass,
                                newDivision,
                                "$newClass$newDivision@$instituteID.com",
                                Utils.generatePassword()
                            )

                            classList.add(element)

                            currentClassList.add(element) // just to update visibility

                            Settings().putString(
                                Constants.KEY_CLASS_LIST, Gson().toJson(classList)
                            )

                        }, content = {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(color = Color.White)) {
                                        append("Add Class")
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

                Column(
                    Modifier.fillMaxHeight().fillMaxWidth().padding(16.dp, 8.dp, 16.dp, 0.dp)
                        .clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colors.background)
                ) {

                    Row {

                        Text(
                            modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(8.dp))
//                                .border(BorderStroke(1.dp, Color(0xFF292D32)))
                                .padding(8.dp),
                            text = "${selectedClass.standard} ${selectedClass.division}",
                            fontFamily = poppinsFont,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,

//                            shape = RoundedCornerShape(12.dp),
//                            contentPadding = PaddingValues(12.dp),
                        )

                        Spacer(Modifier.weight(1f))

                        TextButton(
                            modifier = Modifier.align(Alignment.CenterVertically).padding(16.dp),
                            border = BorderStroke(
//                                if (currentScreen.value == Constants.DASH_CLASS_LIST) 4.dp else
                                1.dp, Color(0xFF292D32)
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(
                                    0xFF292D32
                                )
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(12.dp),
                            onClick = {},
                            content = {
                                Text(
                                    text = "Class List",
                                    fontFamily = poppinsFont,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                        )

                        TextButton(
                            modifier = Modifier.align(Alignment.CenterVertically).padding(16.dp),
                            border = BorderStroke(
//                                if (currentScreen.value == Constants.DASH_CLASS_CONFIG) 4.dp else
                                    1.dp,
                                Color(0xFF292D32)
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(
                                    0xFF292D32
                                )
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(12.dp),
                            onClick = {},
                            content = {
                                Text(
                                    text = "Attendance Details",
                                    fontFamily = poppinsFont,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                        )

                    }
                    Column {

                        if (currentStudentList.isEmpty()) {
                            Image(
                                modifier = Modifier.fillMaxSize(0.5f)
                                    .align(Alignment.CenterHorizontally),
                                painter = painterResource("undraw_no_data.png"),
                                contentDescription = "undraw_no_data"
                            )
                        }

                        LazyColumn(modifier = Modifier.weight(1f)) {

                            items(currentStudentList) {

                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(8.dp, 8.dp, 8.dp, 0.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFB5B7C0))
                                ) {
                                    Column() {
                                        Text(
                                            "${it.rollNumber} | ${it.name}",
                                            modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp),
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 2.sp
                                        )

                                        Text(
                                            text = it.parentPhoneNumber,
                                            modifier = Modifier.padding(all = 8.dp),
                                            letterSpacing = 4.sp,
                                            style = MaterialTheme.typography.caption,
                                        )
                                    }

                                    Spacer(Modifier.weight(1f))

                                    Icon(
                                        modifier = Modifier.padding(8.dp).size(24.dp)
                                            .align(Alignment.CenterVertically).clickable {

                                                currentStudentList.remove(it)
                                                selectedClass.studentList?.remove(it)

                                                currentClassList.remove(selectedClass)
                                                currentClassList.add(selectedClass)

                                                classList.remove(selectedClass)
                                                classList.add(selectedClass)

                                                Settings().putString(
                                                    Constants.KEY_CLASS_LIST,
                                                    Gson().toJson(classList)
                                                )

                                            },
                                        painter = painterResource("delete.png"),
                                        contentDescription = "student delete icon"
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.wrapContentSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            var rollNumber by remember { mutableStateOf(0) }
                            var name by remember { mutableStateOf("") }
                            var phoneNumber by remember { mutableStateOf("") }

                            Spacer(Modifier.weight(1f))

                            OutlinedTextField(modifier = Modifier.padding(16.dp),
                                value = rollNumber.toString(),
                                onValueChange = {
                                    try {
                                        rollNumber = it.trim().toInt()
                                    } catch (e: NumberFormatException) {
                                        rollNumber = 0
                                    }
                                },
                                label = { Text("Roll Number", fontFamily = poppinsFont) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                leadingIcon = @Composable {
                                    Icon(
                                        painterResource("person.png"), "person icon"
                                    )
                                })

                            Spacer(Modifier.weight(1f))

                            OutlinedTextField(modifier = Modifier.padding(16.dp),
                                value = name,
                                onValueChange = {
                                    name = it
                                },
                                label = {
                                    Text(
                                        "Student Name", fontFamily = poppinsFont
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                leadingIcon = @Composable {
                                    Icon(
                                        painterResource("name.png"), "name icon"
                                    )
                                })

                            Spacer(Modifier.weight(1f))

                            OutlinedTextField(modifier = Modifier.padding(16.dp),
                                value = phoneNumber,
                                onValueChange = {
                                    phoneNumber = it
                                },
                                label = {
                                    Text(
                                        "Parent Phone Number", fontFamily = poppinsFont
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                leadingIcon = @Composable {
                                    Icon(
                                        painterResource("phone.png"), "phone icon"
                                    )
                                })
                            Spacer(Modifier.weight(1f))

                            TextButton(modifier = Modifier.padding(32.dp)
                                .align(Alignment.CenterVertically).clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF292D32)).padding(8.dp), onClick = {

                                val student = Student(rollNumber, name, phoneNumber)

                                selectedClass.studentList?.add(student)

                                classList.remove(selectedClass)
                                classList.add(selectedClass)

                                Settings().putString(
                                    Constants.KEY_CLASS_LIST, Gson().toJson(classList)
                                )

                                currentStudentList.add(student) // just to update the visible list

                            }, content = {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(SpanStyle(color = Color.White)) {
                                            append("Add Student")
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
    }

    companion object {
        @Serial
        private const val serialVersionUID: Long = -6432837658175213004L
    }
}