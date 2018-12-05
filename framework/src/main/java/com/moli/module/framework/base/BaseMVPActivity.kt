package com.moli.module.framework.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.alibaba.android.arouter.launcher.ARouter
import com.moli.module.framework.R
import com.moli.module.framework.analytics.AnalyticsAgent
import com.moli.module.framework.mvp.IPresenter
import com.moli.module.framework.utils.StatusBarUtils
import com.moli.module.framework.utils.isFullScreen
import org.simple.eventbus.EventBus
import android.view.View
import android.widget.EditText
import com.blankj.utilcode.util.KeyboardUtils


/**
 * 项目名称：heixiu
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：2017/2/28 15:03
 * 修改人：LiuJun
 * 修改时间：2017/2/28 15:03
 * 修改备注：
 */
abstract class BaseMVPActivity<P : IPresenter> : AppCompatActivity() {

    protected var presenter: P? = null
    protected var isContentToStatusBar = false //是否将内容显示到状态栏
    open protected var isDispatchTouchEvent=true

    @get:LayoutRes
    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        presenter = createPresenter()
        EventBus.getDefault().register(this) //注册到事件主线
        if (layoutResId > 0) {
            setContentView(layoutResId)
        }
        initData(savedInstanceState)
        initStatusBar()
    }

    override fun onResume() {
        super.onResume()
        AnalyticsAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        AnalyticsAgent.onPause(this)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            super.onRestoreInstanceState(savedInstanceState)
        }
        if (presenter == null) {
            presenter = createPresenter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        if (presenter != null) {
            presenter!!.onDestroy() //释放资源
            presenter = null
        }
    }


    protected open fun initStatusBar() {
        if (isContentToStatusBar) {
            return
        }
        if (isFullScreen() == true) {
            return
        }
        val mode = StatusBarUtils.setStatusBarDarkMode(this)
        if (mode) {
            StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark), 0)
        } else {
            StatusBarUtils.setTranslucent(this)
        }
    }

    /**
     * 设置透明状态栏
     */
    protected fun setTranslucentForStatusBar() {
        if (isFullScreen() == true) {
            return
        }
        val mode = StatusBarUtils.setStatusBarLightMode(this)
        if (mode) {
            StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark), 0)
        } else {
            StatusBarUtils.setTranslucent(this)
        }
    }

    protected abstract fun initData(savedInstanceState: Bundle?)

    protected abstract fun createPresenter(): P?

    companion object {
        const val ACTIVITY_LOGOUT = "all_activity_logout"
    }


    open override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if(!isDispatchTouchEvent){
            return super.dispatchTouchEvent(ev)
        }
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {
                hideSoftInout(v)
            }
            return super.dispatchTouchEvent(ev)
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return if (window.superDispatchTouchEvent(ev)) {
            true
        } else onTouchEvent(ev)
    }


    open fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()

            val eventX=event.x
            val eventY=event.y
            val a = (eventX > left && eventX < right
                    && eventY > top && eventY < bottom)
            return !a
        }
        return false
    }


    open fun hideSoftInout(view: View) {
        KeyboardUtils.hideSoftInput(this)
    }
}
