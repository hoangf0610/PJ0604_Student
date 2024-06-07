package com.example.pj0604_student.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pj0604_student.R
import com.example.pj0604_student.fragment.DetailFragment
import com.example.pj0604_student.model.Student
import com.example.pj0604_student.model.Subject

class SubjectAdapter(var subjectList: MutableList<Subject>, var listener: SubjectAdapter.OnItemClicked): RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>(){
    inner class SubjectViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val subjectName : EditText = itemView.findViewById(R.id.edt_subject_name)
        val subjectPoint : EditText = itemView.findViewById(R.id.edt_subject_point)
        val tvDelete : TextView = itemView.findViewById(R.id.tv_delete)
    }

    val subjectData = mutableListOf<Subject>()

    private val detailFragment = DetailFragment()
//    private val allFragment = AllFragment()
    var gpa: Double = 0.0

    interface OnItemClicked {
        fun onItemClicked(subjectList: MutableList<Subject>, position: Int, gpa: Double)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subject_item,parent,false)
        return SubjectViewHolder(view)
    }

    override fun getItemCount(): Int = subjectList.size

    override fun onBindViewHolder(holder: SubjectViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.setIsRecyclable(false) // ngừng việc recycler lại position
//        Log.i("subject", "position $position : ${subjectList[position].pointSubject}")
        val subject = subjectList[position]
        holder.subjectName.setText(subject.nameSubject)
        holder.subjectPoint.setText(subject.pointSubject.toString())

        holder.tvDelete.setOnClickListener{
            gpa = chargeGPA()
            subjectList.removeAt(position)
            listener.onItemClicked(subjectList = subjectList, position, gpa)
            notifyDataSetChanged()
        }

        holder.subjectPoint.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                gpa = chargeGPA()
                try {
                    subjectList[holder.adapterPosition].pointSubject = holder.subjectPoint.text.toString().toDouble()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Log.i("gpa value : ", gpa.toString())
                listener.onItemClicked(subjectList = subjectList, position, gpa)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
//
//    override fun getItemId(position: Int): Long {
//        return subjectList[position].index.toLong()
//    }


    fun chargeGPA ():Double {
        val sum = subjectList.sumOf { it.pointSubject }
        gpa = if(subjectList.isNotEmpty()) sum.toDouble()/ subjectList.size else 0.0
        return gpa
    }
}