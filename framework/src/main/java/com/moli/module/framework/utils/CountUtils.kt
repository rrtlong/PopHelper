package com.moli.module.framework.utils

import android.content.Context
import com.blankj.utilcode.util.Utils
import com.moli.module.framework.BuildConfig
import com.moli.module.framework.analytics.Analyticsable
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.analytics.AnalyticsConfig
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import timber.log.Timber

/**
 * 项目名称：Platformer
 * 类描述：统计
 * 创建人：liujun
 * 创建时间：2017/10/16 10:25
 * 修改人：liujun
 * 修改时间：2017/10/16 10:25
 * 修改备注：
 * @version
 */
object CountUtils : Analyticsable {

    init {

        val channel = AnalyticsConfig.getChannel(Utils.getApp())
        UMConfigure.init(Utils.getApp(), "5c0bafd9f1f5564ef10002fb", channel, UMConfigure.DEVICE_TYPE_PHONE, null)
        UMConfigure.setLogEnabled(BuildConfig.LOG_DEBUG)
        Timber.i("init-umeng channel=%s", channel)
        //禁止默认的页面统计方式，这样将不会再自动统计Activity。
        MobclickAgent.openActivityDurationTrack(true)
        //错误统计
        MobclickAgent.setCatchUncaughtExceptions(true)
        // 设置日志加密
        // 参数：boolean 默认为false（不加密）

        UMConfigure.setEncryptEnabled(true)

        initBugly()
    }

    fun initBugly() {
        //初始化bugly
        val context = Utils.getApp()
        val strategy = CrashReport.UserStrategy(context)
        strategy.appChannel = AnalyticsConfig.getChannel(context)
        strategy.isEnableUserInfo = true
        strategy.isUploadProcess = AppUtils.isMainProcess(context)
        //        CrashReport.initCrashReport(Utils.getApp(), "42cd1936ad", BuildConfig.LOG_DEBUG, strategy)
        CrashReport.initCrashReport(Utils.getApp(), "2e9cce17d8", BuildConfig.LOG_DEBUG, strategy)
    }

    /**
     * 点击统计
     */
    fun analysisClick(context: Context, id: String) {
        MobclickAgent.onEvent(context, id)
    }

    /**
     * PV统计：进入统计
     */
    fun analysisPVStart(context: Context, id: String) {
        MobclickAgent.onPageStart(id) //统计页面
        MobclickAgent.onResume(context) //统计时长
    }

    /**
     * PV统计：结束统计
     */
    fun analysisPVEnd(context: Context, id: String) {
        MobclickAgent.onPageEnd(id) //统计页面
        MobclickAgent.onPause(context) //统计时长
    }

    override fun onResume(context: Context?) {
        MobclickAgent.onResume(context)
    }

    override fun onPause(context: Context?) {
        MobclickAgent.onPause(context)
    }

    fun onPageStart(context: Context, name: String) {
        MobclickAgent.onPageStart(name)
    }


    fun onPageEnd(context: Context, name: String) {
        MobclickAgent.onPageEnd(name)
    }


    /**
     * 友盟统计用户ID
     *
     */
    fun onProfileSignIn(userName: String, userId: Long) {
        MobclickAgent.onProfileSignIn(userId.toString())
        CrashReport.setUserId(userId.toString())
        CrashReport.putUserData(Utils.getApp(), "nickname", userName)
        CrashReport.putUserData(Utils.getApp(), "userId", userId.toString())
    }

    /**
     * 退出登录，取消友盟统计用户功能
     */
    fun onProfileSignOff() {
        MobclickAgent.onProfileSignOff()
    }


    /**
     * 上传异常信息到友盟和Bugly
     */
    fun onError(e: String? = null, throwable: Throwable? = null) {
        if (e == null && throwable == null) return
        //        MobclickAgent.reportError(Utils.getApp(), e)
        CrashReport.postCatchedException(PostCatchedException(e, throwable))
    }


    class PostCatchedException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)
}
