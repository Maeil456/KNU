package com.example.test1

import android.accessibilityservice.AccessibilityService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.os.Parcelable
import android.view.*
import android.widget.ImageView
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.Toast
import com.example.test1.MainActivity.Companion.ACTION_SHOW_FLOATING_IMAGE
import kotlin.collections.ArrayList
import kotlinx.android.parcel.Parcelize

class FloatingImageService : AccessibilityService() {

    private lateinit var windowManager: WindowManager
    private var floatingImageLayout: View? = null
    private var floatingImageView: ImageView? = null
    private lateinit var bubbleView: View
    private var targetPositions = ArrayList<MainActivity.Coord>()
    private var isMoving = false
    private var currentPositionIndex = 0
    private var isShowing = false
    private lateinit var btnGoToApp: Button

    private var initX = 0
    private var initY = 0
    private val touchSlopSquare = 100 * 100
    private val params = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
    } else {
        WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
    }.apply {
        gravity = Gravity.TOP or Gravity.START
    }

    private val floatingImageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ACTION_SHOW_FLOATING_IMAGE) {
                val positions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableArrayListExtra("targetPositions", MainActivity.Coord::class.java)
                } else {
                    intent.getParcelableArrayListExtra<MainActivity.Coord>("targetPositions")
                }
                println(positions.toString())
                if (positions != null) {
                    targetPositions = positions as ArrayList<MainActivity.Coord>
                    currentPositionIndex = 0

                    if (targetPositions.isNotEmpty()) {
                        showFloatingImage(
                            targetPositions[currentPositionIndex].x,
                            targetPositions[currentPositionIndex].y
                        )

                    }
                }
            }
        }
    }

    private val touchListener = View.OnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isMoving = false
                initX = event.rawX.toInt()
                initY = event.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                if (!isMoving) {
                    val dX = event.rawX.toInt() - initX
                    val dY = event.rawY.toInt() - initY
                    if (dX * dX + dY * dY > touchSlopSquare) {
                        isMoving = true
                    }
                }
                if (isMoving) {
                    val x = event.rawX.toInt() - initX + params.x
                    val y = event.rawY.toInt() - initY + params.y
                    params.x = x
                    params.y = y
                    windowManager.updateViewLayout(floatingImageLayout, params)
                }
            }
            MotionEvent.ACTION_UP -> {
                btnGoToApp.visibility = View.VISIBLE
                if (!isMoving) {
                    currentPositionIndex += 1

                    if(currentPositionIndex >= targetPositions.size) {
                        hideFloatingImage()
                        currentPositionIndex = 0
                    } else {
                        val targetPosition = targetPositions[currentPositionIndex]
                        params.x = targetPosition.x
                        params.y = targetPosition.y
                        windowManager.updateViewLayout(floatingImageLayout, params)
                    }
                }

            }
        }
        true
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.packageName == "com.kakao.talk" || event?.packageName == "com.nhn.android.search") {
            if (!isShowing) {
                showFloatingImage(100, 200)
                showToast("Good!")
                isShowing = true
            }
        } else {
            if (isShowing) {
                hideFloatingImage()
                isShowing = false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (!::windowManager.isInitialized) {
            windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        }
        val filter = IntentFilter().apply {
            addAction(ACTION_SHOW_FLOATING_IMAGE)
        }
        registerReceiver(floatingImageReceiver, filter)

        bubbleView = LayoutInflater.from(this).inflate(R.layout.layout_bubble, null)
        btnGoToApp = bubbleView.findViewById(R.id.btnGoToMain)
        val bubbleParams = createLayoutParams()
        windowManager.addView(bubbleView, bubbleParams)
    }
    private fun createLayoutParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
    }

    override fun onDestroy() {
        windowManager.removeView(bubbleView)
        unregisterReceiver(floatingImageReceiver)
        hideFloatingImage()
        super.onDestroy()
    }

    override fun onInterrupt() {}

    private fun showFloatingImage(x: Int, y: Int) {
        //windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        params.x = x
        params.y = y

        val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        floatingImageLayout = layoutInflater.inflate(R.layout.floating_image_layout, null)
        floatingImageView = floatingImageLayout?.findViewById(R.id.floating_image_view)

        floatingImageLayout?.setOnTouchListener(touchListener)

        floatingImageLayout?.let { windowManager.addView(it, params) }


        btnGoToApp.visibility = View.GONE

        btnGoToApp.setOnClickListener {
            val intent = Intent(this@FloatingImageService, MainActivity::class.java) // 여기서 MainActivity는 앱의 메인 화면 클래스 이름입니다.
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


    private fun hideFloatingImage() {
        floatingImageLayout?.let {
            windowManager.removeView(it)
            floatingImageLayout = null
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_SHOW_FLOATING_IMAGE)
        registerReceiver(floatingImageReceiver, intentFilter)


        return START_STICKY
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showFloatingArray(arrayList: ArrayList<Pair<Int, Int>>) {
        for ((x, y) in arrayList) {
            showFloatingImage(x, y)
        }
    }
}
