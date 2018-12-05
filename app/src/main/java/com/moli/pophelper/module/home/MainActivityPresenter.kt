package com.moli.pophelper.module.home

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.moli.module.framework.mvp.BasePresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.net.http.provider.APIService

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/5 02:37
 * 修改人：lijilong
 * 修改时间：2018/12/5 02:37
 * 修改备注：
 * @version
 */
class MainActivityPresenter(iView: IView) : BasePresenter<IView>(iView) {
    @Autowired
    lateinit var api: APIService
}
