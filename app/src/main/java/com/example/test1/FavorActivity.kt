package com.example.test1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FavorActivity : AppCompatActivity() {

    private fun performOptionAction(option: String) {
        when (option) {
            "문자 보내기" -> {
                val packageName = "com.kakao.talk"
                startEpisodeIntent(packageName)
                val serviceIntent = Intent(this, FloatingImageService::class.java)
                startService(serviceIntent)
            }
            "영상 통화" -> {
                val packageName = "com.kakao.talk"
                startEpisodeIntent(packageName)
                val serviceIntent = Intent(this, FloatingImageService::class.java)
                startService(serviceIntent)
            }
            "음성 통화" -> {
                val packageName = "com.kakao.talk"
                startEpisodeIntent(packageName)
                val serviceIntent = Intent(this, FloatingImageService::class.java)
                startService(serviceIntent)
            }
            // ... 나머지 옵션들에 대한 코드 ...
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favor)

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