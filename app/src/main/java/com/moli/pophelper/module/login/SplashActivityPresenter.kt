package com.moli.pophelper.module.login

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.moli.module.framework.mvp.BasePresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.model.base.BannerModel
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.provider.APIService

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

    /**
     * 获取开屏广告
     */
    fun getBanner() {
        api.getBanner(1).bindToLifecycle(owner).subscribe(object : HttpSubscriber<BannerModel>() {
            override fun onNext(t: BannerModel) {
                if (t != null) {
                    MVPMessage.obtain(rootView!!, 1, t).handleMessageToTarget()
                } else {
                    MVPMessage.obtain(rootView!!, 2).handleMessageToTarget()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                MVPMessage.obtain(rootView!!, 2).handleMessageToTarget()
            }

        })
    }
}
