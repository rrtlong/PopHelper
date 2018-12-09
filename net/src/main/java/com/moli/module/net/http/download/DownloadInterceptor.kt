package com.moli.module.net.http.download


import com.moli.module.model.base.ProgressModel
import com.moli.module.model.constant.EventConstant
import okhttp3.Interceptor
import okhttp3.Response
import org.simple.eventbus.EventBus
import timber.log.Timber
import java.io.IOException

/**
 * Created by lijilong on 06/06.
 */

class DownloadInterceptor() : Interceptor {

    lateinit var downloadUrl: String
    fun progress(progress: Int) {
        EventBus.getDefault().post(ProgressModel(downloadUrl, progress), EventConstant.DOWNLOAD_PROGRESS)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        var url = chain.request().url().toString()
        downloadUrl = url
//        var index = url.indexOf("?")
//        downloadUrl = if (index > -1) url.substring(0, index) else url

        return response.newBuilder()
            .body(DownloadResponseBody(response.body()!!, this@DownloadInterceptor::progress))
            .build()
    }
}
