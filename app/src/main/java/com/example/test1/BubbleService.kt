package com.example.test1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.test1.MainActivity.Companion.ACTION_HIDE_IMAGE
import com.torrydo.floatingbubbleview.CloseBubbleBehavior
import com.torrydo.floatingbubbleview.FloatingBubbleListener
import com.torrydo.floatingbubbleview.helper.ViewHelper
import com.torrydo.floatingbubbleview.service.expandable.BubbleBuilder
import com.torrydo.floatingbubbleview.service.expandable.ExpandableBubbleService
import com.torrydo.floatingbubbleview.service.expandable.ExpandedBubbleBuilder


class BubbleService : ExpandableBubbleService() {

    private var isDrag:Boolean = false
    private var downTime: Long = 0
    companion object {
        const val ACTION_IMAGE_FROM_FIRST = "com.example.test1.IMAGE_FROM_FIRST"
    }
    override fun  configBubble(): BubbleBuilder?{

        // You can create your own view manually, or using this helper class that I specially designed for you 💖
        val imgView = ViewHelper.fromDrawable(this, R.drawable.your_bubble_icon, 60, 60)


        imgView.setOnClickListener{
            expand()
        }



        return BubbleBuilder(this)

            .bubbleView(imgView)

            .startLocation(0,500)

        //.closeBehavior(CloseBubbleBehavior.DYNAMIC_CLOSE_BUBBLE)

        //.closeBubbleView(ViewHelper.fromDrawable(this, R.drawable.xicon, 3, 3))


    }

    override fun configExpandedBubble(): ExpandedBubbleBuilder? {
        //val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //val layout = inflater.inflate(R.layout.layout_bubble,null)
        val expandedView = LayoutInflater.from(this).inflate(R.layout.layout_bubble, null)

        expandedView.findViewById<View>(R.id.btnGoToMain).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            minimize()
            startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK))
            val intentB = Intent(this, BubbleService::class.java)
            stopService(intentB)
        }

        expandedView.findViewById<View>(R.id.btnHelpImage).setOnClickListener {
            val intent = Intent(ACTION_IMAGE_FROM_FIRST)
            sendBroadcast(intent)
            minimize()
        }

        expandedView.findViewById<View>(R.id.exit).setOnClickListener {
            stopSelf()
        }

        expandedView.findViewById<View>(R.id.backview).setOnClickListener {
            minimize()
        }

        /*
        layout.findViewById<Button>(R.id.btnGoToMain).setOnClickListener {bubbleView:View?->
            val intent = Intent(this, MainActivity::class.java)
            action.popToBubble()
            startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK))
            val intentB = Intent(this, BubbleService::class.java)
            stopService(intentB)
        }

        layout.findViewById<Button>(R.id.btnHelpImage).setOnClickListener {bubbleView:View?->
            val intent = Intent(ACTION_IMAGE_FROM_FIRST)
            sendBroadcast(intent)
            action.popToBubble()
        }
        layout.findViewById<View>(R.id.backview).setOnClickListener {bubbleView:View?->
            action.popToBubble()
        }

        */
        return ExpandedBubbleBuilder(this)

            .expandedView(expandedView)

            .dimAmount(0.8f)

            .startLocation(20,20)

    }
    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter().apply {
            addAction(ACTION_HIDE_IMAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(hideBubbleReceiver, filter, RECEIVER_EXPORTED)
        }else {
            registerReceiver(hideBubbleReceiver, filter)
        }
        minimize()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(hideBubbleReceiver)
    }
    private val hideBubbleReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_HIDE_IMAGE) {
                stopSelf()
            }
        }
    }
}