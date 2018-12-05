package com.moli.module.net.http.provider.impl

import android.content.Context
import com.moli.module.model.http.ResponsePay
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.utils.rx.toIoAndMain
import com.moli.module.net.http.RetrofitUtils
import com.moli.module.net.http.api.PingxxApi
import com.moli.module.net.http.provider.PingxxService
import com.moli.module.router.RewardRouter
import io.reactivex.Observable

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/11 上午10:45
 * 修改人：yuliyan
 * 修改时间：2018/10/11 上午10:45
 * 修改备注：
 * @version
 */
@Route(path = RewardRouter.Service.PINGXX_API)
class PingxxServiceImpl : PingxxService {

    private lateinit var api: PingxxApi

    override fun init(context: Context?) {
        api = RetrofitUtils.pingRetrofit.create(PingxxApi::class.java)
    }

    override fun requestPayCharge(responsePay: ResponsePay): Observable<String> {
        return api.requestPayCharge(responsePay).toIoAndMain()
    }

}
