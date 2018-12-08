package com.moli.module.widget.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.moli.module.widget.R
import kotlinx.android.synthetic.main.footer_view.view.*

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/7 12:27
 * 修改人：lijilong
 * 修改时间：2018/12/7 12:27
 * 修改备注：
 * @version
 */
class FooterView : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.footer_view, this)
    }

    fun setMore(hasMore: Boolean) {
        if (hasMore) {
            loadingLayout.visibility = View.VISIBLE
            noMoreData.visibility = View.GONE
        } else {
            loadingLayout.visibility = View.GONE
            noMoreData.visibility = View.VISIBLE
        }
    }

}
