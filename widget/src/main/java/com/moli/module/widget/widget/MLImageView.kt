package com.moli.module.widget.widget

import android.content.Context
import android.util.AttributeSet
import com.facebook.drawee.generic.GenericDraweeHierarchy
import info.liujun.image.LJImageView

/**
 * 项目名称：moli
 * 类描述：统一的图片加载控件
 * 创建人：liujun
 * 创建时间：2018/4/11 15:41
 * 修改人：liujun
 * 修改时间：2018/4/11 15:41
 * 修改备注：
 * @version
 */
open class MLImageView : LJImageView {

    constructor(context: Context?, hierarchy: GenericDraweeHierarchy?) : super(context, hierarchy)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

}
