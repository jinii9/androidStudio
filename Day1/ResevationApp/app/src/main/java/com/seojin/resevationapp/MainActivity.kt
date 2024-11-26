package com.seojin.resevationapp

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var location: String = ""
    var date: String = ""
    var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View 초기화
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val buttonDate = findViewById<Button>(R.id.buttonDate)
        val buttonBook = findViewById<Button>(R.id.buttonBook)

        // 스피너에 나타날 지점 목록
        val locationList = listOf(
            "서울", "양양", "대전", "부산", "광주"
        )

        // 스피너 어댑터 설정
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // 선택 이벤트 처리
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                location = p0!!.getItemAtPosition(p2).toString()
                Toast.makeText(applicationContext, location, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        // 날짜 선택
        buttonDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // DatePickerDialog 생성
            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                // 선택된 날짜를 버튼에 표시
                date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                buttonDate.text = date
            }, year, month, day)

            // 다이얼로그 표시
            datePickerDialog.show()
        }

        // 버튼 클릭
        buttonBook.setOnClickListener {
            showDefaultDialog(editTextName.text.toString(), location, date)
        }
    }

    // 사용자가 볼 수 있도록
    private fun showDefaultDialog(name: String, location: String, date: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("클럽 예약 정보")
        builder.setMessage("${name}님은 \n ${date}에 ${location} \n 장소를 방문할 예정입니다.")

        // 버튼 셋팅
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}