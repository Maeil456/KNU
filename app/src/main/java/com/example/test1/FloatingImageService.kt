package com.example.test1

import android.accessibilityservice.AccessibilityService
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.ImageView

class FloatingImageService : AccessibilityService() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingImage: ImageView

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val params = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        } else {
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        }
        params.gravity = Gravity.TOP or Gravity.START
        params.x = 100
        params.y = 200
        floatingImage = LayoutInflater.from(this).inflate(R.layout.floating_image_layout, null) as ImageView
        windowManager.addView(floatingImage, params)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // You can handle the accessibility events here to detect when the target app is launched
        // and show/hide the floating image accordingly.
        // For example, you can check the package name of the event and display the floating image
        // only when the desired app (e.g., KakaoTalk) is launched.
    }

    override fun onInterrupt() {
        // Handle service interruption
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the floating image view when the service is destroyed
        windowManager.removeView(floatingImage)
    }
}
