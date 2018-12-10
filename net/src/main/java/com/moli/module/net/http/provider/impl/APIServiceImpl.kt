package com.moli.module.net.http.provider.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.moli.module.framework.utils.rx.toIoAndMain
import com.moli.module.model.base.*
import com.moli.module.model.constant.SPConstant
import com.moli.module.model.http.*
import com.moli.module.net.http.RetrofitUtils
import com.moli.module.net.http.api.API
import com.moli.module.net.http.cache.APICache
import com.moli.module.net.http.provider.APIService
import com.moli.module.net.json.jolyglot
import com.moli.module.router.RewardRouter
import io.reactivex.Observable
import io.rx_cache2.internal.RxCache

/**
 * 项目名称：Aletter
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/7/31 下午6:23
 * 修改人：yuliyan
 * 修改时间：2018/7/31 下午6:23
 * 修改备注：
 * @version
 */

@Route(path = RewardRouter.Service.API)
class APIServiceImpl : APIService {

    private lateinit var api: API

    private lateinit var apiCache: APICache

    override fun init(context: Context?) {
        api = RetrofitUtils.retrofit.create(API::class.java)
        apiCache = RxCache.Builder()
            .useExpiredDataIfLoaderNotAvailable(true)
            .persistence(Utils.getApp().filesDir, jolyglot)
            .using(APICache::class.java)

    }

    override fun version(responseVersion: ResponseVersion): Observable<VersionModel> {
        return api.version(responseVersion).toIoAndMain()
    }

    override fun getPhoneCode(codeRequest: CodeRequest): Observable<String> {
        return api.getPhoneCode(codeRequest).toIoAndMain()
    }


    override fun login(login: ResonseLogin): Observable<UserInfo> {
        return api.login(login).toIoAndMain()
    }


    override fun getAccountInfo(): Observable<UserInfo> {
        return api.getAccountInfo().toIoAndMain()
    }

    override fun delectComment(commentId: Long): Observable<String> {
        return api.delectComment(commentId).toIoAndMain()
    }


    override fun getToken(fileKey: TokenRequest): Observable<String> {
        return api.getToken(fileKey).toIoAndMain()
    }

    override fun publishGifts(): Observable<List<GiftModel>> {
        return api.publishGifts().toIoAndMain()
    }

    override fun follow(toUserId: Long): Observable<Int> {
        return api.follow(toUserId).toIoAndMain()
    }

    override fun attentionUsers(listPage: ResponseListPage): Observable<List<UserLiteModel>> {
        return api.attentionUsers(listPage).toIoAndMain()
    }

    override fun createOrder(order: ResponseOrder): Observable<OrderModel> {
        return api.createOrder(order).toIoAndMain()
    }

    override fun searchList(pageList: ResponseSearch): Observable<List<UserLiteModel>> {
        return api.searchList(pageList).toIoAndMain()
    }


    override fun bannerInfos(): Observable<List<BannerModel>> {
        return api.bannerInfos().toIoAndMain()
    }

    override fun greenBanner(): Observable<List<BannerModel>> {
        return api.greenBanner().toIoAndMain()
    }

    override fun removeBindInfo(loginType: Int): Observable<String> {
        return api.removeBindInfo(loginType).toIoAndMain()
    }

    override fun accountInfo(): Observable<AccountInfo> {
        return api.accountInfo().toIoAndMain()
    }

    override fun delectStory(id: Long): Observable<String> {
        return api.delectStory(id).toIoAndMain()
    }

    override fun commentLike(commentId: Long): Observable<String> {
        return api.commentLike(commentId).toIoAndMain()
    }

    override fun commentDelect(id: Long): Observable<String> {
        return api.commentDelect(id).toIoAndMain()
    }

    override fun pullBlackUser(userId: Long): Observable<String> {
        return api.pullBlackUser(userId).toIoAndMain()
    }

    override fun userWallet(): Observable<UserWallet> {
        return api.userWallet().toIoAndMain()
    }

    override fun searchRecommendUsers(listPage: ResponseListPage): Observable<List<UserLiteModel>> {
        return api.searchRecommendUsers(listPage).toIoAndMain()
    }

    override fun downLoadUrl(url: DownLoadRespone): Observable<String> {
        return api.downLoadUrl(url).toIoAndMain()
    }


    override fun addLog(response: ResponseLogModel): Observable<String> {
        return api.addLog(response).toIoAndMain()
    }

    override fun recommandOrHotMusics(response: ResponseMusicList): Observable<List<StrategyModel>> {
        return api.recommandOrHotMusics(response).toIoAndMain()
    }

    override fun getBanner(bannerRequest: BannerRequest): Observable<List<BannerModel>> {
        var brand = SPUtils.getInstance().getString(SPConstant.DEVICE_BRAND, null)
        bannerRequest.phoneTyep = brand
        return api.getBanner(bannerRequest).toIoAndMain()
    }

    override fun getStrategyList(responseListPage: ResponseListPage): Observable<List<StrategyModel>> {
        return api.getStrategyList(responseListPage).toIoAndMain()
    }

    override fun getRecommendList(responseListPage: ResponseListPage): Observable<List<StrategyModel>> {
        return api.getRecommendList(responseListPage).toIoAndMain()
    }

    override fun getAppList(responseListPage: ResponseListPage): Observable<List<AppModel>> {
        return api.getAppList(responseListPage).toIoAndMain()
    }

    override fun getGoodsList(): Observable<List<GoodsModel>> {
        return api.getGoodsList().toIoAndMain()
    }


    override fun sign(): Observable<String> {
        return api.sign().toIoAndMain()
    }

    override fun paySign(charge: ResponseOrder): Observable<String> {
        return api.paySign(charge).toIoAndMain()
    }

    override fun getUserInfo(): Observable<UserInfo> {
        return api.getUserInfo().toIoAndMain()
    }

}
