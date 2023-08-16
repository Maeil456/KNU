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
import android.widget.Toast
import com.example.test1.MainActivity.Companion.ACTION_SHOW_FLOATING_IMAGE
import kotlinx.android.parcel.Parcelize

class FloatingImageService : AccessibilityService() {

    @Parcelize
    data class Coord(val x: Int, val y: Int) : Parcelable

    private lateinit var windowManager: WindowManager
    private lateinit var floatingImageLayout: View
    private lateinit var floatingImageView: ImageView
    private lateinit var targetPositions: ArrayList<MainActivity.Coord>
    private var currentPositionIndex = 0
    private var isShowing = false

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
                    intent.getParcelableArrayListExtra<Coord>("targetPositions", Coord::class.java)
                } else {
                    intent.getParcelableArrayListExtra<Coord>("targetPositions")
                }
                if (positions != null) {
                    targetPositions = positions as ArrayList<MainActivity.Coord>
                    currentPositionIndex = 0

                    if (targetPositions.isNotEmpty()) {
                        showFloatingImage(
                            targetPositions[currentPositionIndex].x,
                            targetPositions[currentPositionIndex].y
                        )
                        currentPositionIndex = (currentPositionIndex + 1) % targetPositions.size
                    }
                }
            }
        }
    }


    private var isMoving = false
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
                if (!isMoving) {
                    currentPositionIndex = (currentPositionIndex + 1) % targetPositions.size
                    val targetPosition = targetPositions[currentPositionIndex]
                    params.x = targetPosition.x
                    params.y = targetPosition.y
                    windowManager.updateViewLayout(floatingImageLayout, params)
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
            windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
    }

    override fun onDestroy() {
        unregisterReceiver(floatingImageReceiver)
        super.onDestroy()
        hideFloatingImage()
    }

    override fun onInterrupt() {}

    private fun showFloatingImage(x: Int, y: Int) {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        params.x = x
        params.y = y

        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        floatingImageLayout = layoutInflater.inflate(R.layout.floating_image_layout, null)
        floatingImageView = floatingImageLayout.findViewById(R.id.floating_image_view)

        floatingImageLayout.setOnTouchListener(touchListener)

        windowManager.addView(floatingImageLayout, params)
    }

    private fun hideFloatingImage() {
        if (::windowManager.isInitialized) {
            windowManager.removeView(floatingImageLayout)
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