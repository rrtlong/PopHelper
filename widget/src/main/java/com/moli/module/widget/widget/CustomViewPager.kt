package com.moli.module.widget.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by 123 on 2017/5/12.
 */

class CustomViewPager : ViewPager {

    var isScrollable: Boolean = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isScrollable && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return isScrollable && super.onTouchEvent(ev)

    }
}

