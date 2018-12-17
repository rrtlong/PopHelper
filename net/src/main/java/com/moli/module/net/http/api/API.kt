package com.moli.module.net.http.api

import com.moli.module.model.base.*
import com.moli.module.model.http.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * 项目名称：Aletter
 * 类描述：api接口
 * 创建人：yuliyan
 * 创建时间：2018/7/31 下午6:10
 * 修改人：yuliyan
 * 修改时间：2018/7/31 下午6:10
 * 修改备注：
 * @version
 */
interface API {
    /******************POP Helper API**************************/


    @POST("/moli_helper/helper/version/versionInfo")
    fun getVersion(@Body request: VersionRequest): Observable<VersionModel>

    /**
     * 登录模块-获取验证码
     */
//    @POST("/moli_helper/helper/user/getPhoneAuth")
    @POST
    fun getPhoneCode(@Url url: String, @Body codeRequest: CodeRequest): Observable<String>

    /**
     *  登录接口
     */
//    @POST("/moli_helper/helper/user/register")
    @POST
    fun login(@Url url: String, @Body login: ResonseLogin): Observable<UserInfo>

    /**
     * banner以及下载信息接口
     * bannerType->
     */
//    @POST("/moli_helper/helper/config/bannerContent")
    @POST
    fun getBanner(@Url url: String, @Body bannerRequest: BannerRequest): Observable<List<BannerModel>>

    //攻略列表
//    @POST("/moli_helper/helper/strategy/getStrategyList")
    @POST
    fun getStrategyList(@Url url: String, @Body responseListPage: ResponseListPage): Observable<List<StrategyModel>>

    //攻略推荐列表
//    @POST("/moli_helper/helper/strategy/getRecommendList")
    @POST
    fun getRecommendList(@Url url: String, @Body responseListPage: ResponseListPage): Observable<List<StrategyModel>>

    //app推荐
//    @POST("/moli_helper/helper/strategy/getSortRecommendList")
    @POST
    fun getAppList(@Url url: String, @Body responseListPage: ResponseListPage): Observable<List<AppModel>>


    //财富，获取商品列表
//    @POST("/moli_helper/helper/recharge/rechargeConfig")
    @POST
    fun getGoodsList(@Url url: String): Observable<List<GoodsModel>>

    //签到
//    @POST("/moli_helper/helper/user/sign")
    @POST
    fun sign(@Url url: String): Observable<String>

    //支付凭证
//    @POST("/moli_helper/helper/recharge/createBilling")
    @POST
    fun paySign(@Url url: String, @Body charge: ResponseOrder): Observable<String>

    //    @POST("/moli_helper/helper/user/getUserInfo")
    @POST
    fun getUserInfo(@Url url: String, @Body request: CodeRequest): Observable<UserInfo>

    @POST  ///moli_helper/helper/user/moneyLog
    fun getRecordList(@Url url:String, @Body request: RecordRequest): Observable<List<RecordModel>>


}
