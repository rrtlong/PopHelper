package com.moli.module.widget.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/12 下午5:57
 * 修改人：yuliyan
 * 修改时间：2018/10/12 下午5:57
 * 修改备注：
 * @version
 */
class CenterAlignImageSpan : ImageSpan {

    constructor(d: Drawable?) : super(d)
    constructor(context: Context?, b: Bitmap?) : super(context, b)


    override fun draw(canvas: Canvas?, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint?) {
        val b = drawable
        val fm = paint?.fontMetricsInt
        var transY = (y + (fm?.descent ?: 0) + y + (fm?.ascent ?: 0)) / 2 - b.bounds.bottom / 2
        if (transY <= 0) {
            transY = 5
        }
        canvas?.save()
        canvas?.translate(x, transY.toFloat()) //绘制图片位移一段距离
        b.draw(canvas)
        canvas?.restore()

    }
}
