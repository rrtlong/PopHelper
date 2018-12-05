package com.moli.module.widget.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/12 下午5:14
 * 修改人：yuliyan
 * 修改时间：2018/10/12 下午5:14
 * 修改备注：
 * @version
 */

fun String.startImageSpannableString(context: Context, imageId: Int, imageRate: Float, textSize: Int): SpannableString {
    val spannableString = SpannableString("  " + this)
    val xImageDrawable = ContextCompat.getDrawable(context, imageId)
    xImageDrawable?.setBounds(0, 0, (textSize / imageRate).toInt(), textSize)
    val imageSpan = CenterAlignImageSpan(xImageDrawable)
    spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannableString
}

fun String.startImageSpannableString(context: Context, imageId1: Int, imageRate1: Float, imageId2: Int, imageRate2: Float, textSize: Int): SpannableString {
    val spannableString = SpannableString("    " + this)
    val xImageDrawable = ContextCompat.getDrawable(context, imageId1)
    xImageDrawable?.setBounds(0, 0, (textSize / imageRate1).toInt(), textSize)
    val imageSpan = CenterAlignImageSpan(xImageDrawable)

    spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    val xImageDrawable2 = ContextCompat.getDrawable(context, imageId2)
    xImageDrawable2?.setBounds(0, 0, (textSize / imageRate2).toInt(), textSize)
    val imageSpan2 = CenterAlignImageSpan(xImageDrawable2)
    spannableString.setSpan(imageSpan2, 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannableString
}
