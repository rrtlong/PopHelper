package com.moli.module.net.http.provider.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.utils.rx.toIoAndMain
import com.moli.module.net.http.RetrofitUtils
import com.moli.module.net.http.api.APIDownload
import com.moli.module.net.http.provider.APIDownloadService
import com.moli.module.router.RewardRouter
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/6 18:53
 * 修改人：lijilong
 * 修改时间：2018/12/6 18:53
 * 修改备注：
 * @version
 */
@Route(path = RewardRouter.Service.DOWNLOAD)
class APIDownloadServiceImpl : APIDownloadService {
    private lateinit var apiDownload: APIDownload

    override fun init(context: Context?) {
        apiDownload = RetrofitUtils.downloadRetrofit.create(APIDownload::class.java)
    }

    override fun downloadAPK(url: String): Observable<ResponseBody> {
        return apiDownload.downloadAPK(url).toIoAndMain()
    }
}
