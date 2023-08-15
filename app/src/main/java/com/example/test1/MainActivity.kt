package com.example.test1

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val requestOverlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (isOverlayPermissionGranted()) {
                startFloatingImageService()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var currentPositionIndex = 0

        if (!isOverlayPermissionGranted()) {
            requestOverlayPermission()
        } else {
            startFloatingImageService()
        }


        val btnEpisode1 = findViewById<Button>(R.id.btnkakao)
        val btnEpisode2 = findViewById<Button>(R.id.btnnaver)
        // Add more buttons for each episode as needed

        btnEpisode1.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
        }

        btnEpisode2.setOnClickListener {
            val packageName = "com.nhn.android.search"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
            val floatingImageView = findViewById<ImageView>(R.id.floating_image_view)

            floatingImageView.setOnTouchListener(object : View.OnTouchListener {
                // 기존에 생성한 터치 리스너 코드 (변수 초기화, onTouch 메서드)
                // ACTION_UP 경우에 대한 코드 수정
                MotionEvent.ACTION_UP -> {
                    if (currentPositionIndex < targetPositions.size) {
                        val targetPosition = targetPositions[currentPositionIndex]
                        val layoutParams =
                            floatingImageView.layoutParams as ViewGroup.MarginLayoutParams
                        layoutParams.setMargins(targetPosition.first, targetPosition.second, 0, 0)
                        floatingImageView.layoutParams = layoutParams

                        currentPositionIndex++
                    } else {
                        // ImageView를 사라지게 할 때 사용
                        floatingImageView.visibility = View.INVISIBLE
                    }
                }
            })
        }
    }
    private fun requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            requestOverlayPermissionLauncher.launch(intent)
        }
    }

    private fun isOverlayPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(this)
        } else {
            true
        }
    }
    private fun isAccessibilityServiceEnabled(): Boolean {
        val enabledServices = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )?:return false
        val services = enabledServices.split(":".toRegex()).toTypedArray()
        val packageName = packageName
        val className = FloatingImageService::class.java.name
        val expectedComponentName = ComponentName(packageName, className).flattenToString()

        for (service in services) {
            if (service.equals(expectedComponentName, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
    private fun openAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    private fun startFloatingImageService() {
        if (!isAccessibilityServiceEnabled()) {
            openAccessibilitySettings()
        } else {
            val intent = Intent(this, FloatingImageService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }
    private fun setArray(arrayName: String){
        val resourceId = resources.getIdentifier(arrayName, "array", packageName)
        val targetPositions = ArrayList<Pair<Int, Int>>()

        if (resourceId != 0) { // Check if the resource exists
            val positionsStringArray = resources.getStringArray(resourceId)

            for (positionString in positionsStringArray) {
                val coordinates = positionString.split(",").map { it.trim().toInt() }
                if (coordinates.size == 2) {
                    val pair = coordinates[0] to coordinates[1]
                    targetPositions.add(pair)
                }
            }

        } else {
            showToast("Error: Array not found!")
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
}

