package com.moli.pophelper.module.home

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.blankj.utilcode.util.SPUtils
import com.moli.module.framework.mvp.BasePresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.model.base.UserInfo
import com.moli.module.model.constant.EventConstant
import com.moli.module.model.constant.SPConstant
import com.moli.module.model.http.CodeRequest
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.provider.APIService
import com.moli.module.net.manager.UserManager
import org.simple.eventbus.EventBus

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

    fun getUserInfo() {
        if (UserManager.isLogin()) {
            UserManager.getSynSelf()?.let {
                var phone = SPUtils.getInstance().getString(SPConstant.LOGIN_PHONE)
                if (phone == null || phone.isNullOrEmpty()) {
                    return
                }
                api.getUserInfo("", CodeRequest(phone)).subscribe(object : HttpSubscriber<UserInfo>() {
                    override fun onNext(t: UserInfo) {
                        if (t != null) {
                            UserManager.refreshUserInfo(t, false)
                            EventBus.getDefault().post("", EventConstant.REFRESH_USER_INFO)
                        }
                    }

                })
            }
        }

    }


}
