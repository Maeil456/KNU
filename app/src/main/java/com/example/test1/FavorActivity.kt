package com.example.test1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FavorActivity : AppCompatActivity() {


    companion object {
        const val ACTION_RECENTLY_BUTTON = "com.example.test1.ACTION_RECENTLY_BUTTON"
    }

    private fun performOptionAction(option: String) {
        when (option) {
            "배달의 민족" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 5)
                intent.putExtra("app","main")
                sendBroadcast(intent)
            }
            "유튜브" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 6)
                intent.putExtra("app","main")
                sendBroadcast(intent)
            }
            "다음" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 7)
                intent.putExtra("app","main")
                sendBroadcast(intent)
            }
            "더보기메인" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 8)
                intent.putExtra("app","main")
                sendBroadcast(intent)
            }
            "문자 보내기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 1)
                intent.putExtra("app","kakao")
                sendBroadcast(intent)
            }
            "영상 통화" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 2)
                intent.putExtra("app","kakao")
                sendBroadcast(intent)
            }
            "음성 통화" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 3)
                intent.putExtra("app","kakao")
                sendBroadcast(intent)
            }
            "선물 하기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 4)
                intent.putExtra("app","kakao")
                sendBroadcast(intent)
            }
            "프로필 설정하기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 5)
                intent.putExtra("app","kakao")
                sendBroadcast(intent)
            }
            "쇼핑 하기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 6)
                intent.putExtra("app","kakao")
                sendBroadcast(intent)
            }
            "친구 추가" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 7)
                intent.putExtra("app","kakao")
                sendBroadcast(intent)
            }
            "더보기카카오" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 8)
                intent.putExtra("app","kakao")
                sendBroadcast(intent)
            }
            "네이버 검색 하기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 1)
                intent.putExtra("app","naver")
                println("good")
                sendBroadcast(intent)
            }
            "주식 보기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 2)
                intent.putExtra("app","naver")
                sendBroadcast(intent)
            }
            "날씨 보기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 3)
                intent.putExtra("app","naver")
                sendBroadcast(intent)
            }
            "메일보기,보내기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 4)
                intent.putExtra("app","naver")
                sendBroadcast(intent)
            }
            "뉴스 보기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 5)
                intent.putExtra("app","naver")
                sendBroadcast(intent)
            }
            "쇼핑 하기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 6)
                intent.putExtra("app","naver")
                sendBroadcast(intent)
            }
            "지도 보기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 7)
                intent.putExtra("app","naver")
                sendBroadcast(intent)
            }
            "더보기네이버" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 8)
                intent.putExtra("app","naver")
                sendBroadcast(intent)
            }
            "정치/사회 뉴스보기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 1)
                intent.putExtra("app","news")
                sendBroadcast(intent)
            }
            "연예 뉴스보기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 2)
                intent.putExtra("app","news")
                sendBroadcast(intent)
            }
            "스포츠 뉴스보기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 3)
                intent.putExtra("app","news")
                sendBroadcast(intent)
            }
            "경제 뉴스보기" -> {
                val intent = Intent(ACTION_RECENTLY_BUTTON)
                intent.putExtra("btn", 4)
                intent.putExtra("app","news")
                sendBroadcast(intent)
            }
            // ... 나머지 옵션들에 대한 코드 ...
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favor)

        supportActionBar?.apply {
            title = "최근 사용한 옵션" // 툴바 제목
            setDisplayHomeAsUpEnabled(true) // 뒤로 가기 버튼 활성화
        }
        val btnFavorFunction1: Button = findViewById(R.id.btnFavorFunction1)
        val btnFavorFunction2: Button = findViewById(R.id.btnFavorFunction2)
        val btnFavorFunction3: Button = findViewById(R.id.btnFavorFunction3)
        val btnFavorFunction4: Button = findViewById(R.id.btnFavorFunction4)
        val btnFavorFunction5: Button = findViewById(R.id.btnFavorFunction5)
        val btnFavorFunction6: Button = findViewById(R.id.btnFavorFunction6)
        val btnFavorFunction7: Button = findViewById(R.id.btnFavorFunction7)
        val btnFavorFunction8: Button = findViewById(R.id.btnFavorFunction8)
        // 버튼들을 배열에 연결
        val recentOptions = RecentOptionsManager.getRecentOptions()
        if (recentOptions.size > 0) {
            btnFavorFunction1.text = recentOptions[0]
        }
        if (recentOptions.size > 1) {
            btnFavorFunction2.text = recentOptions[1]
        }
        if (recentOptions.size > 2) {
            btnFavorFunction3.text = recentOptions[2]
        }
        if (recentOptions.size > 3) {
            btnFavorFunction4.text = recentOptions[3]
        }
        if (recentOptions.size > 4) {
            btnFavorFunction5.text = recentOptions[4]
        }
        if (recentOptions.size > 5) {
            btnFavorFunction6.text = recentOptions[5]
        }
        if (recentOptions.size > 6) {
            btnFavorFunction7.text = recentOptions[6]
        }
        if (recentOptions.size > 7) {
            btnFavorFunction8.text = recentOptions[7]
        }
        btnFavorFunction1.setOnClickListener {
            performOptionAction(btnFavorFunction1.text.toString())
        }
        btnFavorFunction2.setOnClickListener {
            performOptionAction(btnFavorFunction2.text.toString())
        }
        btnFavorFunction3.setOnClickListener {
            performOptionAction(btnFavorFunction3.text.toString())
        }
        btnFavorFunction4.setOnClickListener {
            performOptionAction(btnFavorFunction4.text.toString())
        }
        btnFavorFunction5.setOnClickListener {
            performOptionAction(btnFavorFunction5.text.toString())
        }
        btnFavorFunction6.setOnClickListener {
            performOptionAction(btnFavorFunction6.text.toString())
        }
        btnFavorFunction7.setOnClickListener {
            performOptionAction(btnFavorFunction7.text.toString())
        }
        btnFavorFunction8.setOnClickListener {
            performOptionAction(btnFavorFunction8.text.toString())
        }

    }
    override fun onResume() {
        super.onResume()
        val hideImageIntent = Intent(MainActivity.ACTION_HIDE_IMAGE)
        sendBroadcast(hideImageIntent)
    }

    private fun startEpisodeIntent(packageName: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            startActivity(intent)
        } else {
            // Handle the case when the target app is not installed
            // You can show an error message or direct the user to install the app
            val link = "https://play.google.com/store/apps/details?id=$packageName"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(link)
            }
            startActivity(intent)
        }
    }

}