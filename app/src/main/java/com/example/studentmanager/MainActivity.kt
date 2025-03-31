package com.example.studentmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val students = mutableListOf<StudentModel>()

        adapter = StudentAdapter(students) { student ->
            adapter.removeStudent(student)
        }

        val listStudents = findViewById<ListView>(R.id.list_students)
        listStudents.adapter = adapter

        val editName = findViewById<EditText>(R.id.editName)
        val editId = findViewById<EditText>(R.id.editId)
        val addButton = findViewById<Button>(R.id.add)

        addButton.setOnClickListener {
            val name = editName.text.toString()
            val mssv = editId.text.toString()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                val newStudent = StudentModel(name, mssv)
                adapter.addStudent(newStudent)

                editName.text.clear()
                editId.text.clear()
            }
        }
    }
}