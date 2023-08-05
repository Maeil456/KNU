package com.example.test1

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.ImageView

class FloatingImageService : AccessibilityService() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View
    private lateinit var imageView: ImageView
    private var isShowing = false

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Check if the desired app is launched
        if (event?.packageName == "com.kakao.talk" || event?.packageName == "com.nhn.android.search") {
            // Show the floating image when the desired app is launched
            showFloatingImage(100, 200)
        } else {
            // Hide the floating image when the desired app is closed or goes to the background
            hideFloatingImage()
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

        // Add touch listener to handle dragging and dismissing the floating image
        imageView.setOnTouchListener(object : View.OnTouchListener {
            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0f
            private var initialTouchY: Float = 0f

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager.updateViewLayout(floatingView, params)
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        // Implement dismissing logic here if needed
                        return true
                    }
                }
                return false
            }
        })

        windowManager.addView(floatingView, params)
        isShowing = true
    }

    private fun hideFloatingImage() {
        if (!isShowing) return
        windowManager.removeView(floatingView)
        isShowing = false
    }

    // Add other methods as needed
}