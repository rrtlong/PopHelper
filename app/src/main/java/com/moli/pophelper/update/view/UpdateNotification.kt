package com.aletter.xin.app.update.view

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import com.moli.pophelper.R

class UpdateNotification(val context: Context) {
    private var notificationManager: NotificationManager =
        context.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
    var builder: NotificationCompat.Builder? = null

    init {
        builder = NotificationCompat.Builder(context)
        builder?.setSmallIcon(R.mipmap.icon_launcher)
            ?.setContentInfo("下载中...")
            ?.setContentTitle("正在下载")
    }

    fun notifyProgress(progress: Int) {
        builder?.setProgress(100, progress, false)
        //        notificationManager?.notify(0x3,builder?.build())
        builder?.setContentText("下载$progress%")
        notificationManager.notify(0x3, builder?.build())
        if (progress == 100) {
            notificationManager.cancel(0x3)
        }

    }


}
