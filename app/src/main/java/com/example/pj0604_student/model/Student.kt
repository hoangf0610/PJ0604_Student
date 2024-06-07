package com.example.pj0604_student.model

import android.os.Parcel
import android.os.Parcelable

class Student(
    var index: Int,
    var img: Int,
    var name: String,
    var age: Int,
    var point: Double,
    var gender: String = "gender",
    var subject: Int = 0,
    var subjectArray: Array<Subject> = emptyArray()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readInt(),
        TODO("subjectArray")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
        parcel.writeInt(img)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeDouble(point)
        parcel.writeString(gender)
        parcel.writeInt(subject)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }

}