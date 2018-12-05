package com.moli.module.net.http.api

import com.moli.module.model.http.ResponsePay
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/11 上午10:42
 * 修改人：yuliyan
 * 修改时间：2018/10/11 上午10:42
 * 修改备注：
 * @version
 */
interface PingxxApi {

    @POST("/demo/charge")
    fun requestPayCharge(@Body responsePay: ResponsePay): Observable<String>

}
