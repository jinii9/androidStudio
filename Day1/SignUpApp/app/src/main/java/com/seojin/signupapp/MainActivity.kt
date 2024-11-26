package com.seojin.signupapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val buttonBirth = findViewById<Button>(R.id.buttonBirth)
        val editTextFight = findViewById<EditText>(R.id.editTextFight)
        val buttonBook = findViewById<Button>(R.id.buttonBook)

        // 스피너 목록
        val genderList = listOf("남자", "여자")

    }
}