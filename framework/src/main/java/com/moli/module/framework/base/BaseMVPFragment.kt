package com.moli.module.framework.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.alibaba.android.arouter.launcher.ARouter
import com.moli.module.framework.mvp.IPresenter

import org.simple.eventbus.EventBus

/**
 * 项目名称：heixiu
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：2017/3/21 15:54
 * 修改人：LiuJun
 * 修改时间：2017/3/21 15:54
 * 修改备注：
 */
abstract class BaseMVPFragment<P : IPresenter> : Fragment() {
    protected var rootView: View? = null
    protected var presenter: P? = null
    private var userVisible: Boolean = false //Fragment是否能被用户看见

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        presenter = createPresenter()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = initView(inflater, container)
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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


    override fun onResume() {
        super.onResume()
        userVisible = checkResumeVisible()
        onVisibleChanged(userVisible)
    }


    override fun onPause() {
        super.onPause()
        userVisible = false
        onVisibleChanged(false)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        userVisible = false
        onVisibleChanged(false)
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        this.rootView = null
    }


    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.onDestroy() //释放资源
            presenter = null
        }
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        userVisible = isVisibleToUser && isResumed
        onVisibleChanged(userVisible)
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        userVisible = !hidden
        onVisibleChanged(userVisible)
    }


    /**
     * 当前 Fragment 是否能被用户看见
     *
     * 1、在Activity使用XML引入，或者使用 FragmentManager 的 addFragment 或者 replaceFragment 动态载入，只要监听Fragment的onResume和onPause方法就能够判断其显隐
     *
     * 2、使用 show 和 hide 来显隐的 Fragment，监听 onHiddenChanged 方法
     *
     * 3、在 ViewPager 中的 Fragment，监听 setUserVisibleHint 来判断到底对用户是否可见
     *
     * 注意：Fragment 不可见的信号会被多次发送
     *
     * @param isVisibleToUser true if this fragment's UI is currently visible to the user
     * (default),false if it is not.
     */
    open fun onVisibleChanged(isVisibleToUser: Boolean) {}


    /**
     * 是否使用eventBus,默认为不使用(false)，
     */
    protected open fun useEventBus(): Boolean {
        return false
    }


    protected abstract fun initView(inflater: LayoutInflater, container: ViewGroup?): View?

    protected abstract fun initData()

    protected abstract fun createPresenter(): P?


    /**
     * 在[.onResume]中调用，判断当前Fragment是否显示。
     * 主要是Fragment在ViewPager中的时候，判断ViewPager当前显示的Fragment是不是自己
     * 如果没有在ViewPager中使用，默认为显示
     *
     * @return true 显示，false 不显示
     */
    protected open fun checkResumeVisible(): Boolean {
        return true
    }


    fun getUserVisible(): Boolean {
        return userVisible
    }

}
