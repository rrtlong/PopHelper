package com.moli.pophelper.application

import android.app.Application
import com.moli.module.framework.application.IAppInit
import com.moli.pophelper.BuildConfig
import com.umeng.socialize.Config
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.utils.Log

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 14:11
 * 修改人：lijilong
 * 修改时间：2018/12/4 14:11
 * 修改备注：
 * @version
 */
class UmengInit : IAppInit {
    override fun init(application: Application) {
        PlatformConfig.setWeixin(BuildConfig.APPID_WX, BuildConfig.APPKEY_WX)
        PlatformConfig.setQQZone(BuildConfig.APPID_QQ, BuildConfig.APPKEY_QQ)
        PlatformConfig.setSinaWeibo(BuildConfig.APPID_WB, BuildConfig.APPKEY_WB, BuildConfig.REDIRECTURL_WB)
        Log.LOG = BuildConfig.LOG_DEBUG
        Config.DEBUG = BuildConfig.LOG_DEBUG

    }
}
