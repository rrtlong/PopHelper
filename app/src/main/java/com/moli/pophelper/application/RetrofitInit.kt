package com.moli.pophelper.application

import android.app.Application
import com.moli.module.framework.application.IAppInit
import com.moli.module.net.http.RetrofitUtils

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
class RetrofitInit : IAppInit {
    override fun init(application: Application) {
        RetrofitUtils.retrofit
    }

}
