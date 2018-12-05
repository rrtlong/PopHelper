package com.moli.module.net.http.provider.impl

import android.content.Context
import com.moli.module.net.http.api.StatisticApi
import com.moli.module.net.http.provider.StatisticsService
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.net.http.RetrofitUtils
import com.moli.module.router.RewardRouter

/**
 * 项目名称：Aletter
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/7/31 下午6:34
 * 修改人：yuliyan
 * 修改时间：2018/7/31 下午6:34
 * 修改备注：
 * @version
 */
@Route(path = RewardRouter.Service.STATISTICS)
class StatisticServiceImpl : StatisticsService {
    lateinit var api: StatisticApi

    override fun init(context: Context?) {
        api = RetrofitUtils.retrofit.create(StatisticApi::class.java)
    }

    override fun activationInformation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loginData(type: String, typeInfo: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun shareData(type: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerData(type: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
