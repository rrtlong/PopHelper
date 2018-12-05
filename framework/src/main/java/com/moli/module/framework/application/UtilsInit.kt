package com.moli.module.framework.application

import android.app.Application
import com.blankj.utilcode.util.Utils

/**
 * 项目名称：Aletter
 * 类描述：com.blankj.utilcode.util.Utils初始化操作
 * 创建人：yuliyan
 * 创建时间：2018/7/30 下午7:53
 * 修改人：yuliyan
 * 修改时间：2018/7/30 下午7:53
 * 修改备注：
 * @version
 */
class UtilsInit : IAppInit {
    override fun init(application: Application) {
        Utils.init(application)
    }

}
