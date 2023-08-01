package com.example.test1
// FloatingImage.kt
import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

class FloatingImage(private val context: Context) {
    private val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val floatingView: View =
        View.inflate(context, R.layout.floating_image_layout, null)
    private val layoutParams: WindowManager.LayoutParams

    init {
        // Configure the floating image view
        // Set layoutParams properties as per your requirements (size, position, flags, etc.)
        layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        layoutParams.gravity = Gravity.TOP or Gravity.START

        // Set an onTouchListener to handle touch events on the floating image view
        floatingView.setOnTouchListener(FloatingTouchListener())
    }

    fun show(x: Int, y: Int) {
        // Set the position for the floating view
        layoutParams.x = x
        layoutParams.y = y
        windowManager.addView(floatingView, layoutParams)
    }

    private inner class FloatingTouchListener : View.OnTouchListener {
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    // Remove the floating view when touched
                    windowManager.removeView(floatingView)
                    return true
                }
            }
            return false
        }
    }
}
