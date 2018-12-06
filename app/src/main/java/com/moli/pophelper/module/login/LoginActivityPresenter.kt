package com.moli.pophelper.module.login

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.moli.module.framework.mvp.BasePresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.model.base.UserInfo
import com.moli.module.model.constant.EventConstant
import com.moli.module.model.http.ResonseLogin
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.provider.APIService
import com.moli.module.net.manager.UserManager
import org.simple.eventbus.EventBus

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/6 03:45
 * 修改人：lijilong
 * 修改时间：2018/12/6 03:45
 * 修改备注：
 * @version
 */
class LoginActivityPresenter(iView: IView) : BasePresenter<IView>(iView) {
    @Autowired
    lateinit var api: APIService

    fun getCode(phone: String) {
        api.getPhoneCode(phone).bindToLifecycle(owner).subscribe(object : HttpSubscriber<String>() {
            override fun onNext(t: String) {
                MVPMessage.obtain(rootView!!, 1).handleMessageToTarget()
            }

        })
    }

    fun login(phone: String, code: String) {
        api.login(ResonseLogin(phone = phone, phoneCode = code)).bindToLifecycle(owner)
            .subscribe(object : HttpSubscriber<UserInfo>() {
                override fun onNext(t: UserInfo) {
                    UserManager.refreshUserInfo(userInfo = t, hasAccessToken = false)
                    MVPMessage.obtain(rootView!!, 2, t).handleMessageToTarget()
                    EventBus.getDefault().post(EventConstant.LOGIN_SUCCESS)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    MVPMessage.obtain(rootView!!, 3).handleMessageToTarget()
                }
            })

    }
}
