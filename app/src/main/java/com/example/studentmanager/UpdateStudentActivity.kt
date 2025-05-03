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

class UpdateStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Student Manager"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        
        val oldStudent = intent.getParcelableExtra<StudentModel>("student")
        
        val updateName = findViewById<EditText>(R.id.updateName)
        val updateId = findViewById<EditText>(R.id.updateId)
        val updateEmail = findViewById<EditText>(R.id.updateEmail)
        val updatePhone = findViewById<EditText>(R.id.updatePhone)
        val updateButton = findViewById<Button>(R.id.updateButton)

        if (oldStudent != null) {
            updateName.setText(oldStudent.name)
            updateId.setText(oldStudent.mssv)
            updateEmail.setText(oldStudent.email)
            updatePhone.setText(oldStudent.phone)
        }

        updateButton.setOnClickListener {
            val name = updateName.text.toString()
            val mssv = updateId.text.toString()
            val email = updateEmail.text.toString()
            val phone = updatePhone.text.toString()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                val newStudent = StudentModel(name, mssv, email, phone)

                val resultIntent = Intent().apply {
                    putExtra("newStudent", newStudent)
                    putExtra("oldStudent", oldStudent)
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
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}