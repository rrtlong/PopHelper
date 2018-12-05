package com.moli.module.widget.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import com.moli.module.widget.R

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/9/13 下午2:52
 * 修改人：yuliyan
 * 修改时间：2018/9/13 下午2:52
 * 修改备注：
 * @version
 */
object GiftCountUtils {

    fun getGiftCountSpannableString(context: Context, count: Int, textSize: Int): SpannableString {
        val spannableString = SpannableString(count.toString().plus(" "))
        val countArray = count.toString().toCharArray()
        val imageSpanns = mutableListOf<ImageSpan>()
        val xImageDrawable = ContextCompat.getDrawable(context, R.drawable.icon_x)
        if (xImageDrawable != null) {
            val width = textSize * xImageDrawable.intrinsicWidth / xImageDrawable.intrinsicHeight
            xImageDrawable.setBounds(0, 0, width / 2, textSize / 2)
            imageSpanns.add(ImageSpan(xImageDrawable))
        }
        countArray.forEach {
            // char转换 int  48-0
            val countInt = it.toInt() - 48
            val imageDrawable = getDrawable(countInt, context)
            if (imageDrawable != null) {
                val width = textSize * imageDrawable.intrinsicWidth / imageDrawable.intrinsicHeight
                imageDrawable.setBounds(0, 0, width, textSize)
                imageSpanns.add(ImageSpan(imageDrawable))
            }
        }
        imageSpanns.forEachIndexed { index, imageSpan ->
            spannableString.setSpan(imageSpan, index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return spannableString

    }





      fun getDrawable(count: Int, context: Context): Drawable? {
        return when (count) {
            0 -> {
                ContextCompat.getDrawable(context, R.drawable.icon_0)
            }
            1 -> {
                ContextCompat.getDrawable(context, R.drawable.icon_1)
            }
            2 -> {
                ContextCompat.getDrawable(context, R.drawable.icon_2)
            }
            3 -> {
                ContextCompat.getDrawable(context, R.drawable.icon_3)
            }
            4 -> {
                ContextCompat.getDrawable(context, R.drawable.icon_4)
            }
            5 -> {
                ContextCompat.getDrawable(context, R.drawable.icon_5)
            }
            6 -> {
                ContextCompat.getDrawable(context, R.drawable.icon_6)
            }
            7 -> {
                ContextCompat.getDrawable(context, R.drawable.icon_7)
            }
            8 -> {
                ContextCompat.getDrawable(context, R.drawable.icon_8)
            }
            9 -> {
                ContextCompat.getDrawable(context, R.drawable.icon_9)
            }
            else -> {

                ContextCompat.getDrawable(context, R.drawable.icon_0)
            }

        }
    }
}
