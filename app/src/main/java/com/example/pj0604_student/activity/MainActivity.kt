package com.example.pj0604_student.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pj0604_student.Contains
import com.example.pj0604_student.R
import com.example.pj0604_student.databinding.ActivityMainBinding
import com.example.pj0604_student.fragment.AllFragment
import com.example.pj0604_student.fragment.DetailFragment
import com.example.pj0604_student.model.Student

private lateinit var binding : ActivityMainBinding
class MainActivity : AppCompatActivity(), DetailFragment.OnCreateStudent {

    private val fragmentAll = AllFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragmentAll)
            commit()
        }
    }

    fun goToAddNewStudent() {
        val fragmentDetail = supportFragmentManager.beginTransaction()
        val detailFragment = DetailFragment()
        fragmentDetail.addToBackStack(DetailFragment.TAG) // chuyển data
        fragmentDetail.replace(R.id.flFragment,detailFragment) // add : thêm detailFragment vào stack
        fragmentDetail.commit()
    }


    @SuppressLint("CommitTransaction")
    fun gotoUpdateStudent(student: Student) {
        val fragmentDetail = supportFragmentManager.beginTransaction()
        val detailFragment = DetailFragment()

        val bundle = Bundle().apply {
            putInt("index", student.index)
            putInt("img", student.img)
            putString("name", student.name)
            putInt("age", student.age)
            putDouble("point", student.point)
            putString("gender", student.gender)
            putInt("subject", student.subject)
//            putParcelable("student", student)
        }

        detailFragment.arguments = bundle

        fragmentDetail.addToBackStack(DetailFragment.TAG) // chuyển data
        fragmentDetail.replace(R.id.flFragment,detailFragment) // add : thêm detailFragment vào stack
        fragmentDetail.commit()
    }

    override fun onCreateStudent(student: Student) {
        Contains.getStudentList().add(student)
    }


}