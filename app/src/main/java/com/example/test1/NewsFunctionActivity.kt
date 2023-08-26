package com.example.test1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class NewsFunctionActivity : AppCompatActivity() {

    private var imageIndex: ArrayList<String> = arrayListOf()
    private val targetPositions = ArrayList<MainActivity.Coord>()
    private val targetSizes = ArrayList<MainActivity.Coord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val btnEpisode1 = findViewById<Button>(R.id.btnpolit)
        val btnEpisode2 = findViewById<Button>(R.id.btnceleb)
        val btnEpisode3 = findViewById<Button>(R.id.btnsports)
        val btnEpisode4 = findViewById<Button>(R.id.btnecono)

        btnEpisode1.setOnClickListener {
            val packageName = "com.nhn.android.search"
            val url = "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=100"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)

            startLinkIntent(packageName,url)
            RecentOptionsManager.addOption("정치/사회 뉴스보기")
        }

        btnEpisode2.setOnClickListener {
            val packageName = "com.nhn.android.search"
            val url = "https://entertain.naver.com/home"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)

            startLinkIntent(packageName,url)
            RecentOptionsManager.addOption("연예 뉴스보기")
        }

        btnEpisode3.setOnClickListener {
            val packageName = "com.nhn.android.search"
            val url = "https://sports.news.naver.com/index"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)

            startLinkIntent(packageName,url)
            RecentOptionsManager.addOption("스포츠 뉴스보기")
        }

        btnEpisode4.setOnClickListener {
            val packageName = "com.nhn.android.search"
            val url = "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=101"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)

            startLinkIntent(packageName,url)
            RecentOptionsManager.addOption("경제 뉴스보기")
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        val hideImageIntent = Intent(FloatingImageService.ACTION_HIDE_IMAGE)
        sendBroadcast(hideImageIntent)
    }
}