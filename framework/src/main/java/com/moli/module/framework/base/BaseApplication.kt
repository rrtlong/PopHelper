package com.moli.module.framework.base

import android.app.Application
import com.moli.module.framework.application.*

/**
 * 项目名称：RewardApp
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/9/10 下午5:05
 * 修改人：yuliyan
 * 修改时间：2018/9/10 下午5:05
 * 修改备注：
 * @version
 */
open class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        val initList = mutableListOf<IAppInit>()
        initList.add(UtilsInit())
        initList.add(HookInit())
        initList.add(LogInit())
        initList.add(ForegroundInit())
        initList.add(AnalyticsAgentInit())
        initList.forEach { it.init(this) }
    }
}
