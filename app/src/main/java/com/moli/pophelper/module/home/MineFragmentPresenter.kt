package com.moli.pophelper.module.home

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.moli.module.framework.mvp.BasePresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.model.base.BannerModel
import com.moli.module.model.http.BannerRequest
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.provider.APIService

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 18:45
 * 修改人：lijilong
 * 修改时间：2018/12/4 18:45
 * 修改备注：
 * @version
 */
class MineFragmentPresenter(iView: IView) : BasePresenter<IView>(iView) {
    @Autowired
    lateinit var api: APIService

    fun getBanner() {
        api.getBanner("",BannerRequest("4")).bindToLifecycle(owner).subscribe(object : HttpSubscriber<List<BannerModel>>() {
            override fun onNext(t: List<BannerModel>) {
                var temp = t.filter { it.bannerType != 0 }
                if(temp != null && temp.isNotEmpty()){
                    MVPMessage.obtain(rootView!!, 1, t).handleMessageToTarget()
                }
            }

        })
    }
}
