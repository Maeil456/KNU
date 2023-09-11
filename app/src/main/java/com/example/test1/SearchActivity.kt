package com.example.test1

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {

    private var imageIndex: ArrayList<String> = arrayListOf()
    private val targetPositions = ArrayList<MainActivity.Coord>()
    private val targetSizes = ArrayList<MainActivity.Coord>()
    private lateinit var searchResultsAdapter: ArrayAdapter<String>

    private val requestOverlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (isOverlayPermissionGranted()) {
                startFloatingImageService()
            }
        }

    private val actions = mapOf(
        "카카오톡 문자 보내기" to { val packageName = "com.kakao.talk"
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
            startService(intentB) },
        "카카오톡 영상 통화" to { val packageName = "com.kakao.talk"
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
            startService(intentB) },
        "카카오톡 음성 통화" to { val packageName = "com.kakao.talk"
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
            startService(intentB) },
        "카카오톡 선물 하기" to { val packageName = "com.kakao.talk"
            val url = "https://gift-talk.kakao.com"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)

            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)},
        "카카오톡 프로필 설정하기" to { val packageName = "com.kakao.talk"
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
            startService(intentB) },
        "카카오톡 쇼핑 하기" to { val packageName = "com.kakao.talk"
            val url = "https://store.kakao.com/"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)

            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)},
        "카카오톡 친구 추가" to { val packageName = "com.kakao.talk"
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
            startService(intentB)},

        "네이버 검색 하기" to { val packageName = "com.nhn.android.search"
            val url = "https://www.naver.com"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)},
        "네이버 주식 보기" to { val packageName = "com.nhn.android.search"
            val url = "https://m.stock.naver.com/"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)},
        "네이버 날씨 보기" to { val packageName = "com.nhn.android.search"
            val url = "https://weather.naver.com/"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)},
        "네이버 메일보기,보내기" to { val packageName = "com.nhn.android.search"
            val url = "https://mail.naver.com/v2/folders/0/all"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)},
        "네이버 뉴스보기" to { val intent = Intent(this@SearchActivity, NewsFunctionActivity::class.java)
            startActivity(intent)},
        "네이버 쇼핑 하기" to { val packageName = "com.nhn.android.search"
            val url = "https://shopping.naver.com/home"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startLinkIntent(packageName,url)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)},
        "네이버 지도 보기" to { val packageName = "com.nhn.android.nmap"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startEpisodeIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)},

        "배달의 민족" to { checkImageService()
            val packageName = "com.sampleapp"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startTestIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)},

        "유튜브" to { checkImageService()
            val packageName = "com.google.android.youtube"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startEpisodeIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)},

        "다음" to { checkImageService()
            val packageName = "net.daum.android.mail"
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(FloatingImageService.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent)
            startEpisodeIntent(packageName)
            val intentB = Intent(this, BubbleService::class.java)
            startService(intentB)},






        // ... 다른 버튼들 ...
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seacrh)

        supportActionBar?.apply {
            title = "검색하기" // 툴바 제목
            setDisplayHomeAsUpEnabled(true) // 뒤로 가기 버튼 활성화
        }

        searchResultsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        val searchResultsListView = findViewById<ListView>(R.id.searchResultsListView)
        searchResultsListView.adapter = searchResultsAdapter

        searchResultsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedAction = searchResultsAdapter.getItem(position)
            actions[selectedAction]?.invoke()
        }
        // 검색 EditText 참조 및 검색 기능 구현
        val searchEditText = findViewById<EditText>(R.id.searchEditText)

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = searchEditText.text.toString()
                Toast.makeText(this, "검색어: $query", Toast.LENGTH_SHORT).show()
                search(query)
                true
            } else {
                false
            }
        }
    }
    private fun search(query: String) {
        val matchingResults = actions.keys.filter { it.contains(query, ignoreCase = true) }

        if (matchingResults.isNotEmpty()) {
            searchResultsAdapter.clear()
            searchResultsAdapter.addAll(matchingResults)
            searchResultsAdapter.notifyDataSetChanged()
        } else {
            showToast("검색 결과가 없습니다.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
    private fun checkImageService(){
        if (!isOverlayPermissionGranted()) {
            requestOverlayPermission()
        } else {
            startFloatingImageService()
        }
    }
    private fun startFloatingImageService() {
        val intentF = Intent(this, FloatingImageService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(intentF)
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

    override fun onResume() {
        super.onResume()
        val hideImageIntent = Intent(MainActivity.ACTION_HIDE_IMAGE)
        sendBroadcast(hideImageIntent)
    }

}