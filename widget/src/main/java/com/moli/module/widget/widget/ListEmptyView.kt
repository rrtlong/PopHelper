package com.moli.module.widget

import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.list_empty_layout.view.*

/**
 * 项目名称：Jasmine
 * 类描述：列表控件中的空视图
 * 创建人：liujun
 * 创建时间：2018/1/10 22:02
 * 修改人：liujun
 * 修改时间：2018/1/10 22:02
 * 修改备注：
 * @version
 */
class ListEmptyView : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.list_empty_layout, this)
        gravity = Gravity.CENTER
        orientation = VERTICAL
    }

    fun setEmptyImage(@DrawableRes resId: Int) {
        ivEmpty.setImageResource(resId)
    }

}
