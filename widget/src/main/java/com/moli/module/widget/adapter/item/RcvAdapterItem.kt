package com.moli.module.widget.adapter.item

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * 项目名称：Jasmine
 * 类描述：
 * 创建人：liujun
 * 创建时间：2018/1/4 16:05
 * 修改人：liujun
 * 修改时间：2018/1/4 16:05
 * 修改备注：
 * @version
 */
class RcvAdapterItem<T>(
        context: Context,
        parent: ViewGroup,
        val item: AdapterItem<T>
) : RecyclerView.ViewHolder(item.getItemView(context, parent)) {

    init {
        this.item.setViews()
    }
}
