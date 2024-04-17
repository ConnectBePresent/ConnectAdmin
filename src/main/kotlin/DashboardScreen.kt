@file:OptIn(DelicateCoroutinesApi::class)

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

class DashboardScreen() : Screen {
    @Composable
    override fun Content() {

        val settings = Settings()

        var currentScreen by remember { mutableStateOf(Constants.DASH_CLASS_CONFIG) }

        if (settings.getString(Constants.KEY_CLASS_LIST, "null") != "null") currentScreen =
            Constants.DASH_CLASS_LIST

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
                            },
                        painter = painterResource("account.png"),
                        contentDescription = "profile"
                    )
                }

                if (currentScreen == Constants.DASH_CLASS_CONFIG) ClassConfig()
                else if (currentScreen == Constants.DASH_CLASS_LIST) ClassList()
            }
        }
    }

    private @Composable
    fun ClassList() {
        Column(
            Modifier.fillMaxSize().padding(32.dp, 16.dp, 32.dp, 0.dp)
                .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)).background(Color(0xFFB5B7C0))
        ) {

            val data = Settings().getString(
                Constants.KEY_CLASS_LIST, "null"
            )

            val typeToken = object : TypeToken<ArrayList<Class>>() {}
            val classList: ArrayList<Class> = Gson().fromJson(data, typeToken.type)

            Text(
                modifier = Modifier.align(Alignment.Start).padding(16.dp).fillMaxWidth(0.5f)
                    .padding(16.dp, 8.dp, 16.dp, 0.dp),
                text = "Class List",
                fontFamily = poppinsFont,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )

            var selectedClass by remember {
                mutableStateOf(
                    classList.get(0)
                )
            }

            Row(Modifier.fillMaxSize()) {
                Column(
                    Modifier.fillMaxHeight().fillMaxWidth(0.3f).padding(16.dp)
                        .clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colors.background)
                ) {
                    LazyColumn {
                        items(classList) {
                            Column(modifier = Modifier.fillMaxWidth()
                                .padding(8.dp, 8.dp, 8.dp, 0.dp).clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFB5B7C0)).clickable {
                                    selectedClass = it
                                }) {
                                Text(
                                    "${it.standard}${it.division}",
                                    modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp),
//                                        color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.body1,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 2.sp
                                )

                                Text(
                                    text = it.teacherEmail,
                                    modifier = Modifier.padding(all = 8.dp),
                                    letterSpacing = 4.sp,
//                                        color = MaterialTheme.colors.,
                                    style = MaterialTheme.typography.caption,
                                )
                            }
                        }
                    }
                }

                Column(
                    Modifier.fillMaxHeight().fillMaxWidth().padding(16.dp)
                        .clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colors.background)
                ) {

                    if (selectedClass.studentList == null || selectedClass.studentList!!.size == 0) {

                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "${selectedClass.standard} ${selectedClass.division}",
                            fontFamily = poppinsFont,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.weight(1f))

                        TextButton(modifier = Modifier.padding(32.dp)
                            .align(Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(12.dp)).background(Color(0xFF292D32))
                            .padding(8.dp), onClick = {

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

    private @Composable
    fun ClassConfig() {
        Column(
            Modifier.fillMaxSize().padding(32.dp, 16.dp, 32.dp, 0.dp)
                .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)).background(Color(0xFFB5B7C0))
        ) {

            Text(
                modifier = Modifier.align(Alignment.Start).padding(16.dp).fillMaxWidth(0.5f)
                    .padding(16.dp, 8.dp, 16.dp, 0.dp),
                text = "Class List",
                fontFamily = poppinsFont,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )

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

                var classCount by remember { mutableStateOf("5") }
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

                var selectedItems = ArrayList<String>()

                Row {

                    Spacer(Modifier.weight(1f))

                    Column {

                        var row = 1
                        var column = 0

                        while (row < classCount.toInt() + 1) {

                            Row {

                                while (column < divisionCount.toInt()) {

                                    selectedItems.add("$row${(column + 65).toChar()}")

                                    ClassChip(s = "$row${(column + 65).toChar()}",
                                        onSelectionChanged = fun(s, isChecked) {
                                            if (isChecked) selectedItems.add(s)
                                            else selectedItems.remove(s)
                                        })
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

                    TextButton(modifier = Modifier.padding(32.dp).clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF292D32)).padding(8.dp), onClick = {

                        GlobalScope.launch {
                            val classList = ArrayList<Class>()

                            val instituteID =
                                Settings().getString(Constants.KEY_INSTITUTE_ID, "null")

                            for (s in selectedItems) {
                                classList.add(
                                    Class(
                                        s[0].digitToInt(),
                                        s[1].toString(),
                                        "$s@$instituteID.com",
                                        Utils.generatePassword()
                                    )
                                )
                            }

                            val institutionsList = firebaseAPI.getInstitutionsList()

                            for (institution in institutionsList) {
                                if (instituteID == institution.instituteID) institution.classList =
                                    classList
                            }

                            Settings().putString(
                                Constants.KEY_CLASS_LIST, Gson().toJson(classList)
                            )

                            firebaseAPI.setInstitutionsList(
                                institutionsList
                            )
                        }

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

    @OptIn(ExperimentalMaterialApi::class)
    private
    @Composable
    fun ClassChip(s: String, onSelectionChanged: (s: String, isChecked: Boolean) -> Unit) {

        var isChecked by remember { mutableStateOf(true) }
        val text = s

        FilterChip(
            modifier = Modifier.padding(4.dp),
            onClick = {
                isChecked = !isChecked
                onSelectionChanged(text, isChecked)
            },
            selected = isChecked,
            content = {
                if (isChecked) Icon(
                    modifier = Modifier.clip(CircleShape).align(Alignment.CenterVertically)
                        .padding(2.dp),
                    painter = painterResource("check.png"),
                    contentDescription = "check"
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically), text = s
                )
            },
        )
    }

    companion object {
        @Serial
        private const val serialVersionUID: Long = -6432837658175213004L
    }
}
