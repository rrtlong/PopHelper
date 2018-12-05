package com.moli.module.framework.mvp

import `in`.srain.cube.views.ptr.PtrClassicFrameLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * 项目名称：Jasmine
 * 类描述：
 * 创建人：liujun
 * 创建时间：2018/1/10 19:46
 * 修改人：liujun
 * 修改时间：2018/1/10 19:46
 * 修改备注：
 * @version
 */
interface IListView : IView {

    fun getLayoutManager(): LayoutManager

    fun getRecyclerView(): RecyclerView

    fun getRefreshLayout(): PtrClassicFrameLayout? {
        return null
    }

    fun getLoadingView(): View? {
        return null
    }

    fun getEmptyView(): View? {
        return null
    }

    fun getErrorView(): View? {
        return null
    }

    fun getHeaderView(): View? {
        return null
    }

    fun getFooterView(): View? {
        return null
    }

}
