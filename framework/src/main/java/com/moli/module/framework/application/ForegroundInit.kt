package com.moli.module.framework.application

import android.app.Application
import com.moli.module.framework.utils.Foreground

/**
 * 项目名称：Aletter
 * 类描述：是否在前台展示
 * 创建人：yuliyan
 * 创建时间：2018/7/30 下午8:10
 * 修改人：yuliyan
 * 修改时间：2018/7/30 下午8:10
 * 修改备注：
 * @version
 */
class ForegroundInit : IAppInit {
    override fun init(application: Application) {
        Foreground.init(application)
    }
}
