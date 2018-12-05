package com.moli.module.net.http

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * 项目名称：Aletter
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/7/31 下午2:05
 * 修改人：yuliyan
 * 修改时间：2018/7/31 下午2:05
 * 修改备注：
 * @version
 */
object OkHttpBuilder {
    /**
     * 超时时间
     */
    private val DEFAULT_TIMEOUT = 5L
    private val DEFAULT_READ_TIMEOUT = 5L
    private val DEFAULT_WRITE_TIMEOUT = 5L

    val okHttpBuilder: OkHttpClient.Builder by lazy {
        return@lazy OkHttpClient.Builder().connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
    }

}
