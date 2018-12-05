package com.moli.module.widget.widget.dialog.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import com.blankj.utilcode.util.ScreenUtils
import com.qmuiteam.qmui.QMUILog

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/9/17 15:26
 * 修改人：lijilong
 * 修改时间：2018/9/17 15:26
 * 修改备注：
 * @version
 */
open class BottomDialog(context: Context, styleId: Int) : Dialog(context, styleId) {
    private var mContentView: View? = null
    private var mIsAnimating = false
    private var mOnBottomShowListener: OnBottomShowListener? = null

    fun setOnBottomShowListener(onBottomListener: OnBottomShowListener) {
        this.mOnBottomShowListener = onBottomListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window!!.decorView.setPadding(0, 0, 0, 0)
        val params = this.window!!.attributes
        params.height = -2
        params.gravity = 81
        val screenWidth = ScreenUtils.getScreenWidth()
        val screenHeight = ScreenUtils.getScreenHeight()
        params.width = if (screenWidth < screenHeight) screenWidth else screenHeight
        this.window!!.attributes = params
        this.setCanceledOnTouchOutside(true)
    }

    override fun setContentView(layoutResID: Int) {
        this.mContentView = LayoutInflater.from(this.context).inflate(layoutResID, null as ViewGroup?)
        super.setContentView(this.mContentView!!)
    }

    override fun setContentView(view: View, params: android.view.ViewGroup.LayoutParams?) {
        this.mContentView = view
        super.setContentView(view, params)
    }

    fun getContentView(): View? {
        return this.mContentView
    }

    override fun setContentView(view: View) {
        this.mContentView = view
        super.setContentView(view)
    }

    private fun animateUp() {
        if (this.mContentView != null) {
            val translate = TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f)
            val alpha = AlphaAnimation(0.0f, 1.0f)
            val set = AnimationSet(true)
            set.addAnimation(translate)
            set.addAnimation(alpha)
            set.interpolator = DecelerateInterpolator()
            set.duration = 200L
            set.fillAfter = true
            this.mContentView!!.startAnimation(set)
        }
    }

    private fun animateDown() {
        if (this.mContentView != null) {
            val translate = TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f)
            val alpha = AlphaAnimation(1.0f, 0.0f)
            val set = AnimationSet(true)
            set.addAnimation(translate)
            set.addAnimation(alpha)
            set.interpolator = DecelerateInterpolator()
            set.duration = 200L
            set.fillAfter = true
            set.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    this@BottomDialog.mIsAnimating = true
                }

                override fun onAnimationEnd(animation: Animation) {
                    this@BottomDialog.mIsAnimating = false
                    this@BottomDialog.mContentView!!.post {
                        try {
                            super@BottomDialog.dismiss()
                        } catch (var2: Exception) {
                            QMUILog.w("BottomDialog", "dismiss error\n" + Log.getStackTraceString(var2), *arrayOfNulls(0))
                        }
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            this.mContentView!!.startAnimation(set)
        }
    }

    override fun show() {
        super.show()
        this.animateUp()
        if (this.mOnBottomShowListener != null) {
            this.mOnBottomShowListener!!.onShow()
        }

    }

    override fun dismiss() {
        if (!this.mIsAnimating) {
            this.animateDown()
        }
    }


    interface OnBottomShowListener {
        fun onShow()
    }

    companion object {
        private val TAG = "BottomDialog"
        private val mAnimationDuration = 200
    }
}
