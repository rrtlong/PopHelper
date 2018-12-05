package com.moli.module.framework.application

import android.app.Application
import com.moli.module.framework.analytics.AnalyticsAgent
import com.moli.module.framework.utils.CountUtils

/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/8/3 下午4:56
 * 修改人：yuliyan
 * 修改时间：2018/8/3 下午4:56
 * 修改备注：
 * @version
 */
class AnalyticsAgentInit : IAppInit {
    override fun init(application: Application) {
        val countUtils = CountUtils
        AnalyticsAgent.setAnalyticsable(countUtils)
    }
}
