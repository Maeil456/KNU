package com.example.test1

// MainActivity.kt
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
                val link = "https://play.google.com/store/apps/details?id=com.kakao.talk&hl=ko&gl=US"
                val kakaoTalkIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(link)
                }
                startActivity(kakaoTalkIntent)
                val floatingImage = FloatingImage(this)
                floatingImage.show(100, 200)
            }
        }
    //haha
        btnEpisode2.setOnClickListener {
            // Similar logic as btnEpisode1
            // Launch KakaoTalk with the appropriate episode intent and display the floating image
        }

        // Implement click listeners for other buttons as needed for each episode
    }
    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // You need to request the permission at runtime
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + packageName)
                )
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
            } else {
                // Permission already granted, show the floating image
                showFloatingImage()
            }
        } else {
            // On pre-Marshmallow devices, no special permission is required
            showFloatingImage()
        }
    }
}
