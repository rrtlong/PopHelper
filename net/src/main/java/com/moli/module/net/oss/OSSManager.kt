package com.moli.module.net.oss

import com.alibaba.sdk.android.oss.*
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import com.alibaba.sdk.android.oss.model.*
import com.blankj.utilcode.util.Utils
import com.moli.module.framework.utils.rx.RetryWithDelayFlowable
import com.moli.module.framework.utils.rx.subscribeOnIo
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * 项目名称：Platformer
 * 类描述：OSS的扩展方法
 * 创建人：liujun
 * 创建时间：2017/9/26 09:44
 * 修改人：liujun
 * 修改时间：2017/9/26 09:44
 * 修改备注：
 * @version
 */
object OSSManager {
    /**
     * 默认的bucket
     */
    var BUCKET_NAME = "moli2017"

    private var AccessKeySecret = "R8Pf6CLZ4I2nZDbuENGfrhIWe3huA2"
    private var AccessKeyId = "LTAIB4bui61O93Aj"
    private var SecurityToken = ""

    private val oss: OSS by lazy {
        val conf = ClientConfiguration()
        conf.connectionTimeout = 15 * 1000 //连接超时，默认15秒
        conf.socketTimeout = 15 * 1000 // socket超时，默认15秒
        conf.maxConcurrentRequest = 5 // 最大并发请求书，默认5个
        conf.maxErrorRetry = 2
        val credentialProvider = OSSStsTokenCredentialProvider(AccessKeyId, AccessKeySecret, SecurityToken)
        return@lazy OSSClient(
                Utils.getApp(),
                "http://file.xsawe.top",
                //                OSSFCProvider(),
                credentialProvider,
                conf)
    }

    /**
     * 获取OSS公开可访问的URL
     *
     * @param objectKey  Object名
     * @param bucketName 存储Object的Bucket名
     * @return
     */
    fun getUrl(objectKey: String, bucketName: String = BUCKET_NAME): String {
        return oss.presignPublicObjectURL(bucketName, objectKey)
    }

    /**
     * 签名Object的访问URL，以便授权第三方访问,有效时间默认为10小时
     *
     * @param objectKey Object名
     * @param bucketName 存储Object的Bucket名
     * @return
     * @throws ClientException
     */
    fun getPrivateUrl(objectKey: String, expiredTimeInSeconds: Long = 10 * 60 * 60,
                      bucketName: String = BUCKET_NAME): Single<String> {
        return Single.unsafeCreate<String> {
            oss.presignConstrainedObjectURL(bucketName, objectKey, expiredTimeInSeconds)
        }.subscribeOn(Schedulers.io())
    }

    /**
     * 同步上传文件
     *
     * @param objectKey Object名
     * @param uploadFilePath 本地文件路径，上传到OSS
     * @param bucketName 存储Object的Bucket名
     * @param contentType 文件 ContentType 类型
     * @return 图片URL
     */
    fun syncPutObject(
            objectKey: String,
            uploadFilePath: String,
            bucketName: String = BUCKET_NAME,
            contentType: String = ""
    ): String? {
        val put = PutObjectRequest(bucketName, objectKey, uploadFilePath)
        setMD5Metadata(put, contentType)
        val result = oss.putObject(put)
        if (result != null && result.statusCode == 200) {
            return getUrl(objectKey)
        }
        return null
    }

    /**
     * 没有进度回调的异步上传文件
     *
     * @param objectKey Object名
     * @param uploadFilePath 本地文件路径，上传到OSS
     * @param bucketName 存储Object的Bucket名
     * @param contentType 文件 ContentType 类型
     * @param timeout 上传文件的超时时间 默认60秒 单位秒
     * @return 请求的ObjectKey
     */
    fun putObject(
            objectKey: String,
            uploadFilePath: String,
            bucketName: String = BUCKET_NAME,
            contentType: String = "",
            timeout: Long = 60
    ): Single<PutObjectRequest> {
        val put = PutObjectRequest(bucketName, objectKey, uploadFilePath)

        setMD5Metadata(put, contentType)
        Timber.e(put.toString())
        Timber.e("bucketName=%s,\nobjectKey=%s,\nuploadFilePath=%s\n", bucketName, objectKey, uploadFilePath)
        return Single.create<PutObjectRequest> { e: SingleEmitter<PutObjectRequest> ->
            oss.asyncPutObject(
                    put,
                    object : OSSCompletedCallback<PutObjectRequest, PutObjectResult> {
                        override fun onSuccess(request: PutObjectRequest, result: PutObjectResult) {
                            Timber.i("文件上传成功：%s", request.objectKey)
                            e.onSuccess(request)
                        }

                        override fun onFailure(request: PutObjectRequest,
                                               clientException: ClientException, serviceException: ServiceException) {
                            Timber.e(clientException)
                            Timber.e(serviceException)
                            e.onError(clientException)
                        }
                    }
            )
        }.subscribeOnIo()
                .retryWhen(RetryWithDelayFlowable(2, 3000))
                .timeout(timeout, TimeUnit.SECONDS)
    }

