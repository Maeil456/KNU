package com.example.test1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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

        supportActionBar?.apply {
            title = "카카오톡" // 툴바 제목
            setDisplayHomeAsUpEnabled(true) // 뒤로 가기 버튼 활성화
        }

        val btnKakaoFunction1 = findViewById<Button>(R.id.btnKakaoFunction1)
        val btnKakaoFunction2 = findViewById<Button>(R.id.btnKakaoFunction2)
        val btnKakaoFunction3 = findViewById<Button>(R.id.btnKakaoFunction3)
        val btnKakaoFunction4 = findViewById<Button>(R.id.btnKakaoFunction4)
        val btnKakaoFunction5 = findViewById<Button>(R.id.btnKakaoFunction5)
        val btnKakaoFunction6 = findViewById<Button>(R.id.btnKakaoFunction6)
        val btnKakaoFunction7 = findViewById<Button>(R.id.btnKakaoFunction7)
        val btnKakaoFunction8 = findViewById<Button>(R.id.btnKakaoFunction8)

        val filter = IntentFilter().apply {
            addAction(FavorActivity.ACTION_RECENTLY_BUTTON)
        }
        registerReceiver(recentlyButtonReceiver, filter)

        btnKakaoFunction1.setOnClickListener {
            val packageName = "com.kakao.talk"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startMainIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("문자 보내기")
        }

        btnKakaoFunction2.setOnClickListener {
            val packageName = "com.kakao.talk"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startMainIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("영상 통화")
        }

        btnKakaoFunction3.setOnClickListener {
            val packageName = "com.kakao.talk"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startMainIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("음성 통화")
        }

        btnKakaoFunction4.setOnClickListener {
            val packageName = "com.kakao.talk"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)

            startMainIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("선물 하기")
        }

        btnKakaoFunction5.setOnClickListener {
            val packageName = "com.kakao.talk"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startMainIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("프로필 설정하기")
        }

        btnKakaoFunction6.setOnClickListener {
            val packageName = "com.kakao.talk"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)

            startMainIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("쇼핑 하기")
        }

        btnKakaoFunction7.setOnClickListener {
            val packageName = "com.kakao.talk"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startMainIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("친구 추가")
        }
        btnKakaoFunction8.setOnClickListener {
            val packageName = "com.kakao.talk"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startMainIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)
            RecentOptionsManager.addOption("카카오 테스트")
        }

    }

    private val recentlyButtonReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == FavorActivity.ACTION_RECENTLY_BUTTON) {
                val appname = intent.getStringExtra("app")
                val btnNumber = intent.getIntExtra("btn", 9)
                if(appname=="kakao") {
                    if (btnNumber < 9) {
                        when (btnNumber) {
                            1 -> findViewById<Button>(R.id.btnKakaoFunction1).performClick()
                            2 -> findViewById<Button>(R.id.btnKakaoFunction2).performClick()
                            3 -> findViewById<Button>(R.id.btnKakaoFunction3).performClick()
                            4 -> findViewById<Button>(R.id.btnKakaoFunction4).performClick()
                            5 -> findViewById<Button>(R.id.btnKakaoFunction5).performClick()
                            6 -> findViewById<Button>(R.id.btnKakaoFunction6).performClick()
                            7 -> findViewById<Button>(R.id.btnKakaoFunction7).performClick()
                            8 -> findViewById<Button>(R.id.btnKakaoFunction8).performClick()
                            else -> println("intent Error")
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
    private fun startMainIntent(packageName: String) {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
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
        val hideImageIntent = Intent(MainActivity.ACTION_HIDE_IMAGE)
        sendBroadcast(hideImageIntent)
    }


}
