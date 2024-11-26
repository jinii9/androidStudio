package com.seojin.memoapp3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayList: ArrayList<String> = ArrayList<String>() // 메모 데이터를 저장하는 리스트
        val editText: EditText = findViewById(R.id.editText) // 사용자 입력 필드
        val listView: ListView = findViewById(R.id.listView) // 메모 목록을 보여주는 뷰
        val button: Button = findViewById(R.id.button) // 메모 추가 버튼


        // 데이터 관리하는 어댑터 (리스트 출력하는 어댑터)
        // context : 사용자가 지금 뭘 하고 있는지
        // 어떤 레이아웃으로 셋팅을 해줄 거냐 - simple_list_item_1은 텍스트 하나 해주는 형태이다.
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, // 현재 액티비티 컨텍스트
            android.R.layout.simple_list_item_1, // 안드로이드 기본 제공 레이아웃
            arrayList // 데이터 소스
        )
        listView.adapter = arrayAdapter         // 리스트뷰에 어댑터 연결


        arrayList.add("빨래 하기")
        arrayList.add("빨래 하기")
        arrayList.add("빨래 하기")



        button.setOnClickListener {
            arrayList.add(editText.text.toString())
            // 데이터가 추가되는걸 어댑터가 알아야하기 때문에 넣어주고, 리로더하게
            arrayAdapter.notifyDataSetChanged() // 데이터 변경을 어댑터에 알림
        }

    }
}