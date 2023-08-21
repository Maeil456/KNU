package com.example.test1

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val targetPositions = ArrayList<Pair<Int, Int>>()

    private val requestOverlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (isOverlayPermissionGranted()) {
                startFloatingImageService()
            } else {
                showToast("Please grant overlay permission for proper functionality")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var currentPositionIndex = 0

        val OVERLAY_PERMISSION_REQUEST_CODE = 1001
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE)
        }

        if (!isOverlayPermissionGranted()) {
            requestOverlayPermission()
        } else {
            startFloatingImageService()
        }

        val btnEpisode1 = findViewById<Button>(R.id.btnfavor)
        val btnEpisode2 = findViewById<Button>(R.id.btnsearch)
        val btnEpisode3 = findViewById<Button>(R.id.btnkakao)
        val btnEpisode4 = findViewById<Button>(R.id.btnnaver)
        val btnEpisode5 = findViewById<Button>(R.id.btndelivery)
        val btnEpisode6 = findViewById<Button>(R.id.btnyoutube)
        val btnEpisode7 = findViewById<Button>(R.id.btndaum)
        val btnEpisode8 = findViewById<Button>(R.id.btnetc)

        btnEpisode2.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }

        btnEpisode3.setOnClickListener {
            val intent = Intent(this, KakaoFunctionsActivity::class.java)
            startActivity(intent)
        }

        btnEpisode4.setOnClickListener {
            val packageName = "com.nhn.android.search"
            startEpisodeIntent(packageName)
            if(isOverlayPermissionGranted()) {
                val serviceIntent = Intent(this, FloatingImageService::class.java)
                startService(serviceIntent)
            }
        }
        btnEpisode5.setOnClickListener {
            val packageName = "com.woowahan.baemin"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
        }
        btnEpisode6.setOnClickListener {
            val packageName = "com.google.android.youtube"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
        }
        btnEpisode7.setOnClickListener {
            val packageName = "net.daum.android.mail"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
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
        ) ?: return false
        val services = enabledServices.split(":".toRegex()).toTypedArray()
        val expectedComponentName = ComponentName(packageName, FloatingImageService::class.java.name).flattenToString()

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

    private fun setArray(arrayName: String) {
        val resourceId = resources.getIdentifier(arrayName, "array", packageName)
        targetPositions.clear()

        if (resourceId != 0) {
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}




