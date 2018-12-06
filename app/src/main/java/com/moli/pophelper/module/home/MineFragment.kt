package com.moli.pophelper.module.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.base.BaseMVPFragment
import com.moli.module.framework.mvp.IView
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 18:43
 * 修改人：lijilong
 * 修改时间：2018/12/4 18:43
 * 修改备注：
 * @version
 */
@Route(path = HelperArouter.Fragment.MineFragment.PATH)
class MineFragment : BaseMVPFragment<MineFragmentPresenter>(), IView {
    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun initData() {

    }

    override fun createPresenter(): MineFragmentPresenter? {
        return MineFragmentPresenter(this)
    }

}
