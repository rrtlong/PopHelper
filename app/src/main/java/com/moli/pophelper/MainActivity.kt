package com.moli.pophelper

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.IView
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.module.home.MainActivityPresenter
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

@Route(path = HelperArouter.Activity.MainActivity.PATH)
class MainActivity : BaseMVPActivity<MainActivityPresenter>(), IView {
    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        QMUIStatusBarHelper.translucent(this)
    }

    override fun createPresenter(): MainActivityPresenter? {
        return MainActivityPresenter(this)
    }

}
