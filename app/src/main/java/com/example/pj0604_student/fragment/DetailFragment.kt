package com.example.pj0604_student.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pj0604_student.Contains
import com.example.pj0604_student.R
import com.example.pj0604_student.activity.MainActivity
import com.example.pj0604_student.adapter.StudentAdapter
import com.example.pj0604_student.adapter.SubjectAdapter
import com.example.pj0604_student.databinding.FragmentDetailBinding
import com.example.pj0604_student.model.Student
import com.example.pj0604_student.model.Subject

@Suppress("DEPRECATION", "NAME_SHADOWING")
class DetailFragment : Fragment(R.layout.fragment_detail){
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "DetailFragment"
    }

    interface OnCreateStudent {
        fun onCreateStudent(student: Student)
    }

    lateinit var studentList: MutableList<Student>
    lateinit var subjectList: MutableList<Subject>
    lateinit var studentAdapter: StudentAdapter
    lateinit var subjectAdapter: SubjectAdapter
    private var listener: OnCreateStudent? = null


    private lateinit var studentSelected: Student
    val genders = arrayOf("Male", "Female")

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try {
            binding.imgStudent.setImageURI(galleryUri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCreateStudent) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnCreateStudent")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        studentList = Contains.getStudentList()
        subjectList = Contains.subjectList()

        var index = arguments?.getInt("index")  ?: -1
        var img = arguments?.getInt("img") ?: 0
        var name = arguments?.getString("name") ?: ""
        var age = arguments?.getInt("age") ?: 0
        var point = arguments?.getDouble("point") ?: 0.0
        var gender = arguments?.getString("gender") ?: "gender"
        var subject = arguments?.getInt("subject") ?: 0

//        val studentArgument: Student? = arguments?.getParcelable("student")

//        if (studentArgument != null) {
//            studentSelected = Student(
//                studentArgument.index,
//                studentArgument.img,
//                studentArgument.name,
//                studentArgument.age,
//                studentArgument.point,
//                studentArgument.gender,
//                studentArgument.subject
//            )
//        }
        studentSelected = Student(
            index,
            img,
            name,
            age,
            point,
            gender,
            subject
        )

        binding.imgStudent.setImageResource(studentSelected.img)
        binding.edtName.setText(studentSelected.name)
        binding.edtAge.setText(studentSelected.age.toString())


        binding.tvPoint.setText(studentSelected.point.toString())

        val arrayAdapter = context?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                genders
            )
        }

        binding.spGender.adapter = arrayAdapter
        binding.spGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                subjectAdapter.notifyDataSetChanged()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.imgStudent.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
        studentAdapter = StudentAdapter(studentList, object : StudentAdapter.OnItemClicked {
            override fun onItemClicked(student: Student, position: Int) {
                AllFragment.indexSelected = position
                (activity as MainActivity).gotoUpdateStudent(student)
            }

        })
        subjectAdapter = SubjectAdapter(subjectList, object : SubjectAdapter.OnItemClicked {
            override fun onItemClicked(
                subjectList: MutableList<Subject>,
                position: Int,
                gpa: Double
            ) {
                binding.tvPoint.setText(String.format("%.2f",gpa))
            }

        })


        binding.rcvSubject.adapter = subjectAdapter
        binding.rcvSubject.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )


        binding.tvAddSubject.setOnClickListener {
            val nameSubject: String = ""
            val pointSubject: Double = 0.0
            val subject = Subject(subjectList.size, nameSubject, pointSubject)
            subjectList.add(subject)
            subjectAdapter.notifyDataSetChanged()
        }

        binding.btnCreate.setOnClickListener{
            if(binding.edtAge.text.toString().toInt() < 1 && binding.edtAge.text.toString().toInt() > 200) {
                Toast.makeText(requireContext(), "Invalid Age", Toast.LENGTH_SHORT).show()
            }
            else {
                val name = binding.edtName.text.toString()
                val age = binding.edtAge.text.toString().toIntOrNull() ?: 0
                val point = binding.tvPoint.text.toString().toDoubleOrNull() ?: 0.0
                val img = studentSelected.img
                val gender = binding.spGender.selectedItem.toString()
                val subject = studentSelected.subject

                val newStudent = Student(studentList.size, img, name, age, point, gender, subject)
                listener?.onCreateStudent(newStudent)
                parentFragmentManager.popBackStack()
            }
        }

        binding.btnDelete.setOnClickListener{
            if(AllFragment.indexSelected!=-1) studentList.removeAt(AllFragment.indexSelected)
            studentAdapter.notifyDataSetChanged()
            parentFragmentManager.popBackStack()
        }

        return view
    }


}