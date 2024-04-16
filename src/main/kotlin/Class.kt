data class Class(
    val standard: Int,
    val division: String,
    val teacherEmail: String,
    val teacherPassword: String,
    val studentList: ArrayList<Student>? = ArrayList()
)