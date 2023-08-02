package com.example.test1

// MainActivity.kt
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val REQUEST_OVERLAY_PERMISSION = 101
    private lateinit var overlayPermissionLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        overlayPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // Check if the permission was granted by the user
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    // Permission granted, show the floating image
                    //showFloatingImage()
                } else {
                    // Permission denied, handle it gracefully
                    // For example, show an alternative UI or a message to the user
                }
            }

            val btnEpisode1 = findViewById<Button>(R.id.btnkakao)
            val btnEpisode2 = findViewById<Button>(R.id.btnnaver)
            // Add more buttons for each episode as needed

            btnEpisode1.setOnClickListener {
                val packageName = "com.kakao.talk"
                startEpisodeIntent(packageName)
                val floatingImage = FloatingImage(this)
                floatingImage.show(100, 200)
            }
            //haha
            btnEpisode2.setOnClickListener {
                // Similar logic as btnEpisode1
                // Launch KakaoTalk with the appropriate episode intent and display the floating image
                val packageName = "com.nhn.android.search"
                startEpisodeIntent(packageName)
                val floatingImage = FloatingImage(this)
                floatingImage.show(100, 200)
            }
        }


        fun startEpisodeIntent(packageName: String) {
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
}
