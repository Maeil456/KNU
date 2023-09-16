package com.example.test1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
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
            "카카오쇼핑 하기" -> {
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
            "네이버쇼핑 하기" -> {
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
        val btnFavorFunction1: ImageButton = findViewById(R.id.btnFavorFunction1)
        val btnFavorFunction2: ImageButton = findViewById(R.id.btnFavorFunction2)
        val btnFavorFunction3: ImageButton = findViewById(R.id.btnFavorFunction3)
        val btnFavorFunction4: ImageButton = findViewById(R.id.btnFavorFunction4)
        val btnFavorFunction5: ImageButton = findViewById(R.id.btnFavorFunction5)
        val btnFavorFunction6: ImageButton = findViewById(R.id.btnFavorFunction6)
        val btnFavorFunction7: ImageButton = findViewById(R.id.btnFavorFunction7)
        val btnFavorFunction8: ImageButton = findViewById(R.id.btnFavorFunction8)
        // 버튼들을 배열에 연결
        val recentOptions = RecentOptionsManager.getRecentOptions()
        if (recentOptions.size > 0) {
            btnFavorFunction1.tag = recentOptions[0]
            setbtnImage(btnFavorFunction1.tag.toString(),1)
        }
        if (recentOptions.size > 1) {
            btnFavorFunction2.tag = recentOptions[1]
            setbtnImage(btnFavorFunction2.tag.toString(),2)
        }
        if (recentOptions.size > 2) {
            btnFavorFunction3.tag = recentOptions[2]
            setbtnImage(btnFavorFunction3.tag.toString(),3)
        }
        if (recentOptions.size > 3) {
            btnFavorFunction4.tag = recentOptions[3]
            setbtnImage(btnFavorFunction4.tag.toString(),4)
        }
        if (recentOptions.size > 4) {
            btnFavorFunction5.tag = recentOptions[4]
            setbtnImage(btnFavorFunction5.tag.toString(),5)
        }
        if (recentOptions.size > 5) {
            btnFavorFunction6.tag = recentOptions[5]
            setbtnImage(btnFavorFunction6.tag.toString(),6)
        }
        if (recentOptions.size > 6) {
            btnFavorFunction7.tag = recentOptions[6]
            setbtnImage(btnFavorFunction7.tag.toString(),7)
        }
        if (recentOptions.size > 7) {
            btnFavorFunction8.tag = recentOptions[7]
            setbtnImage(btnFavorFunction8.tag.toString(),8)
        }
        btnFavorFunction1.setOnClickListener {
            performOptionAction(btnFavorFunction1.tag.toString())
        }
        btnFavorFunction2.setOnClickListener {
            performOptionAction(btnFavorFunction2.tag.toString())
        }
        btnFavorFunction3.setOnClickListener {
            performOptionAction(btnFavorFunction3.tag.toString())
        }
        btnFavorFunction4.setOnClickListener {
            performOptionAction(btnFavorFunction4.tag.toString())
        }
        btnFavorFunction5.setOnClickListener {
            performOptionAction(btnFavorFunction5.tag.toString())
        }
        btnFavorFunction6.setOnClickListener {
            performOptionAction(btnFavorFunction6.tag.toString())
        }
        btnFavorFunction7.setOnClickListener {
            performOptionAction(btnFavorFunction7.tag.toString())
        }
        btnFavorFunction8.setOnClickListener {
            performOptionAction(btnFavorFunction8.tag.toString())
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

    private fun setbtnImage(tag: String, num: Int){

        val buttonId = resources.getIdentifier("btnFavorFunction$num", "id", packageName)
        val myButton: ImageButton = findViewById(buttonId)

        when (tag) {
            "배달의 민족" -> {
                myButton.setImageResource(R.drawable.main_bt_05)
            }
            "유튜브" -> {
                myButton.setImageResource(R.drawable.main_bt_06)
            }
            "다음" -> {
                myButton.setImageResource(R.drawable.main_bt_07)
            }
            "더보기메인" -> {
                myButton.setImageResource(R.drawable.main_bt_08)
            }
            "문자 보내기" -> {
                myButton.setImageResource(R.drawable.kakao_bt_01)
            }
            "영상 통화" -> {
                myButton.setImageResource(R.drawable.kakao_bt_02)
            }
            "음성 통화" -> {
                myButton.setImageResource(R.drawable.kakao_bt_03)
            }
            "선물 하기" -> {
                myButton.setImageResource(R.drawable.kakao_bt_04)
            }
            "프로필 설정하기" -> {
                myButton.setImageResource(R.drawable.kakao_bt_05)
            }
            "카카오쇼핑 하기" -> {
                myButton.setImageResource(R.drawable.kakao_bt_06)
            }
            "친구 추가" -> {
                myButton.setImageResource(R.drawable.kakao_bt_07)
            }
            "더보기카카오" -> {
                myButton.setImageResource(R.drawable.kakao_bt_08)
            }
            "네이버 검색 하기" -> {
                myButton.setImageResource(R.drawable.naver_bt_01)
            }
            "주식 보기" -> {
                myButton.setImageResource(R.drawable.naver_bt_02)
            }
            "날씨 보기" -> {
                myButton.setImageResource(R.drawable.naver_bt_03)
            }
            "메일보기,보내기" -> {
                myButton.setImageResource(R.drawable.naver_bt_04)
            }
            "뉴스 보기" -> {
                myButton.setImageResource(R.drawable.naver_bt_05)
            }
            "네이버쇼핑 하기" -> {
                myButton.setImageResource(R.drawable.naver_bt_06)
            }
            "지도 보기" -> {
                myButton.setImageResource(R.drawable.naver_bt_07)
            }
            "더보기네이버" -> {
                myButton.setImageResource(R.drawable.naver_bt_08)
            }
            "정치/사회 뉴스보기" -> {
                myButton.setImageResource(R.drawable.naver_02_bt_01)
            }
            "연예 뉴스보기" -> {
                myButton.setImageResource(R.drawable.naver_02_bt_02)
            }
            "스포츠 뉴스보기" -> {
                myButton.setImageResource(R.drawable.naver_02_bt_03)
            }
            "경제 뉴스보기" -> {
                myButton.setImageResource(R.drawable.naver_02_bt_04)
            }
            else -> myButton.setImageResource(R.drawable.appicon_2)
            // ... 나머지 옵션들에 대한 코드 ...
        }

    }

}