package com.moli.pophelper.widget

import android.view.View
import com.moli.module.widget.adapter.item.SimpleItem
import kotlinx.android.extensions.LayoutContainer

/**
 * 项目名称：moli
 * 类描述：实现了 LayoutContainer 接口的 Item 类，Kotlin类继承本类，可以直接引用布局中的控件
 * 创建人：liujun
 * 创建时间：2018/6/2 15:13
 * 修改人：liujun
 * 修改时间：2018/6/2 15:13
 * 修改备注：
 * @version
 */
abstract class LayoutContainerItem<T> : SimpleItem<T>(), LayoutContainer {
    override val containerView: View?
        get() = rootView

}
