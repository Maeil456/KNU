package com.example.test1

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val REQUEST_OVERLAY_PERMISSION = 101
    private lateinit var overlayPermissionLauncher: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.M)
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
                    showOverlayPermissionAlert()
                }
            }
        }

        val btnEpisode1 = findViewById<Button>(R.id.btnkakao)
        val btnEpisode2 = findViewById<Button>(R.id.btnnaver)
        // Add more buttons for each episode as needed

        btnEpisode1.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            checkOverlayPermission()
            val floatingImageService = FloatingImageService()
            floatingImageService.showFloatingImage(100, 200)
        }

        btnEpisode2.setOnClickListener {
            // Similar logic as btnEpisode1
            // Launch KakaoTalk with the appropriate episode intent and display the floating image
            val packageName = "com.nhn.android.search"
            startEpisodeIntent(packageName)
            val floatingImageService = FloatingImageService()
            floatingImageService.showFloatingImage(100, 200)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            // You need to request the permission at runtime
            showOverlayPermissionAlert()
        } else {
            // Permission already granted, show the floating image
            //showFloatingImage()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showOverlayPermissionAlert() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Permission Required")
        alertBuilder.setMessage("This app requires the overlay permission to function properly.")
        alertBuilder.setPositiveButton("Grant Permission") { dialog, which ->
            requestOverlayPermission()
        }
        alertBuilder.setNegativeButton("Cancel") { dialog, which ->
            // Handle the case when the user cancels the permission request
            // For example, show an alternative UI or a message to the user
        }
        val alertDialog = alertBuilder.create()
        alertDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestOverlayPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
        overlayPermissionLauncher.launch(intent)
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

