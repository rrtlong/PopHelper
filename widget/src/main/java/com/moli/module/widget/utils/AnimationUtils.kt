package com.moli.module.widget.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/8 上午11:56
 * 修改人：yuliyan
 * 修改时间：2018/10/8 上午11:56
 * 修改备注：
 * @version
 */

fun View.rotate(): ObjectAnimator {
    val rotateAnimation = ObjectAnimator.ofFloat(this, "rotation", 0f, 360.0f)
    rotateAnimation.duration = 1000
    rotateAnimation.repeatCount = -1
    rotateAnimation.interpolator = LinearInterpolator()
    rotateAnimation.repeatMode = ValueAnimator.RESTART
    rotateAnimation.start()
    return rotateAnimation
}


