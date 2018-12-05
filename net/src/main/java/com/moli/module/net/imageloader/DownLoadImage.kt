package com.moli.module.net.imageloader

import android.net.Uri
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.executors.UiThreadImmediateExecutorService
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import java.util.concurrent.Executor


/**
 * 项目名称：Platformer
 * 类描述：使用Fresco下载图片
 * 创建人：liujun
 * 创建时间：2017/10/24 18:09
 * 修改人：liujun
 * 修改时间：2017/10/24 18:09
 * 修改备注：
 * @version
 */

/**
 * 在异步线程回调
 */
fun downLoadForCallerThread(url: String, subscriber: BaseBitmapDataSubscriber) {
    val request = getImageRequest(url)
    downLoadImage(request, subscriber, CallerThreadExecutor.getInstance())
}


/**
 * 在UI线程回调
 */
fun downLoadForUiThread(url: String, subscriber: BaseBitmapDataSubscriber) {
    val request = getImageRequest(url)
    downLoadImage(request, subscriber, UiThreadImmediateExecutorService
            .getInstance())
}


/**
 * 在异步线程回调
 */
fun downLoadForCallerThread(request: ImageRequest, subscriber: BaseBitmapDataSubscriber) {
    downLoadImage(request, subscriber, CallerThreadExecutor.getInstance())
}


/**
 * 在UI线程回调
 */
fun downLoadForUiThread(request: ImageRequest, subscriber: BaseBitmapDataSubscriber) {
    downLoadImage(request, subscriber, UiThreadImmediateExecutorService
            .getInstance())
}


fun downLoadImage(request: ImageRequest?, subscriber: BaseBitmapDataSubscriber, executor: Executor) {
    if (request == null) {
        return
    }
    val imagePipeline = Fresco.getImagePipeline()
    val dataSource = imagePipeline
            .fetchDecodedImage(request, null)
    dataSource.subscribe(subscriber, executor)
}


private fun getImageRequest(url: String, choice: ImageRequest.CacheChoice = ImageRequest.CacheChoice.DEFAULT): ImageRequest? {
    if (url.isBlank()) {
        return null
    }
    return ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setCacheChoice(choice).build()
}
