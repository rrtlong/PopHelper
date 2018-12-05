package com.moli.pophelper.application

import android.content.ComponentCallbacks2
import android.content.Context
import android.support.multidex.MultiDex
import com.facebook.common.memory.MemoryTrimType
import com.moli.module.framework.application.AnalyticsAgentInit
import com.moli.module.framework.application.IAppInit
import com.moli.module.framework.base.BaseApplication
import timber.log.Timber

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 14:10
 * 修改人：lijilong
 * 修改时间：2018/12/4 14:10
 * 修改备注：
 * @version
 */
class HelperApplication : BaseApplication() {
    private var frescoInit: FrescoInit? = null
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        val initList = mutableListOf<IAppInit>()
        frescoInit = FrescoInit()
        initList.add(frescoInit!!)
        initList.add(RetrofitInit())
        initList.add(UmengInit())
        initList.add(CusCrashInit())
        initList.add(ARouterInit())
        initList.add(AnalyticsAgentInit())
        initList.forEach { it.init(this) }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Timber.e("onTrimMemory level:%s", level)
        try {

            if (frescoInit?.mTrimmableRegistry != null) {
                when (level) {
                    ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN //内存不足，并且该进程的UI已经不可见了。
                    -> frescoInit?.mTrimmableRegistry?.onTrimMemory(MemoryTrimType.OnCloseToDalvikHeapLimit)

                    ComponentCallbacks2.TRIM_MEMORY_BACKGROUND //内存不足，并且该进程是后台进程。
                    -> frescoInit?.mTrimmableRegistry?.onTrimMemory(MemoryTrimType.OnSystemLowMemoryWhileAppInBackground)

                    ComponentCallbacks2.TRIM_MEMORY_MODERATE //内存不足，并且该进程在后台进程列表的中部。
                        , ComponentCallbacks2.TRIM_MEMORY_COMPLETE //内存不足，并且该进程在后台进程列表最后一个，马上就要被清理
                    -> frescoInit?.mTrimmableRegistry?.onTrimMemory(MemoryTrimType.OnAppBackgrounded)

                    ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL //内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存
                        , ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW //内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存
                        , ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE //内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存
                    -> frescoInit?.mTrimmableRegistry?.onTrimMemory(MemoryTrimType.OnSystemLowMemoryWhileAppInForeground)
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }


}
