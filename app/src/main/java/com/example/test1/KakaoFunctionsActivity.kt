package com.example.test1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class KakaoFunctionsActivity : AppCompatActivity() {

    private var imageIndex: ArrayList<String> = arrayListOf()
    private val targetPositions = ArrayList<MainActivity.Coord>()
    private val targetSizes = ArrayList<MainActivity.Coord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_functions)

        val btnKakaoFunction1 = findViewById<Button>(R.id.btnKakaoFunction1)
        val btnKakaoFunction2 = findViewById<Button>(R.id.btnKakaoFunction2)
        val btnKakaoFunction3 = findViewById<Button>(R.id.btnKakaoFunction3)
        val btnKakaoFunction4 = findViewById<Button>(R.id.btnKakaoFunction4)
        val btnKakaoFunction5 = findViewById<Button>(R.id.btnKakaoFunction5)
        val btnKakaoFunction6 = findViewById<Button>(R.id.btnKakaoFunction6)
        val btnKakaoFunction7 = findViewById<Button>(R.id.btnKakaoFunction7)

        btnKakaoFunction1.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            RecentOptionsManager.addOption("문자 보내기")
        }

        btnKakaoFunction2.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            RecentOptionsManager.addOption("영상 통화")
        }

        btnKakaoFunction3.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            RecentOptionsManager.addOption("음성 통화")
        }

        btnKakaoFunction4.setOnClickListener {
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
            RecentOptionsManager.addOption("선물 하기")
        }

        btnKakaoFunction5.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            RecentOptionsManager.addOption("프로필 설정하기")
        }

        btnKakaoFunction6.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            RecentOptionsManager.addOption("쇼핑 하기")
        }

        btnKakaoFunction7.setOnClickListener {
            val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            RecentOptionsManager.addOption("친구 추가")
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
