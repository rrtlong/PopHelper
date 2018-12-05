package com.moli.module.net.imageloader

import android.app.ActivityManager
import android.content.Context

/**
 * 项目名称：Jasmine
 * 类描述：
 * 创建人：liujun
 * 创建时间：2018/2/7 16:19
 * 修改人：liujun
 * 修改时间：2018/2/7 16:19
 * 修改备注：
 * @version
 */
object AppUtils {

    fun isMainProcess(context: Context): Boolean {
        val curProcessName = getCurProcessName(context)
        val packageName = context.packageName
        return packageName == curProcessName
    }


    /**
     * 获取当前的进程名
     */
    fun getCurProcessName(context: Context): String? {
        val pid = android.os.Process.myPid()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        val processes = activityManager?.runningAppProcesses ?: return null
        return processes.firstOrNull { it.pid == pid }?.processName
    }


}
