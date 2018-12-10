package com.moli.pophelper.application

import android.app.Application
import com.moli.module.framework.application.IAppInit
import zlc.season.rxdownload3.core.DownloadConfig
import zlc.season.rxdownload3.extension.ApkInstallExtension
import zlc.season.rxdownload3.http.OkHttpClientFactoryImpl
import zlc.season.rxdownload3.notification.NotificationFactoryImpl

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
        var config = DownloadConfig.Builder.create(application)
            .enableDb(false)     //Enable the database
            .enableService(true)    //Enable Service
            .useHeadMethod(true)    //Use http HEAD method.
            .setMaxRange(10)       // Maximum concurrency for each mission.
            .setRangeDownloadSize(4 * 1000 * 1000) //The size of each Range，unit byte
            .setMaxMission(3)      // The number of mission downloaded at the same time
            .enableNotification(true)   //Enable Notification
            .setNotificationFactory(NotificationFactoryImpl())      //Custom notification
            .setOkHttpClientFacotry(OkHttpClientFactoryImpl())      //Custom OKHTTP
            .addExtension(ApkInstallExtension::class.java)    //Add extension
        DownloadConfig.init(config)
    }

}
