package com.moli.module.widget.adapter.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 项目名称：Android
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：16/8/26 22:44
 * 修改人：LiuJun
 * 修改时间：16/8/26 22:44
 * 修改备注：
 */
abstract class SimpleItem<T> : AdapterItem<T> {

    protected lateinit var rootView: View


    override fun getItemView(context: Context, parent: ViewGroup): View {
        rootView = LayoutInflater.from(context).inflate(layoutResId, parent, false)
        return rootView
    }


    override fun setViews() {}

    /**
     * Item绑定到视图
     */
    override fun onAttach() {}

    /**
     * Item从视图解绑
     */
    override fun onDetach() {}


}
