package com.example.studentmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val onDelete: (student: StudentModel) -> Unit,
    private val onUpdate: (oldStudent: StudentModel) -> Unit,
    private val onCallingPhone: (student: StudentModel) -> Unit,
    private val onSendingEmail: (student: StudentModel) -> Unit,
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private val students = mutableListOf<StudentModel>()

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textHoten: TextView = itemView.findViewById(R.id.text_hoten)
        val textMssv: TextView = itemView.findViewById(R.id.text_mssv)
        private val menuIcon: ImageView = itemView.findViewById(R.id.menu_icon)

        init {
            menuIcon.setOnClickListener { v ->
                showPopupMenu(v, adapterPosition)
            }
        }

        private fun showPopupMenu(anchor: View, position: Int) {
            PopupMenu(anchor.context, anchor).apply {
                inflate(R.menu.popup_menu)
                setOnMenuItemClickListener { menuItem ->
                    val student: StudentModel = students[position]
                    when (menuItem.itemId) {
                        R.id.deleteStudent -> {
                            onDelete(student)

                            true
                        }
                        R.id.toUpdateStudentForm -> {
                            onUpdate(student)
                            true
                        }
                        R.id.callPhone -> {
                            onCallingPhone(student)
                            true
                        }
                        R.id.sendEmail -> {
                            onSendingEmail(student)
                            true
                        }
                        else -> false
                    }
                }
                show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_student_item, parent, false)

        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.textHoten.text = student.name
        holder.textMssv.text = student.mssv
    }

    override fun getItemCount(): Int = students.size

    fun addStudent(student: StudentModel) {
        students.add(student)
        notifyItemInserted(students.size - 1)
    }

    fun updateStudent(oldStudent: StudentModel, newStudent: StudentModel) {
        val index = students.indexOf(oldStudent)
        if (index != -1) {
            students[index] = newStudent
            notifyItemChanged(index)
        }
    }

    fun removeStudent(student: StudentModel) {
        val index = students.indexOf(student)
        if (index != -1) {
            students.removeAt(index)
            notifyItemRemoved(index)
        }
    }

}
