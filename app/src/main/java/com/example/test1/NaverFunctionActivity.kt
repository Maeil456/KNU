package com.example.test1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NaverFunctionActivity : AppCompatActivity() {

    private var imageIndex: ArrayList<String> = arrayListOf()
    private val targetPositions = ArrayList<MainActivity.Coord>()
    private val targetSizes = ArrayList<MainActivity.Coord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_naver_functions)

        supportActionBar?.apply {
            title = "네이버" // 툴바 제목
            setDisplayHomeAsUpEnabled(true) // 뒤로 가기 버튼 활성화
        }

        val btnEpisode1 = findViewById<Button>(R.id.btnNaverFunction1)
        val btnEpisode2 = findViewById<Button>(R.id.btnNaverFunction2)
        val btnEpisode3 = findViewById<Button>(R.id.btnNaverFunction3)
        val btnEpisode4 = findViewById<Button>(R.id.btnNaverFunction4)
        val btnEpisode5 = findViewById<Button>(R.id.btnNaverFunction5)
        val btnEpisode6 = findViewById<Button>(R.id.btnNaverFunction6)
        val btnEpisode7 = findViewById<Button>(R.id.btnNaverFunction7)
        //val btnEpisode8 = findViewById<Button>(R.id.btnNaverFunction8)

        val filter = IntentFilter().apply {
            addAction(FavorActivity.ACTION_RECENTLY_BUTTON)
        }
        registerReceiver(recentlyButtonReceiver, filter)

        btnEpisode1.setOnClickListener {
            val packageName = "com.nhn.android.search"
            val url = "https://www.naver.com"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")
            startFloatingImageService()

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("네이버 검색 하기")
        }

        btnEpisode2.setOnClickListener {
            val packageName = "com.nhn.android.search"
            val url = "https://m.stock.naver.com/"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")
            startFloatingImageService()

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("주식 보기")
        }

        btnEpisode3.setOnClickListener {
            val packageName = "com.nhn.android.search"
            val url = "https://weather.naver.com/"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")
            startFloatingImageService()

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("날씨 보기")
        }

        btnEpisode4.setOnClickListener {
            val packageName = "com.nhn.android.search"
            val url = "https://mail.naver.com/v2/folders/0/all"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")
            startFloatingImageService()

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("메일보기,보내기")
        }
        btnEpisode5.setOnClickListener {
            val intent = Intent(this@NaverFunctionActivity, NewsFunctionActivity::class.java)
            startActivity(intent)
            RecentOptionsManager.addOption("뉴스보기")
        }
        btnEpisode6.setOnClickListener {
            val packageName = "com.nhn.android.search"
            val url = "https://shopping.naver.com/home"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")
            startFloatingImageService()

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("쇼핑 하기")
        }
        btnEpisode7.setOnClickListener {
            val packageName = "com.nhn.android.nmap"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")
            startFloatingImageService()

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startEpisodeIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("지도 보기")
        }
    }

    private val recentlyButtonReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == FavorActivity.ACTION_RECENTLY_BUTTON) {
                val appname = intent.getStringExtra("app")
                val btnNumber = intent.getIntExtra("btn", 9)
                if(appname=="naver") {
                    if (btnNumber < 9) {
                        println("intent win")
                        when (btnNumber) {
                            1 -> findViewById<Button>(R.id.btnNaverFunction1).performClick()
                            2 -> findViewById<Button>(R.id.btnNaverFunction2).performClick()
                            3 -> findViewById<Button>(R.id.btnNaverFunction3).performClick()
                            4 -> findViewById<Button>(R.id.btnNaverFunction4).performClick()
                            5 -> findViewById<Button>(R.id.btnNaverFunction5).performClick()
                            6 -> findViewById<Button>(R.id.btnNaverFunction6).performClick()
                            7 -> findViewById<Button>(R.id.btnNaverFunction7).performClick()
                            8 -> findViewById<Button>(R.id.btnNaverFunction8).performClick()
                        }
                    } else println("intent Error")
                }
            }

        }
    }

    private fun setArray(arrayName: String) {
        val resourceId = resources.getIdentifier(arrayName, "array", packageName)


        if (resourceId != 0) {
            val positionsStringArray = resources.getStringArray(resourceId)
            for (positionString in positionsStringArray) {
                val coordinates = positionString.split(",").map { it.trim().toInt() }
                if (coordinates.size == 2) {
                    targetPositions.add(MainActivity.Coord(coordinates[0], coordinates[1]))
                }
            }
            println(targetPositions.toString())
            println(targetPositions.size)
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
                    targetSizes.add(MainActivity.Coord(coordinates[0], coordinates[1]))
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

    private fun startLinkIntent(packageName: String,url: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        appIntent.setPackage(packageName)
        if (appIntent.resolveActivity(packageManager) != null) {
            // 네이버 앱이 설치되어 있으면 네이버 앱으로 URL을 엽니다.
            startActivity(appIntent)
        } else {
            val link = "https://play.google.com/store/apps/details?id=$packageName"
            val appIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(link)
            }
            val serviceIntent = Intent(this, FloatingImageService::class.java)
            startService(serviceIntent)
        }
    }
    private fun startFloatingImageService() {
        val intentF = Intent(this, FloatingImageService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(intentF)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        val hideImageIntent = Intent(MainActivity.ACTION_HIDE_IMAGE)
        sendBroadcast(hideImageIntent)
    }

}