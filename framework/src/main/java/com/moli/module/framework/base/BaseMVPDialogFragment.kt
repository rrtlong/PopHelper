package com.moli.module.framework.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.ColorRes
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.moli.module.framework.R
import com.moli.module.framework.mvp.IPresenter
import com.moli.module.framework.utils.StatusBarUtils
import com.moli.module.framework.utils.isFullScreen
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.support.v4.ctx
import org.simple.eventbus.EventBus
import java.util.concurrent.TimeUnit

/**
 * 项目名称：heixiu
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：2017/3/21 15:54
 * 修改人：LiuJun
 * 修改时间：2017/3/21 15:54
 * 修改备注：
 */
abstract class BaseMVPDialogFragment<P : IPresenter> : DialogFragment() {
    protected var rootView: View? = null
    protected var presenter: P? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        presenter = createPresenter()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        this.rootView = null
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.onDestroy() //释放资源
            presenter = null
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = initView(inflater, container)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initData()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (presenter == null) {
            presenter = createPresenter()
        }
    }

    /**
     * 几秒钟后自动关闭
     *
     * @param seconds 延迟时间，单位：秒
     */
    fun autoClose(seconds: Int) {
        Observable.timer(seconds.toLong(), TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(this)
                .subscribe { dismissAllowingStateLoss() }
    }

    /**
     * 沉浸式状态栏，需要的话在initData中调用
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param view 需要向下偏移的 View
     */
    protected fun initImgStatusBar(view: View?) {
        val window = dialog?.window ?: return
        if (!window.isFullScreen()) {
            StatusBarUtils.setTransparentForImageView(window, view)
        }
    }

    /**
     * 沉浸式状态栏，需要的话在initData中调用
     */
    protected fun initStatusBar() {
        val window = dialog?.window ?: return
        if (!window.isFullScreen()) {
            val mode = StatusBarUtils.setStatusBarLightMode(window)
            if (mode) {
                StatusBarUtils.setColor(this, window, ContextCompat.getColor(ctx, R.color.colorPrimaryDark), 0)
            } else {
                StatusBarUtils.setTranslucent(this, window)
            }

        }
    }

    /**
     * 沉浸式状态栏，需要的话在initData中调用
     */
    protected fun initStatusBar(@ColorRes color: Int) {
        val window = dialog?.window ?: return
        if (!window.isFullScreen()) {
            StatusBarUtils.setColor(this, window, ContextCompat.getColor(activity!!, color), 0)
        }
    }

    /**
     * 是否使用eventBus,默认为不使用(false)，
     */
    protected open fun useEventBus(): Boolean {
        return false
    }

    protected abstract fun initView(inflater: LayoutInflater, container: ViewGroup?): View?

    protected abstract fun initData()

    protected abstract fun createPresenter(): P?
}
