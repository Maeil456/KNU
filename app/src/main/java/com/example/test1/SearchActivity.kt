package com.example.test1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {

    private var imageIndex: ArrayList<String> = arrayListOf()
    private val targetPositions = ArrayList<MainActivity.Coord>()
    private val targetSizes = ArrayList<MainActivity.Coord>()
    private lateinit var searchResultsAdapter: ArrayAdapter<String>

    private val actions = mapOf(
        "문자 보내기" to { val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent) },
        "영상 통화" to { val packageName = "com.kakao.talk"
            startEpisodeIntent(packageName)
            setArray("Naver_search")
            setArray2("Naver_search_imageSize")
            setImage("Naver_search_image")

            val intent = Intent(MainActivity.ACTION_SHOW_FLOATING_IMAGE)
            intent.putParcelableArrayListExtra("targetPositions", targetPositions)
            intent.putParcelableArrayListExtra("targetSizes", targetSizes)
            intent.putStringArrayListExtra("imageIndex",imageIndex)
            sendBroadcast(intent) },


        // ... 다른 버튼들 ...
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seacrh)

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

    override fun onResume() {
        super.onResume()
        val hideImageIntent = Intent(FloatingImageService.ACTION_HIDE_IMAGE)
        sendBroadcast(hideImageIntent)
    }

}