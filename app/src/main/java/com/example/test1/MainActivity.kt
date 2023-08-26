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
import kotlinx.android.parcel.Parcelize

class MainActivity : AppCompatActivity() {

    @Parcelize
    data class Coord(val x: Int, val y: Int) : Parcelable

    private var imageIndex: ArrayList<String> = arrayListOf()
    private val targetPositions = ArrayList<Coord>()
    private val targetSizes = ArrayList<Coord>()

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
        setContentView(R.layout.activity_main)

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
        //val btnEpisode8 = findViewById<Button>(R.id.btnetc)

        btnEpisode1.setOnClickListener {
            val intent = Intent(this@MainActivity, FavorActivity::class.java)
            startActivity(intent)
        }

        btnEpisode2.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }

        btnEpisode3.setOnClickListener {
            val intent = Intent(this, KakaoFunctionsActivity::class.java)
            startActivity(intent)
        }

        btnEpisode4.setOnClickListener {
            val intent = Intent(this, NaverFunctionActivity::class.java)
            startActivity(intent)
        }
        btnEpisode5.setOnClickListener {
            val packageName = "com.woowahan.baemin"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
        }
        btnEpisode6.setOnClickListener {
            val packageName = "com.google.android.youtube"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
        }
        btnEpisode7.setOnClickListener {
            val packageName = "net.daum.android.mail"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
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

    private fun startFloatingImageService() {
        val intent = Intent(this, FloatingImageService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(intent)
        }
    }

    private fun setArray(arrayName: String) {
        val resourceId = resources.getIdentifier(arrayName, "array", packageName)

        targetPositions.clear()

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
            println("Log From setArray")
        } else {
            showToast("Error: Array not found!")
        }
    }



    private fun setArray2(arrayName: String){
        val resourceId = resources.getIdentifier(arrayName, "array", packageName)

        targetSizes.clear()

        if (resourceId != 0) { // Check if the resource exists
            val sizeStringArray = resources.getStringArray(resourceId)

            for (sizeString in sizeStringArray) {
                val coordinates = sizeString.split(",").map { it.trim().toInt() }
                if (coordinates.size == 2) {
                    targetSizes.add(Coord(coordinates[0], coordinates[1]))
                }
            }
            println(targetSizes.toString())
            println(targetSizes.size)
            println("Log From setArray2")
        } else {
            showToast("Error: Array not found!")
        }
    }

    private fun setImage(arrayName: String){
        val resourceId = resources.getIdentifier(arrayName, "array", packageName)

        imageIndex.clear()

        if (resourceId != 0) { // Check if the resource exists
            val imageStringArray = resources.getStringArray(resourceId)
            for (imageString in imageStringArray) {
                imageIndex.add(imageString)
            }
            println(imageIndex.toString())
            println(imageIndex.size)
            println("Log From setImage")
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


    override fun onResume() {
        super.onResume()
        val hideImageIntent = Intent(FloatingImageService.ACTION_HIDE_IMAGE)
        sendBroadcast(hideImageIntent)
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
}