    /**
     * 有进度回调的异步上传文件
     *
     * @param objectKey Object名
     * @param uploadFilePath 本地文件路径，上传到OSS
     * @param throttleWithTimeout  节流阀与超时 默认50毫秒回调一次进度
     * @param bucketName 存储Object的Bucket名
     * @param contentType 文件 ContentType 类型
     * @param timeout 上传文件的超时时间 默认60秒 单位秒
     * @return 上传进度:Triple<PutObjectRequest, Long, Long> 对应 request, currentSize, totalSize
     * Triple.first : PutObjectRequest
     * Triple.second : currentSize
     * Triple.third : totalSize
     */
    fun putObjectProgress(
            objectKey: String,
            uploadFilePath: String,
            throttleWithTimeout: Long = 50,
            bucketName: String = BUCKET_NAME,
            contentType: String = "",
            timeout: Long = 60
    ): Flowable<Triple<PutObjectRequest, Long, Long>> {
        return Flowable.create<Triple<PutObjectRequest, Long, Long>>({ emitter: FlowableEmitter<Triple<PutObjectRequest, Long, Long>> ->
            val put = PutObjectRequest(bucketName, objectKey, uploadFilePath)
            setMD5Metadata(put, contentType)
            put.setProgressCallback { request, currentSize, totalSize ->
                emitter.onNext(Triple(request, currentSize, totalSize))
            }
            oss.asyncPutObject(put, object : OSSCompletedCallback<PutObjectRequest, PutObjectResult> {
                override fun onSuccess(request: PutObjectRequest, result: PutObjectResult) {
                    Timber.i("文件上传成功：%s", request.objectKey)
                    emitter.onComplete()
                }

                override fun onFailure(request: PutObjectRequest,
                                       clientException: ClientException, serviceException: ServiceException) {
                    Timber.e(clientException)
                    Timber.e(serviceException)
                    emitter.onError(clientException)
                }
            })
        }, BackpressureStrategy.LATEST)
                .subscribeOnIo()
                .throttleLast(throttleWithTimeout, TimeUnit.MILLISECONDS)
                .timeout(timeout, TimeUnit.SECONDS)
                .retryWhen(RetryWithDelayFlowable(2, 3000))
    }

    fun setMD5Metadata(put: PutObjectRequest, contentType: String, contentMD5: String? = null): ObjectMetadata {
        val metadata = ObjectMetadata()
        if (contentType.isNotBlank()) {
            metadata.contentType = contentType
        }
        if (contentMD5.isNullOrBlank()) {
            put.crC64 = OSSRequest.CRC64Config.YES
        } else {
            metadata.contentMD5 = contentMD5
        }
        put.metadata = metadata
        return metadata
    }

    /**
     * 异步删除文件
     */
    fun asyncDelete(objectKey: String, bucketName: String = BUCKET_NAME): Single<Boolean> {
        return if (objectKey.isBlank()) {
            Single.error(RuntimeException("objectKey is null"))
        } else {
            val delete = DeleteObjectRequest(bucketName, objectKey)
            Single.create<Boolean> {
                oss.asyncDeleteObject(delete, object : OSSCompletedCallback<DeleteObjectRequest, DeleteObjectResult> {
                    override fun onSuccess(request: DeleteObjectRequest, result: DeleteObjectResult) {
                        it.onSuccess(true)
                    }

                    override fun onFailure(request: DeleteObjectRequest, clientException: ClientException, serviceException: ServiceException) {
                        it.onError(clientException)
                    }
                })
            }.subscribeOnIo().retryWhen(RetryWithDelayFlowable(2, 3000))
        }
    }

    /**
     * 获取图片上传到OSS的objectKey
     */
    fun getImageObjectKey(): String {
        return "aletter/image/android_${System.currentTimeMillis()}${UUID
                .randomUUID()}" +
                ".jpg"
    }

    /**
     * 获取音频上传到OSS的objectKey
     */
    fun getAudioObjectKey(): String {
        return "aletter/audio/android_${System.currentTimeMillis()}${UUID
                .randomUUID()}.mp3"
    }


}





