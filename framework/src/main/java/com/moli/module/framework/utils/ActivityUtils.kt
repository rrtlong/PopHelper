package com.moli.module.framework.utils

import android.content.Context.ACTIVITY_SERVICE
import android.app.ActivityManager
import android.content.Intent
import com.blankj.utilcode.util.Utils


/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/8/22 下午6:12
 * 修改人：yuliyan
 * 修改时间：2018/8/22 下午6:12
 * 修改备注：
 * @version
 */
object ActivityUtils {

    fun isActivityStart(activity: Class<*>): Boolean {
        val intent = Intent(Utils.getApp(), activity)
        val cmpName = intent.resolveActivity(Utils.getApp().packageManager)
        var flag = false
        if (cmpName != null) { // 说明系统中存在这个activity
            val am = Utils.getApp().getSystemService(ACTIVITY_SERVICE) as ActivityManager
            val taskInfoList = am.getRunningTasks(20)  //获取从栈顶开始往下查找的10个activity
            for (taskInfo in taskInfoList) {
                if (taskInfo.baseActivity == cmpName) { // 说明它已经启动了
                    flag = true
                    break  //跳出循环，优化效率
                }
            }
        }
        return flag
    }
}
