package com.example.test1

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.test1.FloatingImageService.Companion.ACTION_SHOW_FLOATING_IMAGE
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
        const val ACTION_HIDE_IMAGE = "com.example.test1.ACTION_HIDE_IMAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            title = "메인 화면" // 툴바 제목
        // 뒤로 가기 버튼 활성화
        }

        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.show(WindowInsets.Type.statusBars())
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
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

        val filter = IntentFilter().apply {
            addAction(FavorActivity.ACTION_RECENTLY_BUTTON)
        }
        registerReceiver(recentlyButtonReceiver, filter)

        btnEpisode1.setOnClickListener {
            checkImageService()
            val intent = Intent(this@MainActivity, FavorActivity::class.java)
            startActivity(intent)
        }

        btnEpisode2.setOnClickListener {
            checkImageService()
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }

        btnEpisode3.setOnClickListener {
            checkImageService()
            val intent = Intent(this, KakaoFunctionsActivity::class.java)
            startActivity(intent)
        }

        btnEpisode4.setOnClickListener {
            checkImageService()
            val intent = Intent(this, NaverFunctionActivity::class.java)
            startActivity(intent)
        }
        btnEpisode5.setOnClickListener {
            checkImageService()
            val packageName = "com.sampleapp"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startTestIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("배달의 민족")

        }
        btnEpisode6.setOnClickListener {
            checkImageService()
            val packageName = "com.google.android.youtube"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startEpisodeIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("유튜브")
        }
        btnEpisode7.setOnClickListener {
            checkImageService()
            val packageName = "net.daum.android.mail"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startEpisodeIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("다음")
        }
        btnEpisode8.setOnClickListener {
            checkImageService()
            val packageName = "com.nhn.android.search"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startEpisodeIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("더보기메인")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(recentlyButtonReceiver)
    }

    private val recentlyButtonReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == FavorActivity.ACTION_RECENTLY_BUTTON) {
                val appname = intent.getStringExtra("app")
                val btnNumber = intent.getIntExtra("btn", 9)
                if(appname=="main") {
                    if (btnNumber < 9) {
                        println("intent win")
                        when (btnNumber) {
                            5 -> findViewById<Button>(R.id.btndelivery).performClick()
                            6 -> findViewById<Button>(R.id.btnyoutube).performClick()
                            7 -> findViewById<Button>(R.id.btndaum).performClick()
                            8 -> findViewById<Button>(R.id.btnetc).performClick()
                            else -> println("intent Error")
                        }
                    }else println("intent Error")
                }
            }

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
        val intentF = Intent(this, FloatingImageService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(intentF)
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
        val hideImageIntent = Intent(ACTION_HIDE_IMAGE)
        sendBroadcast(hideImageIntent)
    }

    private fun checkImageService(){
        if (!isOverlayPermissionGranted()) {
            requestOverlayPermission()
        } else {
            startFloatingImageService()
        }
    }

    private fun startTestIntent(packageName: String) {
        val intent = Intent(Intent.ACTION_MAIN)
        //intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.setPackage(packageName)
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


