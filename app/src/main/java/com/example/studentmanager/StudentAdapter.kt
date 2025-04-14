package com.example.studentmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: MutableList<StudentModel>,
    private val onDelete: (StudentModel) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textHoten: TextView = itemView.findViewById(R.id.text_hoten)
        val textMssv: TextView = itemView.findViewById(R.id.text_mssv)
        val deleteButton: Button = itemView.findViewById(R.id.deleteStudent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.textHoten.text = student.name
        holder.textMssv.text = student.mssv
        holder.deleteButton.setOnClickListener { onDelete(student) }
    }

    override fun getItemCount(): Int = students.size

    fun addStudent(student: StudentModel) {
        students.add(student)
        notifyItemInserted(students.size - 1)
    }

    fun removeStudent(student: StudentModel) {
        val index = students.indexOf(student)
        if (index != -1) {
            students.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
