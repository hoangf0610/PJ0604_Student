package com.example.pj0604_student

import com.example.pj0604_student.model.Student
import com.example.pj0604_student.model.Subject

object Contains {
    //    fun studentList(): MutableList<Student> {
//        return mutableListOf(
//            Student(1, R.drawable.img_a,"Nguyen Van A", 30, 7.5 , "female"),
//            Student(2, R.drawable.img_c,"Nguyen Van C", 16, 8.5, "female" ),
//            Student(3, R.drawable.img_b,"Nguyen Van B", 19, 9.5, "male" ),
//            )
//    }
    private val studentList = mutableListOf(
        Student(1, R.drawable.img_a, "Nguyen Van A", 30, 7.5, "female"),
        Student(2, R.drawable.img_c, "Nguyen Van C", 16, 8.5, "female"),
        Student(3, R.drawable.img_b, "Nguyen Van B", 19, 9.5, "male"),
    )

    fun subjectList(): MutableList<Subject> {
        return mutableListOf(
//            Subject(1, R.string.subject_maths, 9.2),
//            Subject(2, R.string.subject_literature, 8.2),
//            Subject(3, R.string.subject_history, 7.2),
        )
    }

    fun getStudentList(): MutableList<Student> = studentList

}