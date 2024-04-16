object SampleData {
    val list: List<Institution>
        get() {
            val studentList: ArrayList<Student> = ArrayList()
            studentList.add(Student(63, "Akash Chandran", "8921260217"))
            studentList.add(Student(28, "Hafis Mohamed C P", "9447647932"))
            studentList.add(Student(54, "Shreya Suresh", "9995122747"))
            studentList.add(Student(60, "Vishnu Sanal T", "7558913593"))

            val classList: ArrayList<Class> = ArrayList()
            classList.add(Class(4, "A", "4A@G21010.com", "gYBGWET", studentList))
            classList.add(Class(5, "B", "5B@G21010.com", "cmeWNex", studentList))

            val institutions: ArrayList<Institution> = ArrayList()
            institutions.add(Institution("KL21010", "uLW8a97", classList))
            institutions.add(Institution("KL21010", "VZCVcbA", classList))

            return institutions
        }
}

// println(Gson().toJson(SampleData.list))