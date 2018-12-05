package com.moli.module.widget.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.moli.module.widget.R
import com.moli.module.widget.utils.rotate
import kotlinx.android.synthetic.main.list_loading_layout.view.*

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/8 上午11:38
 * 修改人：yuliyan
 * 修改时间：2018/10/8 上午11:38
 * 修改备注：
 * @version
 */
class ListLoadingView : LinearLayout {

    var animation: ObjectAnimator? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
            defStyleAttr)

    init {
        inflate(context, R.layout.list_loading_layout, this)
        gravity = Gravity.CENTER
        orientation = VERTICAL
        animation = progressBarCircular.rotate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            animation?.pause()
        }
    }
}
