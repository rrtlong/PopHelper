package com.moli.module.widget.widget

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import org.jetbrains.anko.dip
import timber.log.Timber

/**
 * @Created SiberiaDante
 * @Describe：
 * @CreateTime: 2017/12/17
 * @UpDateTime:
 * @Email: 2654828081@qq.com
 * @GitHub: https://github.com/SiberiaDante
 */


class JudgeNestedScrollView : NestedScrollView {
    private var isNeedScroll = true
    private var xDistance: Float = 0.toFloat()
    private var yDistance: Float = 0.toFloat()
    private var xLast: Float = 0.toFloat()
    private var yLast: Float = 0.toFloat()
    private var scaledTouchSlop: Int = 0
    private var headHeight = -1

    constructor(context: Context) : super(context, null) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        scaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                yDistance = 0f
                xDistance = yDistance
                xLast = ev.x
                yLast = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val curX = ev.x
                val curY = ev.y
                xDistance += Math.abs(curX - xLast)
                yDistance += Math.abs(curY - yLast)
                xLast = curX
                yLast = curY
                if (headHeight == -1) {
                    isNeedScroll = true
                } else {
                    isNeedScroll = (this.scrollY <= headHeight)
                }
                Log.e(
                    "SiberiaDante",
                    "xDistance ：$xDistance---yDistance:$yDistance isNeedScroll=$isNeedScroll   head=${headHeight}  scrollY = ${this.scrollY}  childHeight=${this.getChildAt(
                        0
                    ).getHeight()}  ${this.height}"
                )
                return (xDistance < yDistance && yDistance > scaledTouchSlop) && isNeedScroll
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    fun setBottomListener(bottomListener: BottomListener) {
        this.bottomListener = bottomListener
        this.setOnScrollChangeListener(OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if ((this.getChildAt(0).height - this.dip(43) - this.height) <= this.scrollY) {
                Timber.e("SiberiaDante bottom")
                bottomListener?.let {
                    it.onBottom()
                }
            }
        })
    }

    /*
    改方法用来处理NestedScrollView是否拦截滑动事件
     */
    fun setNeedScroll(isNeedScroll: Boolean) {
        this.isNeedScroll = isNeedScroll
    }


    private var bottomListener: BottomListener? = null

}

interface BottomListener {
    fun onBottom()
}
