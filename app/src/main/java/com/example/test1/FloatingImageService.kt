package com.example.test1

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.widget.ImageView
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.Toast
import kotlin.collections.ArrayList

class FloatingImageService : AccessibilityService() {
    private lateinit var windowManager: WindowManager
    private lateinit var floatingImageLayout: View
    private lateinit var floatingImageView: ImageView
    private lateinit var bubbleView: View
    private lateinit var targetPositions: ArrayList<Pair<Int, Int>>
    private var currentPositionIndex = 0
    private var isShowing = false
    private lateinit var btnGoToApp: Button  // 앱 메인 화면으로 돌아가기 버튼
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

        // 공통 WindowManager 초기화
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // FloatingImageService 초기화
        targetPositions = arrayListOf()

        // BubbleService 초기화
        bubbleView = LayoutInflater.from(this).inflate(R.layout.layout_bubble, null)
        val bubbleParams = createLayoutParams()
        windowManager.addView(bubbleView, bubbleParams)
    }
    override fun onDestroy() {
        super.onDestroy()
        hideFloatingImage()
        windowManager.removeView(bubbleView)
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
    override fun onInterrupt() {
        // Handle service interruption if needed
    }
    private fun showFloatingImage(x: Int, y: Int) {
        val localWindowManager = windowManager ?: return

        val params = createLayoutParams()
        params.gravity = Gravity.TOP or Gravity.START
        params.x = x
        params.y = y

        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        floatingImageLayout = layoutInflater.inflate(R.layout.floating_image_layout, null)
        floatingImageView = floatingImageLayout.findViewById(R.id.floating_image_view)
        floatingImageView.setImageResource(R.drawable.pin)
        floatingImageView = floatingImageLayout.findViewById(R.id.floating_image_view)
        btnGoToApp = floatingImageLayout.findViewById(R.id.btnGoToMain)

        floatingImageView.visibility = View.VISIBLE
        btnGoToApp.visibility = View.GONE

        btnGoToApp.setOnClickListener {
            val intent = Intent(this@FloatingImageService, MainActivity::class.java) // 여기서 MainActivity는 앱의 메인 화면 클래스 이름입니다.
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

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
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val offsetX = event.rawX - initialTouchX
                        val offsetY = event.rawY - initialTouchY
                        params.x = initialX + offsetX.toInt()
                        params.y = initialY + offsetY.toInt()
                        windowManager.updateViewLayout(floatingImageLayout, params)
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        btnGoToApp.visibility = View.VISIBLE
                        if (currentPositionIndex < targetPositions.size) {
                            val targetPosition = targetPositions[currentPositionIndex]
                            params.x = targetPosition.first
                            params.y = targetPosition.second
                            windowManager.updateViewLayout(floatingImageLayout, params)

                            currentPositionIndex++
                        } else {
                            stopSelf()
                        }
                        return true
                    }
                }
                return false
            }
        })

        windowManager.addView(floatingImageLayout, params)
    }


    private fun hideFloatingImage() {
        if(::floatingImageLayout.isInitialized) {
            windowManager.removeView(floatingImageLayout)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
