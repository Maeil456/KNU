package com.example.test1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class KakaoFunctionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_functions)

        val btnKakaoFunction1 = findViewById<Button>(R.id.btnKakaoFunction1)
        btnKakaoFunction1.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
            RecentOptionsManager.addOption("문자 보내기")
        }
        val btnKakaoFunction2 = findViewById<Button>(R.id.btnKakaoFunction2)
        btnKakaoFunction2.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
            RecentOptionsManager.addOption("영상 통화")
        }
        val btnKakaoFunction3 = findViewById<Button>(R.id.btnKakaoFunction3)
        btnKakaoFunction3.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
            RecentOptionsManager.addOption("음성 통화")
        }
        val btnKakaoFunction4 = findViewById<Button>(R.id.btnKakaoFunction4)
        btnKakaoFunction4.setOnClickListener {
            val packageName = "https://weather.naver.com/today/15133580?cpName=KMA"
            val naverWeatherIntent = Intent(Intent.ACTION_VIEW, Uri.parse(packageName))
            naverWeatherIntent.setPackage("com.nhn.android.search")
            if (naverWeatherIntent.resolveActivity(packageManager) != null) {
                // 네이버 앱이 설치되어 있으면 네이버 앱으로 URL을 엽니다.
                startActivity(naverWeatherIntent)
            } else {
                // 네이버 앱이 설치되어 있지 않다면, 사용자에게 앱 설치를 제안하는 다른 처리를 할 수 있습니다.
                Toast.makeText(this, "네이버 앱이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
            RecentOptionsManager.addOption("선물 하기")
        }
        val btnKakaoFunction5 = findViewById<Button>(R.id.btnKakaoFunction5)
        btnKakaoFunction5.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
            RecentOptionsManager.addOption("프로필 설정하기")
        }
        val btnKakaoFunction6 = findViewById<Button>(R.id.btnKakaoFunction6)
        btnKakaoFunction6.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
            RecentOptionsManager.addOption("쇼핑 하기")
        }
        val btnKakaoFunction7 = findViewById<Button>(R.id.btnKakaoFunction7)
        btnKakaoFunction7.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
            RecentOptionsManager.addOption("친구 추가")
        }


        // 다른 버튼들에 대한 클릭 리스너도 설정합니다.
    }

    private fun startEpisodeIntent(packageName: String) {
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            startActivity(launchIntent)
        } else {
            val link = "https://play.google.com/store/apps/details?id=$packageName"
            val marketIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(link)
            }
            startActivity(marketIntent)
        }
    }
}