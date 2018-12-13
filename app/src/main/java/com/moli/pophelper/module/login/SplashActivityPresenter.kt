package com.moli.pophelper.module.login

import com.aletter.xin.app.update.AppUpdateUtil
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.moli.module.framework.mvp.BasePresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.model.base.BannerModel
import com.moli.module.model.base.UserInfo
import com.moli.module.model.base.VersionModel
import com.moli.module.model.constant.SPConstant
import com.moli.module.model.http.BannerRequest
import com.moli.module.model.http.CodeRequest
import com.moli.module.model.http.VersionRequest
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.RetrofitUtils
import com.moli.module.net.http.provider.APIService
import com.moli.module.net.manager.UserManager
import com.moli.pophelper.BuildConfig
import com.moli.pophelper.constant.Constant

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 15:04
 * 修改人：lijilong
 * 修改时间：2018/12/4 15:04
 * 修改备注：
 * @version
 */
class SplashActivityPresenter(iView: IView) : BasePresenter<IView>(iView) {

    @Autowired
    lateinit var api: APIService

    val appUpdateUtil: AppUpdateUtil by lazy { AppUpdateUtil((rootView!! as SplashActivity)) }

    /**
     * 获取开屏广告
     */
    fun getBanner() {
        api.getBanner("", BannerRequest("1")).subscribe(object : HttpSubscriber<List<BannerModel>>() {
            override fun onNext(t: List<BannerModel>) {
                var downModel = t.find { it.bannerType == 1 }
                downModel?.let {
                    Constant.POP_DOWNLOAD_URL = it.downloadUrl!!
                    Constant.POP_PACKAGE = it.packageName!!
                }
                var advModel = t.find { it.bannerType == 0 }
                if (advModel != null) {
                    MVPMessage.obtain(rootView!!, 1, advModel).handleMessageToTarget()
                } else {
                    MVPMessage.obtain(rootView!!, 2).handleMessageToTarget()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                e.printStackTrace()
                MVPMessage.obtain(rootView!!, 2).handleMessageToTarget()
            }


        })
    }

    fun getUserInfo() {
        var phone = SPUtils.getInstance().getString(SPConstant.LOGIN_PHONE)
        if (phone == null || phone.isNullOrEmpty()) {
            return
        }
        api.getUserInfo("", CodeRequest(phone)).subscribe(object : HttpSubscriber<UserInfo>() {
            override fun onNext(t: UserInfo) {
                if (t != null) {
                    UserManager.refreshUserInfo(t, false)
                }
            }

        })
    }

    //获取版本信息
    fun getVersion() {
        api.getVersion(VersionRequest(BuildConfig.Channel, BuildConfig.APPLICATION_ID, BuildConfig.versionNumber))
            .bindToLifecycle(owner)
            .subscribe(object : HttpSubscriber<VersionModel>() {
                override fun onNext(t: VersionModel) {
                    if (t.serverwebUrl != null && !t.serverwebUrl.isNullOrEmpty()) {
                        SPUtils.getInstance().put(SPConstant.DYNAMIC_DOMAIN_URL, t.serverwebUrl)
                    } else {
                        SPUtils.getInstance().put(SPConstant.DYNAMIC_DOMAIN_URL, com.moli.module.net.BuildConfig.DOMAIN_NAME)
                    }
                    SPUtils.getInstance().put(SPConstant.IS_DEBUG, t.isDebug ?: 0)
                    SPUtils.getInstance().put("version_model", Gson().toJson(t))
                    appUpdateUtil?.compareVersion(t, true)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    MVPMessage.obtain(rootView!!, 3).handleMessageToTarget()
                }

            })
    }
}
