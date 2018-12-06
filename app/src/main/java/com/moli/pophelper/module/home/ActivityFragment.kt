package com.moli.pophelper.module.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.base.BaseMVPFragment
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.utils.LayoutManagerUtil
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import kotlinx.android.synthetic.main.fragment_activity.*
import org.jetbrains.anko.support.v4.ctx

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
@Route(path = HelperArouter.Fragment.ActFragment.PATH)
class ActivityFragment : BaseMVPFragment<ActivityFragmentPresenter>(), IListView {
    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun initData() {

    }

    override fun createPresenter(): ActivityFragmentPresenter? {
        return ActivityFragmentPresenter(this)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LayoutManagerUtil.getLinearLayoutManager(ctx, true, false)
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView
    }


}
