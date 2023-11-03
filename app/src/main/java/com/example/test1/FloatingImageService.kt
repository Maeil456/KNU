package com.example.test1

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.test1.BubbleService.Companion.ACTION_IMAGE_FROM_FIRST
import com.example.test1.MainActivity.Companion.ACTION_HIDE_IMAGE

class FloatingImageService : AccessibilityService() {

    companion object {
        const val ACTION_SHOW_FLOATING_IMAGE = "com.example.test1.SHOW_FLOATING_IMAGE"
    }

    private lateinit var windowManager: WindowManager
    private var floatingImageLayout: View? = null
    private var floatingImageView: ImageView? = null
    private var targetPositions = ArrayList<MainActivity.Coord>()
    private var targetSizes = ArrayList<MainActivity.Coord>()
    private var imageIndex: ArrayList<String> = arrayListOf()
    private var isMoving = false
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
                    intent.getParcelableArrayListExtra("targetPositions", MainActivity.Coord::class.java)
                } else {
                    intent.getParcelableArrayListExtra<MainActivity.Coord>("targetPositions")
                }

                val sizes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableArrayListExtra("targetSizes", MainActivity.Coord::class.java)
                } else {
                    intent.getParcelableArrayListExtra<MainActivity.Coord>("targetSizes")
                }

                val imageposition = intent.getStringArrayListExtra("imageIndex")

                println(positions.toString())
                println(sizes.toString())
                println(imageposition.toString())

                if (positions != null && imageposition != null && sizes != null) {
                    targetPositions = positions
                    imageIndex = imageposition
                    targetSizes = sizes

                    currentPositionIndex = 0

                    if (targetPositions.isNotEmpty()) {
                        showFloatingImage(
                            targetPositions[currentPositionIndex].x,
                            targetPositions[currentPositionIndex].y
                        )

                    }
                }
            }
            if(intent.action == ACTION_IMAGE_FROM_FIRST){
                currentPositionIndex = 0
                hideFloatingImage()
                if(targetPositions.isNotEmpty()){

                    showFloatingImage(
                        targetPositions[currentPositionIndex].x,
                        targetPositions[currentPositionIndex].y
                    )
                }
            }
            if (intent?.action == ACTION_HIDE_IMAGE) {
                hideFloatingImage()
                targetPositions.clear()
                imageIndex.clear()
                targetSizes.clear()
            }
        }
    }

    private val touchListener = View.OnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                currentPositionIndex += 1

                if(currentPositionIndex >= targetPositions.size) {
                    hideFloatingImage()
                    currentPositionIndex = 0
                } else {
                    val targetPosition = targetPositions[currentPositionIndex]
                    val resourceId= resources.getIdentifier(imageIndex[currentPositionIndex], "drawable", packageName)

                    params.x = targetPosition.x
                    params.y = targetPosition.y

                    floatingImageView?.setImageResource(resourceId)

                    floatingImageView?.setWidth(targetSizes[currentPositionIndex].x)
                    floatingImageView?.setHeight(targetSizes[currentPositionIndex].y)

                    windowManager.updateViewLayout(floatingImageLayout, params)
                }
            }
        }
        true
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}



    override fun onCreate() {
        super.onCreate()
        if (!::windowManager.isInitialized) {
            windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
        val filter = IntentFilter().apply {
            addAction(ACTION_SHOW_FLOATING_IMAGE)
            addAction(ACTION_IMAGE_FROM_FIRST)
            addAction(ACTION_HIDE_IMAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(floatingImageReceiver, filter, RECEIVER_EXPORTED)
        }else{
            registerReceiver(floatingImageReceiver, filter)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(floatingImageReceiver)
        hideFloatingImage()
        super.onDestroy()
    }


    override fun onInterrupt() {}

    private fun showFloatingImage(x: Int, y: Int) {
        params.x = x
        params.y = y

        val imageViewId = R.id.floating_image_view
        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        floatingImageLayout = layoutInflater.inflate(R.layout.floating_view, null)
        floatingImageView= floatingImageLayout?.findViewById(imageViewId)

        val resourceId= resources.getIdentifier(imageIndex[currentPositionIndex], "drawable", packageName)
        floatingImageView?.setImageResource(resourceId)

        floatingImageView?.setOnTouchListener(touchListener)

        floatingImageView?.setWidth(targetSizes[currentPositionIndex].x)
        floatingImageView?.setHeight(targetSizes[currentPositionIndex].y)

        windowManager.addView(floatingImageLayout,params)

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
        intentFilter.addAction(ACTION_HIDE_IMAGE)
        intentFilter.addAction(ACTION_IMAGE_FROM_FIRST)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(floatingImageReceiver, intentFilter, RECEIVER_EXPORTED)
        } else{
            registerReceiver(floatingImageReceiver, intentFilter)
        }

        return START_STICKY
    }

    private fun View.setHeight(value: Int) {
        val lp = layoutParams
        lp?.let {
            lp.height = value
            layoutParams = lp
        }
    }
    private fun View.setWidth(value: Int) {
        val lp = layoutParams
        lp?.let {
            lp.width = value
            layoutParams = lp
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}