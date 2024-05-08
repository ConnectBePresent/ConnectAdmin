import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Utils {
    companion object {
        fun generatePassword(): String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

            return List(6) { allowedChars.random() }.joinToString("")
        }

        fun getDate(millis: Long): String {
            return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(millis))
        }

        fun getToday(): String {
            return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(Calendar.getInstance().time)
        }

        fun getLastWeek(): ArrayList<String> {

            val list = ArrayList<String>()

            val calendar = Calendar.getInstance()
            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            for (i in 0..6) {
                list.add(simpleDateFormat.format(calendar.time))
                calendar.add(Calendar.DAY_OF_YEAR, -1)
            }

            return list
        }

        @OptIn(DelicateCoroutinesApi::class)
        fun generatePDF() {
            GlobalScope.launch { PDFUtils.generate() }
        }
    }
}

/*fun generatePDF() {

            var pageHeight = 1080
            var pageWidth = 1920

            lateinit var bmp: Bitmap
            lateinit var scaledbmp: Bitmap

            var pdfDocument: PdfDocument = PdfDocument()

            var paint: Paint = Paint()
            var title: Paint = Paint()

            bmp = BitmapFactory.decodeResource(context.resources, R.drawable.android)
            scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false)

            var myPageInfo: PdfDocument.PageInfo? =
                PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

            var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

            var canvas: Canvas = myPage.canvas
            canvas.drawBitmap(scaledbmp, 56F, 40F, paint)

            title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

            title.textSize = 15F

            title.setColor(ContextCompat.getColor(context, R.color.purple_200))

            canvas.drawText("A portal for IT professionals.", 209F, 100F, title)
            canvas.drawText("Geeks for Geeks", 209F, 80F, title)
            title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            title.setColor(ContextCompat.getColor(context, R.color.purple_200))
            title.textSize = 15F

            title.textAlign = Paint.Align.CENTER
            canvas.drawText("This is sample document which we have created.", 396F, 560F, title)

            pdfDocument.finishPage(myPage)

            val file: File = File(Environment.getExternalStorageDirectory(), "GFG.pdf")

            try {
                pdfDocument.writeTo(FileOutputStream(file))

                Toast.makeText(context, "PDF file generated..", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()

                Toast.makeText(context, "Fail to generate PDF file..", Toast.LENGTH_SHORT).show()
            }
            pdfDocument.close()
        }*/