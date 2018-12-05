package com.moli.pophelper.application

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.moli.module.framework.application.IAppInit
import com.moli.pophelper.BuildConfig

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 14:09
 * 修改人：lijilong
 * 修改时间：2018/12/4 14:09
 * 修改备注：
 * @version
 */
class ARouterInit : IAppInit {
    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
            ARouter.printStackTrace()
        }
        ARouter.init(application)
    }

}
