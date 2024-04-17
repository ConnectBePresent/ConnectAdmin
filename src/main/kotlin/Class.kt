data class Class(
    val standard: Int,
    val division: String,
    val teacherEmail: String,
    val teacherPassword: String,
    var studentList: ArrayList<Student>? = ArrayList()
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Class

        if (standard != other.standard) return false
        if (division != other.division) return false
        if (teacherEmail != other.teacherEmail) return false
        if (teacherPassword != other.teacherPassword) return false

        return true
    }
}