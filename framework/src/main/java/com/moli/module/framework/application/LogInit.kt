package com.moli.module.framework.application

import android.app.Application
import com.moli.module.framework.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import timber.log.Timber

/**
 * 项目名称：Aletter
 * 类描述： log日志打印
 * 创建人：yuliyan
 * 创建时间：2018/7/31 下午12:10
 * 修改人：yuliyan
 * 修改时间：2018/7/31 下午12:10
 * 修改备注：
 * @version
 */
class LogInit : IAppInit {
    override fun init(application: Application) {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .methodCount(3)
                .methodOffset(5)
                .tag("Reward")
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.LOG_DEBUG
            }

        })
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                Logger.log(priority, tag, message, t)
            }
        })
    }
}
