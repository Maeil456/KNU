package com.example.test1

// MainActivity.kt
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEpisode1 = findViewById<Button>(R.id.btnkakao)
        val btnEpisode2 = findViewById<Button>(R.id.btnnaver)
        // Add more buttons for each episode as needed

        btnEpisode1.setOnClickListener {
            val kakaoTalkIntent = packageManager.getLaunchIntentForPackage("com.kakao.talk")
            if (kakaoTalkIntent != null) {
                startActivity(kakaoTalkIntent)
                val floatingImage = FloatingImage(this)
                floatingImage.show(100, 200) // Set the desired X and Y position for the floating image
            } else {
                // Handle the case when KakaoTalk is not installed
                // You can show an error message or direct the user to install KakaoTalk
            }
        }
    //haha wow
        btnEpisode2.setOnClickListener {
            // Similar logic as btnEpisode1
            // Launch KakaoTalk with the appropriate episode intent and display the floating image
        }

        // Implement click listeners for other buttons as needed for each episode
    }
}
