package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddStudentActivity : AppCompatActivity() {
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Student Manager"
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val editName = findViewById<EditText>(R.id.editName)
        val editId = findViewById<EditText>(R.id.editId)
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPhone = findViewById<EditText>(R.id.editPhone)
        val addButton = findViewById<Button>(R.id.add)

        addButton.setOnClickListener {
            val name = editName.text.toString()
            val mssv = editId.text.toString()
            val email = editEmail.text.toString()
            val phone = editPhone.text.toString()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                val newStudent = StudentModel(name, mssv, email, phone)

                val resultIntent = Intent().apply {
                    putExtra("newStudent", newStudent)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        menu?.findItem(R.id.addStudent)?.isVisible = false

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}