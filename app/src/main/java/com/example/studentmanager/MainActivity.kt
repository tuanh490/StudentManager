package com.example.studentmanager

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: StudentAdapter

    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { intent ->
        if (intent.resultCode == RESULT_OK) {
            val student = intent.data?.getParcelableExtra<StudentModel>("newStudent")
            if (student != null) {
                adapter.addStudent(student)
            }
        }
    }

    private val updateStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { intent ->
        if (intent.resultCode == RESULT_OK) {
            val oldStudent = intent.data?.getParcelableExtra<StudentModel>("oldStudent")
            val newStudent = intent.data?.getParcelableExtra<StudentModel>("newStudent")
            if (oldStudent != null && newStudent != null) {
                adapter.updateStudent(oldStudent, newStudent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Student Manager"
        supportActionBar?.setDisplayShowHomeEnabled(true)

        adapter = StudentAdapter({student: StudentModel ->
            AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa sinh viên")
                .setPositiveButton("Xác nhận") {_, _ ->
                    adapter.removeStudent(student)
                }
                .setNegativeButton("Hủy", null)
                .create()
                .show()
        }, { oldStudent ->
            val intent = Intent(this, UpdateStudentActivity::class.java)
            intent.putExtra("student", oldStudent)
            updateStudentLauncher.launch(intent)
        }, { student ->
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${student.phone}")
            }
            startActivity(intent)
        }, { student ->
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${student.email}")
            }
            startActivity(intent)
        })

        val listStudents = findViewById<RecyclerView>(R.id.list_students)
        listStudents.layoutManager = LinearLayoutManager(this)
        listStudents.adapter = adapter

        registerForContextMenu(listStudents)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        menu?.findItem(R.id.home)?.isVisible = false

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addStudent -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
