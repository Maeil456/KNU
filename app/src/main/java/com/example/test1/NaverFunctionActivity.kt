package com.example.test1

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.parcelize.Parcelize

class NaverFunctionActivity : AppCompatActivity() {

    @Parcelize
    data class Coord(val x: Int, val y: Int) : Parcelable

    private var imageIndex: ArrayList<String> = arrayListOf()
    private val targetPositions = ArrayList<Coord>()
    val image = FloatingImageService()

    private val requestOverlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (isOverlayPermissionGranted()) {
                startFloatingImageService()
            }
        }
    companion object {
        const val ACTION_SHOW_FLOATING_IMAGE = "com.example.test1.SHOW_FLOATING_IMAGE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_naver_functions)
        var currentPositionIndex = 0

        if (!isOverlayPermissionGranted()) {
            requestOverlayPermission()
        } else {
            startFloatingImageService()
        }
        val btnNaverFunction1 = findViewById<Button>(R.id.btnNaverFunction1)
        btnNaverFunction1.setOnClickListener {
            val packageName = "com.nhn.android.search"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
        }
        val btnNaverFunction2 = findViewById<Button>(R.id.btnNaverFunction2)
        btnNaverFunction2.setOnClickListener {
            val packageName = "com.nhn.android.search"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
        }
        val btnNaverFunction3 = findViewById<Button>(R.id.btnNaverFunction3)
        btnNaverFunction3.setOnClickListener {
            val packageName = "com.nhn.android.search"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setImage("Naver_search_image")

            val intent = Intent(ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
        }
        val btnNaverFunction4 = findViewById<Button>(R.id.btnNaverFunction4)
        btnNaverFunction4.setOnClickListener {
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
        }
        val btnNaverFunction5 = findViewById<Button>(R.id.btnNaverFunction5)
        btnNaverFunction5.setOnClickListener {
            val packageName = "com.nhn.android.search"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
        }
        val btnNaverFunction6 = findViewById<Button>(R.id.btnNaverFunction6)
        btnNaverFunction6.setOnClickListener {
            val packageName = "com.nhn.android.search"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
        }
        val btnNaverFunction7 = findViewById<Button>(R.id.btnNaverFunction7)
        btnNaverFunction7.setOnClickListener {
            val packageName = "com.nhn.android.search"
            startEpisodeIntent(packageName)
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
        }


        // 다른 버튼들에 대한 클릭 리스너도 설정합니다.
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
            val intent = Intent(this, FloatingImageService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startService(intent)
            } //else {
            //  this.startService(intent)
            //}


        }
        private fun setArray(arrayName: String){
            val resourceId = resources.getIdentifier(arrayName, "array", packageName)

            if (resourceId != 0) { // Check if the resource exists
                val positionsStringArray = resources.getStringArray(resourceId)

                for (positionString in positionsStringArray) {
                    val coordinates = positionString.split(",").map { it.trim().toInt() }
                    if (coordinates.size == 2) {
                        targetPositions.add(Coord(coordinates[0], coordinates[1]))
                    }
                }
                println(targetPositions.toString())
                println(targetPositions.size)
            } else {
                showToast("Error: Array not found!")
            }
        }

        private fun setImage(arrayName: String){
            val resourceId = resources.getIdentifier(arrayName, "array", packageName)

            if (resourceId != 0) { // Check if the resource exists
                val imageStringArray = resources.getStringArray(resourceId)
                for (imageString in imageStringArray) {
                    imageIndex.add(imageString)
                }
            } else {
                showToast("Error: Array not found!")
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

        private fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }