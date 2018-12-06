package com.moli.module.net.http.api

import com.moli.module.model.base.*
import com.moli.module.model.http.*
import io.reactivex.Observable
import retrofit2.http.*

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

    /*
     * 版本更新
     */
    @POST("/version/version")
    fun version(@Body responseVersion: ResponseVersion): Observable<VersionModel>

    /**
     * 登录模块-获取验证码
     */
    @POST("getPhoneCode/{phone}")
    fun getPhoneCode(@Path("phone") phone: String): Observable<String>


    /**
     *  登录接口
     */
    @POST("/user/login")
    fun login(@Body login: ResonseLogin): Observable<UserInfo>


    /**
     * 获取用户信息 基本信息
     */
    @POST("user/userInfo/{userId}")
    fun getUserInfo(@Path("userId") userId: Long): Observable<UserInfo>

    /**
     *  获取用户 账户信息
     */
    @POST("user/accountInfo")
    fun getAccountInfo(): Observable<UserInfo>

    /**
     * 删除评论接口
     */
    @POST("deletedComment/{commentId}")
    fun delectComment(@Path("commentId") commentId: Long): Observable<String>

    /**
     * 云存储
     */
    @POST("upload/getToken")
    fun getToken(@Body fileKey: TokenRequest): Observable<String>

    /**
     * 礼物列表
     */
    @POST("/gift/gifts")
    fun publishGifts(): Observable<List<GiftModel>>


    @POST("/friend/concern/{toUserId}")
    fun follow(@Path("toUserId") toUserId: Long): Observable<Int>


    /**
     * 首页-关注  推荐关注用户
     */
    @POST("/content/attentionUsers")
    fun attentionUsers(@Body listPage: ResponseListPage): Observable<List<UserLiteModel>>


    /**
     * 创建订单
     */
    @POST("/order/createOrder")
    fun createOrder(@Body order: ResponseOrder): Observable<OrderModel>


    /**
     *  搜索用户/主贴/悬赏
     */
    @POST("/search/info")
    fun searchList(@Body pageList: ResponseSearch): Observable<List<UserLiteModel>>

    /**
     *  bannerInfos
     */
    @POST("/banner/infos")
    fun bannerInfos(): Observable<List<BannerModel>>

    /**
     *  开屏广告
     */
    @POST("/banner/greenBanner")
    fun greenBanner(): Observable<List<BannerModel>>


    /**
     *用户解除绑定信息接口-1手机 2wx 3wb 4qq
     */
    @POST("/user/removeLoginInfo/{loginType}")
    fun removeBindInfo(@Path("loginType") loginType: Int): Observable<String>

    /**
     * 用户账号详情接口
     */
    @POST("/user/accountInfo")
    fun accountInfo(): Observable<AccountInfo>

    /**
     * 删除作品或者悬赏
     */
    @POST("/story/deleteStory/{id}")
    fun delectStory(@Path("id") id: Long): Observable<String>

    /**
     * 拉黑用户
     */
    @POST("/user/blackUser/{userId}")
    fun pullBlackUser(@Path("userId") userId: Long): Observable<String>

    /**
     *  评论点赞
     */
    @POST("/record/likeComment/{commentId}")
    fun commentLike(@Path("commentId") commentId: Long): Observable<String>

    /**
     *  删除评论
     */
    @POST("/comment/deleteComment/{id}")
    fun commentDelect(@Path("id") id: Long): Observable<String>

    /**
     *  用户账单接口
     */
    @POST("/user/userWallet")
    fun userWallet(): Observable<UserWallet>

    /**
     * 搜索页用户推荐
     */
    @POST("search/recommendUsers")
    fun searchRecommendUsers(@Body listPage: ResponseListPage): Observable<List<UserLiteModel>>

    @POST("/play/downLoadUrl")
    fun downLoadUrl(@Body url: DownLoadRespone): Observable<String>


    /**
     * 上报日志type 1:启动上报
     */
    @POST("/dataLog/addLog")
    fun addLog(@Body response: ResponseLogModel): Observable<String>

    /**
     * 音乐列表（1推荐，2热门）
     */
    @POST("/music/musics")
    fun recommandOrHotMusics(@Body response: ResponseMusicList): Observable<List<MusicModel>>

    /**
     * banner以及下载信息接口
     * bannerType->
     */
    @POST("/config/downloadUrl")
    @FormUrlEncoded
    fun getBanner(@Field("bannerType") bannerType: Int): Observable<List<BannerModel>>


}
