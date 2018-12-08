package com.moli.pophelper.application

import android.app.Application
import com.moli.module.framework.application.IAppInit
import zlc.season.rxdownload3.core.DownloadConfig
import zlc.season.rxdownload3.extension.ApkInstallExtension
import zlc.season.rxdownload3.extension.ApkOpenExtension

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/8 17:58
 * 修改人：lijilong
 * 修改时间：2018/12/8 17:58
 * 修改备注：
 * @version
 */
class RxDownloadInit : IAppInit {
    override fun init(application: Application) {
        val builder = DownloadConfig.Builder.create(application)
            .enableDb(false)
            .setDebug(true)
//            .setDbActor(SQLiteActor(application))
            .enableNotification(true)
            .addExtension(ApkInstallExtension::class.java)
            .addExtension(ApkOpenExtension::class.java)

        DownloadConfig.init(builder)
    }

}
