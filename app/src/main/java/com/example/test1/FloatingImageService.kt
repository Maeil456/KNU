package com.example.test1

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.widget.ImageView
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

import kotlin.collections.ArrayList

class FloatingImageService : AccessibilityService() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingImageLayout: View
    private lateinit var floatingImageView: ImageView
    private lateinit var targetPositions: ArrayList<Pair<Int, Int>>
    private var currentPositionIndex = 0
    private var isShowing = false

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
        targetPositions = arrayListOf()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideFloatingImage()
    }

    override fun onInterrupt() {
        // Handle service interruption
    }

    private fun showFloatingImage(x: Int, y: Int) {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val params = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
        }
        params.gravity = Gravity.TOP or Gravity.START
        params.x = x
        params.y = y

        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        floatingImageLayout = layoutInflater.inflate(R.layout.floating_image_layout, null)
        floatingImageView = floatingImageLayout.findViewById(R.id.floating_image_view)

        floatingImageView.setOnTouchListener(object : View.OnTouchListener {
            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0f
            private var initialTouchY: Float = 0f

            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                    }
                    MotionEvent.ACTION_UP -> {
                        if (currentPositionIndex < targetPositions.size) {
                            val targetPosition = targetPositions[currentPositionIndex]
                            params.x = targetPosition.first
                            params.y = targetPosition.second
                            windowManager.updateViewLayout(floatingImageLayout, params)

                            currentPositionIndex++
                        } else {
                            onDestroy()
                        }
                    }
                }
                return false
            }
        })

        windowManager.addView(floatingImageLayout, params)
    }

    private fun hideFloatingImage() {
        windowManager.removeView(floatingImageLayout)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_STICKY
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


