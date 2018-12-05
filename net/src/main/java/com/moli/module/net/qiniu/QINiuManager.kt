package com.moli.module.net.qiniu

import com.moli.module.framework.utils.rx.RetryWithDelayFlowable
import com.moli.module.framework.utils.rx.subscribeOnIo
import com.qiniu.android.common.AutoZone
import com.qiniu.android.storage.Configuration
import com.qiniu.android.storage.NetReadyHandler
import com.qiniu.android.storage.UpCancellationSignal
import com.qiniu.android.storage.UpProgressHandler
import com.qiniu.android.storage.UploadManager
import com.qiniu.android.storage.UploadOptions
import io.reactivex.*

import java.util.UUID
import java.util.concurrent.TimeUnit

import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/9/14 下午8:00
 * 修改人：yuliyan
 * 修改时间：2018/9/14 下午8:00
 * 修改备注：
 */
object QINiuManager  {
    /**
     * 默认七牛的bucket
     */
    private val BUCKET_NAME = "reward"

    private val APP_KEY = "wpsrY8zoU9I2mEXp-cjmkRGb0flhGdBCxA0x-4-X"

    private val SECRET_KEY = "GTS8zDKe5ahAw3HZxQkfPJkIvITWJTqg9itgOiB2"

    private val DOMAIN_SPEED_UP = "http://file.urmoli.com"

    private var token: String? = null

    private val uploadManager: UploadManager by lazy {
        val configuration = object : Configuration.Builder() {}
                .chunkSize(512 * 1024) //// 分片上传时，每片的大小。 默认256K
                .chunkSize(512 * 1024) //// 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .zone(AutoZone.autoZone).build()
        return@lazy UploadManager(configuration)

    }

    /**
     * 异步上传文件不带进度条
     * @param objectKey:文件key
     *
     */
    fun putObject(objectKey: String, uploadFilePath: String, mimeType: String, timeout: Long?): Single<Any> {

        return Single.create<Any> { emitter ->
            if (token == null) {
                token = Auth.create(APP_KEY, SECRET_KEY).uploadToken(BUCKET_NAME)
            }
            uploadManager.put(uploadFilePath, objectKey, token,
                    { key, info, response ->
                        Timber.i("key=%s,\n info=%s,\n response=%s", key, info, response)
                        emitter.onSuccess(key)
                    },
                    null)

        }.subscribeOnIo()
                .retryWhen(RetryWithDelayFlowable(2, 3000))
                .timeout(timeout!!, TimeUnit.SECONDS)

    }

    /**
     *  异步上传文件带进度条
     */

    fun putObjectProgress(objectKey: String, uploadFilePath: String, mimeType: String, timeout: Long?): Flowable<Triple<Any, Long, Long>> {
        return Flowable.create<Triple<Any, Long, Long>>({ emitter: FlowableEmitter<Triple<Any, Long, Long>> ->
            if (token == null) {
                token = Auth.create(APP_KEY, SECRET_KEY).uploadToken(BUCKET_NAME)
            }
            /**
             * 配置参数
             */
            val map = mutableMapOf<String, String>()

            uploadManager.put(uploadFilePath, objectKey, token,
                    { key, info, response ->
                        Timber.i("key=%s,\n info=%s,\n response=%s", key, info, response)
                        emitter.onComplete()
                    }, UploadOptions(map, mimeType, true,
                    UpProgressHandler { key, percent -> },
                    UpCancellationSignal { false },
                    NetReadyHandler { }
            )
            )

        }, BackpressureStrategy.LATEST).subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelayFlowable(2, 3000))
                .timeout(timeout!!, TimeUnit.SECONDS)

    }


    /**
     * 获取图片上传到OSS的objectKey
     */

    fun getImageObjectKey(): String {
        return "reward/image/android_${System.currentTimeMillis()}${UUID
                .randomUUID()}" +
                ".jpg"
    }

    /**
     * 获取音频上传到OSS的objectKey
     */
    fun getVideoObjectKey(): String {
        return "reward/video/android_${System.currentTimeMillis()}${UUID
                .randomUUID()}.mp4"
    }

    fun getDomain():String{
        return DOMAIN_SPEED_UP
    }

}
