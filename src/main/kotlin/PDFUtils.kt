@file:OptIn(DelicateCoroutinesApi::class)

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lowagie.text.Chunk
import com.lowagie.text.Document
import com.lowagie.text.DocumentException
import com.lowagie.text.Element
import com.lowagie.text.Font
import com.lowagie.text.FontFactory
import com.lowagie.text.PageSize
import com.lowagie.text.Paragraph
import com.lowagie.text.pdf.PdfWriter
import com.russhwolf.settings.Settings
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.io.IOException


object PDFUtils {

    suspend fun generate() {

        val settings = Settings()

        val instituteID = settings.getString(
            Constants.KEY_INSTITUTE_ID, "null"
        )

        val typeToken = object : TypeToken<ArrayList<Class>>() {}
        val classList: ArrayList<Class> = Gson().fromJson(
            settings.getString(Constants.KEY_CLASS_LIST, "null"), typeToken.type
        )

        var document = Document(PageSize.A4)

        try {

            withContext(Dispatchers.IO) {
                PdfWriter.getInstance(document, FileOutputStream("ConnectReport.pdf"))
            };

            document.open()

            var title = Paragraph(
                "Attendance Report\n" + instituteID.uppercase() + " (${Utils.getToday()})",
                Font(Font.COURIER, 20f, Font.BOLD)
            )

            title.setAlignment(Element.ALIGN_CENTER)

            document.add(title)

            classList.sortedBy { it.standard * 10 + it.division[0].code }.forEach { standard ->

                var p: Paragraph = Paragraph(
                    Chunk(
                        "\n${standard.standard}${standard.division}",
                        FontFactory.getFont(FontFactory.COURIER, 20f)
                    )
                )

                document.add(p)

                if (standard.studentList == null) document.add(
                    Paragraph(
                        Chunk(
                            "\n\t\tClass Empty!", FontFactory.getFont(FontFactory.COURIER, 14f)
                        )
                    )
                ) else {

                    var studentList: Paragraph = Paragraph(
                        Chunk(
                            "\n\tClass List\n", FontFactory.getFont(FontFactory.COURIER, 18f)
                        )
                    )

                    standard.studentList?.sortedBy { it.rollNumber }?.forEach { student ->
                        studentList.add(
                            Paragraph(
                                Chunk(
                                    "\t\t\t${student.rollNumber}: ${student.name} (${student.parentPhoneNumber})",
                                    FontFactory.getFont(FontFactory.COURIER, 14f)
                                )
                            )
                        )
                    }

                    document.add(studentList)

                    val response = firebaseDatabaseAPI.getAttendance(
                        standard.teacherEmail.lowercase().replace(".com", ""),
                        //                        "01-05-2024"
                        Utils.getToday()
                    )

                    var absenteeList: Paragraph = Paragraph(
                        Chunk(
                            "\n\tToday's Absentees", FontFactory.getFont(FontFactory.COURIER, 18f)
                        )
                    )

                    if (response.isSuccessful && response.body() != null) {

                        response.body()!!.toMutableList().sortedBy { it.rollNumber }
                            .forEach { student ->
                                absenteeList.add(
                                    Chunk(
                                        "\n\t\t\t${student.rollNumber}: ${student.name} (${student.parentPhoneNumber})",
                                        FontFactory.getFont(FontFactory.COURIER, 14f)
                                    )
                                )
                            }
                    } else {
                        absenteeList.add(
                            Chunk(
                                "\n\t\t\tAttendance not updated for the day!",
                                FontFactory.getFont(FontFactory.COURIER, 14f)
                            )
                        )
                    }

                    document.add(absenteeList)

                }

            }
        } catch (de: DocumentException) {
            System.err.println(de.message)
        } catch (de: IOException) {
            System.err.println(de.message)
        }

        document.close()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        GlobalScope.launch { generate() }
    }
}