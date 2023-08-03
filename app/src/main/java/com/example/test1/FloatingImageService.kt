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

class FloatingImageService : AccessibilityService() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View
    private var isShowing = false

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Check if the desired app is launched
        if (event?.packageName == "com.kakao.talk") {
            // Show the floating image when the desired app is launched
            showFloatingImage(100,200)
        }
    }



    override fun onInterrupt() {
        // Handle service interruption
    }

    public fun showFloatingImage(x: Int, y: Int) {
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
        windowManager.addView(floatingView, params)
        isShowing = true
    }

    private fun hideFloatingImage() {
        if (!isShowing) return
        windowManager.removeView(floatingView)
        isShowing = false
    }

    // Add other methods as needed, e.g., to update the position of the floating image based on touch events
}