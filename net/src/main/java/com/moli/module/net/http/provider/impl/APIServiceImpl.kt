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
import timber.log.Timber

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

    override fun getVersion(request: VersionRequest): Observable<VersionModel> {
        return api.getVersion(request).toIoAndMain()
    }

    //helper/user/getPhoneAuth
    override fun getPhoneCode(url: String, codeRequest: CodeRequest): Observable<String> {
        var url = RetrofitUtils.domainName + "helper/user/getPhoneAuth"
        return api.getPhoneCode(url, codeRequest).toIoAndMain()
    }


    override fun login(url: String, login: ResonseLogin): Observable<UserInfo> {
        var url = RetrofitUtils.domainName + "helper/user/register"
        return api.login(url, login).toIoAndMain()
    }

    override fun getBanner(url: String, bannerRequest: BannerRequest): Observable<List<BannerModel>> {
        var brand = SPUtils.getInstance().getString(SPConstant.DEVICE_BRAND, null)
        bannerRequest.phoneTyep = brand
        var url = RetrofitUtils.domainName + "helper/config/bannerContent"
        return api.getBanner(url, bannerRequest).toIoAndMain()
    }

    override fun getStrategyList(url: String, responseListPage: ResponseListPage): Observable<List<StrategyModel>> {
        var url = RetrofitUtils.domainName + "helper/strategy/getStrategyList"
        return api.getStrategyList(url, responseListPage).toIoAndMain()
    }

    override fun getRecommendList(url: String, responseListPage: ResponseListPage): Observable<List<StrategyModel>> {
        var url = RetrofitUtils.domainName + "helper/strategy/getRecommendList"
        return api.getRecommendList(url, responseListPage).toIoAndMain()
    }

    override fun getAppList(url: String, responseListPage: ResponseListPage): Observable<List<AppModel>> {
        var url = RetrofitUtils.domainName + "helper/strategy/getSortRecommendList"
        return api.getAppList(url, responseListPage).toIoAndMain()
    }

    override fun getGoodsList(url: String): Observable<List<GoodsModel>> {
        var url = RetrofitUtils.domainName + "helper/recharge/rechargeConfig"
        return api.getGoodsList(url).toIoAndMain()
    }


    override fun sign(url: String): Observable<String> {
        var url = RetrofitUtils.domainName + "helper/user/sign"
        return api.sign(url).toIoAndMain()
    }

    override fun paySign(url: String, charge: ResponseOrder): Observable<String> {
        var url = RetrofitUtils.domainName + "helper/recharge/createBilling"
        return api.paySign(url, charge).toIoAndMain()
    }

    override fun getUserInfo(url: String,request: CodeRequest): Observable<UserInfo> {
        var url = RetrofitUtils.domainName + "helper/user/getUserInfo"
        return api.getUserInfo(url,request).toIoAndMain()
    }


}
