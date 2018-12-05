package com.moli.pophelper.application

import android.app.Application
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.fresco.FrescoImageLoader
import com.moli.module.framework.application.IAppInit
import com.moli.module.net.imageloader.FrescoConfig
import com.moli.module.net.imageloader.MyMemoryTrimmableRegistry
import okhttp3.OkHttpClient

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 14:13
 * 修改人：lijilong
 * 修改时间：2018/12/4 14:13
 * 修改备注：
 * @versionf
 */
class FrescoInit : IAppInit {
    var mTrimmableRegistry: MyMemoryTrimmableRegistry? = null
    override fun init(application: Application) {
        val cacheDir = application.cacheDir
        mTrimmableRegistry = MyMemoryTrimmableRegistry()
        var configBuilder = FrescoConfig.getConfigBuilder(application, cacheDir, OkHttpClient())
        configBuilder.setMemoryTrimmableRegistry(mTrimmableRegistry)
        BigImageViewer.initialize(FrescoImageLoader.with(application, configBuilder.build()))
    }

}
