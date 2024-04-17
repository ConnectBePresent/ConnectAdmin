data class Student(
    val rollNumber: Int,
    val name: String,
    val parentPhoneNumber: String,
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Student

        if (rollNumber != other.rollNumber) return false
        if (name != other.name) return false
        if (parentPhoneNumber != other.parentPhoneNumber) return false

        return true
    }
}