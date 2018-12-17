package com.moli.pophelper.module.pay

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.moli.module.framework.mvp.BasePresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.model.http.ResponseOrder
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.provider.APIService

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/17 11:52
 * 修改人：lijilong
 * 修改时间：2018/12/17 11:52
 * 修改备注：
 * @version
 */
class PayFragmentPresenter(iView: IView, val order: ResponseOrder) : BasePresenter<IView>(iView) {

    @Autowired
    lateinit var api: APIService

    fun requestOrder(channel: Int) {
        if (order == null) return
        order.platformType = channel
        api.paySign("", order!!).bindToLifecycle(owner)
            .subscribe(object : HttpSubscriber<String>() {
                override fun onNext(t: String) {
                    MVPMessage.obtain(rootView!!, channel, t).handleMessageToTarget()
                }
            })
    }
}
