package com.moli.module.net.http

import com.moli.module.net.http.converter.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.moli.module.net.BuildConfig
import com.moli.module.net.http.pingxx.PingXXCommonInterceptor
import com.moli.module.net.http.pingxx.converter.PingXXGsonConverterFactory
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import timber.log.Timber
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * 项目名称：Aletter
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/7/31 下午1:40
 * 修改人：yuliyan
 * 修改时间：2018/7/31 下午1:40
 * 修改备注：
 * @version
 */
object RetrofitUtils {
    @JvmField
    var domainName: String = BuildConfig.DOMAIN_NAME
//    var domainName: String = BuildConfig.DOMAIN_NAME + "moli_helper/helper/"

    val retrofit: Retrofit by lazy {
        val gson = GsonBuilder().setLenient().create()
        val fastJsonConverterFactory = GsonConverterFactory.create(gson)
        val rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create()
        val builder = OkHttpBuilder.okHttpBuilder
        builder.addInterceptor(CommonInterceptor())
        if (BuildConfig.LOG_DEBUG) {
            builder.addInterceptor(HttpLoggerInterceptor())
        }
        try {
            val trustManager = object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
            val trustAllCerts = arrayOf<TrustManager>(trustManager)
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            builder.sslSocketFactory(sslSocketFactory, trustManager)
        } catch (e: Exception) {
            Timber.e(e)
        }
        val okHttpClient = RetrofitUrlManager.getInstance().with(builder).build()

        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(fastJsonConverterFactory)
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .baseUrl(domainName)
            .build()
    }


    var domainNamePing: String = BuildConfig.PING_DOMAIN_NAME
    val pingRetrofit: Retrofit by lazy {
        val gson = GsonBuilder().setLenient().create()
        val fastJsonConverterFactory = PingXXGsonConverterFactory.create(gson)
        val rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create()
        val builder = OkHttpBuilder.okHttpBuilder
        builder.addInterceptor(PingXXCommonInterceptor())
        if (BuildConfig.LOG_DEBUG) {
            builder.addInterceptor(HttpLoggerInterceptor())
        }
        try {
            val trustManager = object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
            val trustAllCerts = arrayOf<TrustManager>(trustManager)
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            builder.sslSocketFactory(sslSocketFactory, trustManager)
        } catch (e: Exception) {
            Timber.e(e)
        }
        val okHttpClient = RetrofitUrlManager.getInstance().with(builder).build()

        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(fastJsonConverterFactory)
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .baseUrl(domainNamePing)
            .build()
    }
}
