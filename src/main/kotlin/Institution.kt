data class Institution(
    val instituteID: String,
    var classList: ArrayList<Class>? = ArrayList(),
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Institution

        if (instituteID != other.instituteID) return false

        return true
    }
}