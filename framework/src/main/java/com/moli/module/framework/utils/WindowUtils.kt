package com.moli.module.framework.utils

import android.app.Activity
import android.app.DialogFragment
import android.view.Window
import android.view.WindowManager

/**
 * 项目名称：Jasmine
 * 类描述：Window的一些扩展函数
 * 创建人：liujun
 * 创建时间：2018/1/15 11:25
 * 修改人：liujun
 * 修改时间：2018/1/15 11:25
 * 修改备注：
 * @version
 */

/**
 * DialogFragment 是否为全屏
 * @return true: 全屏  false: 不是全屏  null: Dialog 为空
 */
fun android.support.v4.app.DialogFragment.isFullScreen(): Boolean? {
    return this.dialog?.window?.isFullScreen()
}

/**
 * DialogFragment 是否为全屏
 * @return true: 全屏  false: 不是全屏  null: Dialog 为空
 */
fun DialogFragment.isFullScreen(): Boolean? {
    return this.dialog?.window?.isFullScreen()
}

/**
 * Activity 是否为全屏
 * @return true: 全屏  false: 不是全屏  null: window 为空
 */
fun Activity.isFullScreen(): Boolean? {
    return this.window?.isFullScreen()
}

/**
 * Window 是否为全屏
 * @return true: 全屏  false: 不是全屏
 */
fun Window.isFullScreen(): Boolean {
    val flag = this.attributes.flags
    return flag and WindowManager.LayoutParams.FLAG_FULLSCREEN == WindowManager.LayoutParams.FLAG_FULLSCREEN
}
