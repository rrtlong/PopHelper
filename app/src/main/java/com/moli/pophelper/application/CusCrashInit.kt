package com.moli.pophelper.application

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.moli.module.framework.application.IAppInit
import com.moli.module.framework.utils.CountUtils
import com.moli.module.model.constant.SPConstant
import com.moli.module.widget.widget.safetyLongToast
import com.umeng.analytics.MobclickAgent
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 14:10
 * 修改人：lijilong
 * 修改时间：2018/12/4 14:10
 * 修改备注：
 * @version
 */
class CusCrashInit : IAppInit{
    override fun init(application: Application) {
        Thread.setDefaultUncaughtExceptionHandler(CusCrashHandler(application))
    }


    class CusCrashHandler(val context: Context) : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(t: Thread?, e: Throwable?) {
            Timber.e(e)
            CountUtils.onError(t?.name, e)
            context.safetyLongToast("程序异常，2秒后重新启动")
            Observable.timer(2, TimeUnit.SECONDS).subscribe {
                restartApp(context)
            }
        }

        /**
         * 重启应用
         */
        fun restartApp(context: Context) {
            val currentTimeMillis = System.currentTimeMillis()
            //30秒内只重启一次，防止死循环
            if (currentTimeMillis - SPUtils.getInstance().getLong(SPConstant.RESTART_APP_TIME_CONSTANT, 0L) > 30000) {
                val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                val restartIntent = PendingIntent
                    .getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                val mgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                SPUtils.getInstance().put(SPConstant.RESTART_APP_TIME_CONSTANT, currentTimeMillis)
                mgr.set(AlarmManager.RTC, currentTimeMillis + 2000, restartIntent)
            }
            ActivityUtils.finishAllActivities()
            MobclickAgent.onKillProcess(context)
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }

}
