data class Institution(
    val instituteID: String,
    val institutePassword: String,
    var classList: ArrayList<Class>? = ArrayList(),
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Institution

        if (instituteID != other.instituteID) return false
        if (institutePassword != other.institutePassword) return false

        return true
    }
}

// INFO: {"0":{"classList":[{"division":"A","standard":4,"studentList":[{"name":"Akash Chandran","parentPhoneNumber":"8921260217","rollNumber":63},{"name":"Hafis Mohamed C P","parentPhoneNumber":"9447647932","rollNumber":28},{"name":"Shreya Suresh","parentPhoneNumber":"9995122747","rollNumber":54},{"name":"Vishnu Sanal T","parentPhoneNumber":"7558913593","rollNumber":60}],"teacherEmail":"4A@G21010.com","teacherPassword":"gYBGWET"},{"division":"B","standard":5,"studentList":[{"name":"Akash Chandran","parentPhoneNumber":"8921260217","rollNumber":63},{"name":"Hafis Mohamed C P","parentPhoneNumber":"9447647932","rollNumber":28},{"name":"Shreya Suresh","parentPhoneNumber":"9995122747","rollNumber":54},{"name":"Vishnu Sanal T","parentPhoneNumber":"7558913593","rollNumber":60}],"teacherEmail":"5B@G21010.com","teacherPassword":"cmeWNex"}],"instituteID":"KL21010","institutePassword":"uLW8a97"},"1":{"classList":[{"division":"A","standard":4,"studentList":[{"name":"Akash Chandran","parentPhoneNumber":"8921260217","rollNumber":63},{"name":"Hafis Mohamed C P","parentPhoneNumber":"9447647932","rollNumber":28},{"name":"Shreya Suresh","parentPhoneNumber":"9995122747","rollNumber":54},{"name":"Vishnu Sanal T","parentPhoneNumber":"7558913593","rollNumber":60}],"teacherEmail":"4A@G21010.com","teacherPassword":"gYBGWET"},{"division":"B","standard":5,"studentList":[{"name":"Akash Chandran","parentPhoneNumber":"8921260217","rollNumber":63},{"name":"Hafis Mohamed C P","parentPhoneNumber":"9447647932","rollNumber":28},{"name":"Shreya Suresh","parentPhoneNumber":"9995122747","rollNumber":54},{"name":"Vishnu Sanal T","parentPhoneNumber":"7558913593","rollNumber":60}],"teacherEmail":"5B@G21010.com","teacherPassword":"cmeWNex"}],"instituteID":"KL21011","institutePassword":"VZCVcbA"},"-Nvb_IEqSX0hJeOYQO87":{"instituteID":"KL21012","institutePassword":"password"},"-Nvb_Sa7lJ3CedRSA4Ni":{"instituteID":"KL21013","institutePassword":"Xnu76e"}}