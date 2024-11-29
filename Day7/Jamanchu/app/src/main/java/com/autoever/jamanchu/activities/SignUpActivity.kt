package com.autoever.jamanchu.activities

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.autoever.jamanchu.R
import com.autoever.jamanchu.models.Gender
import com.autoever.jamanchu.models.User

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 컴포넌트 선언
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextNickname = findViewById<EditText>(R.id.editTextNickname)
        val editTextIntroduction = findViewById<EditText>(R.id.editTextIntroduction)
        val textViewComplete = findViewById<TextView>(R.id.textViewComplete)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val spinner: Spinner = findViewById(R.id.spinner)

        // 문자열 배열 어댑터 생성
        ArrayAdapter.createFromResource(
            this,
            R.array.age_array, // res > value에 설정
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // 드롭다운 레이아웃 설정
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // 어댑터 설정
            spinner.adapter = adapter
        }

        // "가입하기 버튼"
        textViewComplete.setOnClickListener {
            // 선택된 라디오 버튼 ID 가져오기
            val selectedGenderId = radioGroup.checkedRadioButtonId
            // 선택된 라디오 버튼 가져오기
            val selectedRadioButton = findViewById<RadioButton>(selectedGenderId)
            // 라디오 버튼의 텍스트 값으로 Gender를 결정
            val gender = when (selectedRadioButton.text.toString()) {
                "남성" -> Gender.MALE
                "여성" -> Gender.FEMALE
                else -> throw IllegalArgumentException("올바른 성별을 선택하세요.")
            }

            // Spinner에서 선택된 나이를 가져오기 (선택된 항목의 문자열을 Int로 변환)
            val selectedAge = spinner.selectedItem.toString().toInt()

            // 유저 객체 만들어주기 -> firebase에 넣어주기
            val user = User(
                "",
                editTextEmail.text.toString(),
                editTextNickname.text.toString(),
                editTextIntroduction.text.toString(),
                gender,
                selectedAge
            )

            Log.d("User", user.toString())
        }
    }
}