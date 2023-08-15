package com.example.test1

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seacrh)

        // 검색 EditText 참조 및 검색 기능 구현
        val searchEditText = findViewById<EditText>(R.id.searchEditText)

        // TODO: 여기에 검색 기능과 검색 결과를 표시하는 로직을 추가합니다.
    }
}