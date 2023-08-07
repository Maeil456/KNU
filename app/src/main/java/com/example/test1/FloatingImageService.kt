package com.example.test1

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.ImageView
import android.content.Intent

class FloatingImageService : AccessibilityService() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View
    private lateinit var targetPositions: ArrayList<Pair<Int, Int>>
    private var currentPositionIndex = 0
    private var isShowing = false

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Check if the desired app is launched
        if (event?.packageName == "com.kakao.talk" ||  event?.packageName == "com.nhn.android.search") {
            // Show the floating image when the desired app is launched
            showFloatingImage(100,200)
        } else {
            hideFloatingImage()
        }
    }

    override fun onCreate() {
        super.onCreate()
        showFloatingImage(100, 200)
    }

    override fun onDestroy() {
        super.onDestroy()
        hideFloatingImage()
    }

    override fun onInterrupt() {
        // Handle service interruption
    }

    private fun showFloatingImage(x: Int, y: Int) {
        if (isShowing) return

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

        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_image_layout, null)

        // Add a touch listener to hide the floating image when it's touched
        floatingView.setOnClickListener {
            hideFloatingImage()
        }

        windowManager.addView(floatingView, params)
        isShowing = true
    }

    private fun hideFloatingImage() {
        if (!isShowing) return
        windowManager.removeView(floatingView)
        isShowing = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
}