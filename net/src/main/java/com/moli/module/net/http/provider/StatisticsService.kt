package com.moli.module.net.http.provider

import com.moli.module.net.http.api.StatisticApi
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 项目名称：Aletter
 * 类描述：统计接口
 * 创建人：yuliyan
 * 创建时间：2018/7/31 下午6:18
 * 修改人：yuliyan
 * 修改时间：2018/7/31 下午6:18
 * 修改备注：
 * @version
 */
interface StatisticsService :IProvider, StatisticApi {


    /**
     * 统计激活信息
     */
    fun activationInformation()

    /**
     * 统计登录数据
     */
    fun loginData(type: String, typeInfo: String = "")

    /**
     * 统计分享数据
     */
    fun shareData(type: String)

    /**
     * 统计注册信息
     */
    fun registerData(type: String)
}
