package com.example.studentmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class StudentAdapter(
    private val students: MutableList<StudentModel>,
    private val onDelete: (StudentModel) -> Unit
) : BaseAdapter() {
    override fun getCount() = students.size

    override fun getItem(p0: Int) = students[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getView(
        p0: Int,
        p1: View?,
        p2: ViewGroup?,
    ): View {
        val itemView: View
        val viewHolder: ViewHolder

        if (p1 == null) {
            itemView = LayoutInflater.from(p2?.context).inflate(R.layout.layout_student_item, p2, false)
            viewHolder = ViewHolder().apply {
                textHoten = itemView.findViewById(R.id.text_hoten)
                textMssv = itemView.findViewById(R.id.text_mssv)
                deleteButton = itemView.findViewById(R.id.deleteStudent)
                itemView.tag = this
            }
            itemView.tag = viewHolder
        } else {
            itemView = p1
            viewHolder = p1.tag as ViewHolder
        }

        val student = students[p0]
        viewHolder.textHoten.text = student.name
        viewHolder.textMssv.text = student.mssv

        viewHolder.deleteButton.setOnClickListener {
            onDelete(student)
        }

        return itemView
    }

    fun removeStudent(student: StudentModel) {
        students.remove(student)
        notifyDataSetChanged()
    }

    fun addStudent(student: StudentModel) {
        students.add(student)
        notifyDataSetChanged()
    }

    class ViewHolder {
        lateinit var textHoten: TextView
        lateinit var textMssv: TextView
        lateinit var deleteButton: Button
    }
}
