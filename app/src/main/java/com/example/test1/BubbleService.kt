package com.example.test1

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.getSystemService
import com.torrydo.floatingbubbleview.ExpandableView
import com.torrydo.floatingbubbleview.FloatingBubble
import com.torrydo.floatingbubbleview.FloatingBubbleService
import com.torrydo.floatingbubbleview.viewx.ViewHelper



class BubbleService : FloatingBubbleService() {

    companion object {
        const val ACTION_IMAGE_FROM_FIRST = "com.example.test1.IMAGE_FROM_FIRST"
    }
    override fun setupBubble(action: FloatingBubble.Action): FloatingBubble.Builder {

        // You can create your own view manually, or using this helper class that I specially designed for you 💖
        val bubbleView = ViewHelper.fromDrawable(this, R.drawable.your_bubble_icon, 60, 60)


        bubbleView.setOnClickListener{
            action.navigateToExpandableView()
        }



        return FloatingBubble.Builder(this)

            .bubble(bubbleView)

            .startLocation(50,700)


    }

    override fun setupExpandableView(action: ExpandableView.Action): ExpandableView.Builder? {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.layout_bubble,null)

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

        layout.findViewById<ImageView>(R.id.exit).setOnClickListener {bubbleView:View?->
            action.popToBubble()
        }

        layout.findViewById<View>(R.id.backview).setOnClickListener {bubbleView:View?->
            action.popToBubble()
        }

        return ExpandableView.Builder(this)

            .view(layout)

    }
}