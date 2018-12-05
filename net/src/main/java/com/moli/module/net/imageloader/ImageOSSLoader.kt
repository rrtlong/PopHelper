package com.moli.module.net.imageloader

import android.net.Uri
import android.support.annotation.DrawableRes
import com.facebook.common.util.UriUtil
import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.moli.module.net.oss.OSSManager
import info.liujun.image.LJImageRequestBuilder
import timber.log.Timber
import java.io.File
import java.util.*

const val OSS_CN_ZHANGJIAKOU = "oss-cn-zhangjiakou"
const val OSS_CN_XSAWE = "file.xsawe.top"
const val OSS_PARAMETER = "?x-oss-process=image"
const val BLUR_STYLE = "/blur,r_%s,s_%s"
const val IMAGE_STYLE = "/resize,m_mfit"
const val IMAGE_W_SIZE = ",w_"
const val IMAGE_H_SIZE = ",h_"
const val WEBP_SUFFIX = "/format,webp"
const val DEFAULT_SUFFIX = "" //默认不填格式，是按原图格式返回
const val IMAGE_SUFFIX: String = WEBP_SUFFIX

/**
 * 项目名称：Platformer
 * 类描述：
 * 创建人：yuliuyu
 * 创建时间：2017/10/11 19:54
 * 修改人：yuliuyu
 * 修改时间：2017/10/11 19:54
 * 修改备注：
 * @version
 */
@JvmOverloads
fun SimpleDraweeView.loadImage(url: String?, width: Int = -1, height: Int = -1, controllerListener: ControllerListener<ImageInfo>? = null) {
    if (url.isNullOrBlank()) {
        this.setImageRequest(null)
        return
    }
    val uri = parseUri(url)
    if (checkOtherScheme(uri)) {
        FrescoImageLoader.loadImage(this, uri, uri, width, height, controllerListener)
    } else {
        val newUrl: String = getImageUrl(uri, width, height)
        FrescoImageLoader.loadImage(this, newUrl, newUrl, width, height, controllerListener)
    }
}

private fun parseUri(url: String?): String {
    return when {
        url.isNullOrBlank() -> ""
        url!!.startsWith("/") -> "file://$url"
        else -> url
    }
}


fun getImageUrl(url: String?, width: Int, height: Int): String {
    return when {
    //不是http或者https开头的地址，认为是OSS的objectKey，取完整URL做裁剪
        url?.isStartsWithHttp() == false -> urlWithWidthAndHeight(OSSManager.getUrl(url), width, height)
    //是Oss地址，增加裁剪参数
        url?.isOssImage() == true -> urlWithWidthAndHeight(url, width, height)
        else -> url ?: String()
    }
}

@JvmOverloads
fun SimpleDraweeView.loadBlurImage(url: String?, width: Int = -1, height: Int = -1) {
    if (url.isNullOrBlank()) return
    val uri = parseUri(url)
    val newUrl: String = if (!uri.isStartsWithHttp()) {
        getBlurUrl(OSSManager.getUrl(uri), width, height)
    } else if (uri.isOssImage()) {
        getBlurUrl(uri, width, height)
    } else {
        uri
    }

    if (newUrl.isOssImage()) {
        Timber.i("loadBlurImage: %s", newUrl)
        FrescoImageLoader.loadImage(this, newUrl, newUrl, width, height)
    } else {
        loadOtherBlurImage(newUrl, width, height)
    }
}

fun SimpleDraweeView.loadOtherBlurImage(url: String?, width: Int = -1, height: Int = -1, blurRadius: Int = 25) {
    if (url.isNullOrBlank()) return
    val resizeOptions: ResizeOptions
    val w: Int
    val h: Int
    if (checkoutWH(width, height)) {
        if (width > 2000 || height > 2000) {
            w = width / 6
            h = height / 6
        } else if (width > 1000 || height > 1000) {
            w = width / 5
            h = height / 5
        } else {
            w = width / 4
            h = height / 4
        }
    } else {
        h = 150
        w = h
    }
    resizeOptions = ResizeOptions(w, h)
    val request = LJImageRequestBuilder
            .newBuilderWithSource(Uri.parse(url), url)
    request.imageRequestBuilder
            .setPostprocessor(IterativeBoxBlurPostProcessor(blurRadius))
            .setProgressiveRenderingEnabled(true).setResizeOptions(resizeOptions).build()
    Timber.i("loadOtherBlurImage w:%s h:%s url: %s ", w, h, url)
    FrescoImageLoader.loadImage(this, request.build())
}

/**
 * 加载本地文件图片
 * @param file 图片文件
 */
@JvmOverloads
fun SimpleDraweeView.loadFileImage(file: File, width: Int = -1, height: Int = -1) {
    loadFileImage(file.absolutePath, width, height)
}

/**
 * 加载本地文件图片
 * @param filePath 文件路径
 */
@JvmOverloads
fun SimpleDraweeView.loadFileImage(filePath: String, width: Int = -1, height: Int = -1) {
    val uri = parseUri(filePath)
    Timber.i("loadFileImage w:%s h:%s url: %s ", width, height, uri)
    FrescoImageLoader.loadImage(this, uri, uri, width, height)
}

/**
 * 加载资源图片
 */
fun SimpleDraweeView.loadResImage(@DrawableRes resId: Int) {
    this.setImageURI(UriUtil.getUriForResourceId(resId), null)
}

/**
 * 加载资源图片。并自动播放动图
 */
fun SimpleDraweeView.loadResAutoPlayAnim(@DrawableRes resId: Int) {
    val imageRequest = ImageRequestBuilder.newBuilderWithResourceId(resId).build()
    val draweeController = FrescoImageLoader.getDraweeController(this, imageRequest).build()
    this.controller = draweeController
}

fun urlWithWidthAndHeight(url: String, width: Int, height: Int): String {
    val urlImage = StringBuilder(url)
    urlImage.append(OSS_PARAMETER)
    if (checkoutWH(width, height)) {
        urlImage.append(getImageStyle(width, height))
    }
    urlImage.append(IMAGE_SUFFIX)
    return urlImage.toString()
}

/**
 * 使用图片控件的宽高对阿里OSS上的图片URL进行处理，添加模糊效果
 *
 * @param width 图片宽度
 * @param height 图片高度
 */
fun getBlurUrl(url: String, width: Int, height: Int): String {
    val urlImage = StringBuilder(url)
    urlImage.append(OSS_PARAMETER)
    if (checkoutWH(width, height)) {
        urlImage.append(getImageStyle(width, height))
    }
    urlImage.append(getImageBlurStyle())
    urlImage.append(IMAGE_SUFFIX)
    return urlImage.toString()
}

fun String.isOssImage(): Boolean {
    return this.contains(OSS_CN_XSAWE) || this.contains(OSS_CN_ZHANGJIAKOU)
}

/**
 * 获取图片缩放样式
 *
 * @param w 指定目标缩略图的宽度。
 * @param h 指定目标缩略图的高度。
 */
fun getImageStyle(w: Int, h: Int): String {
    return if (w <= 0 && h <= 0) {
        String()
    } else {
        val str = StringBuilder(IMAGE_STYLE)
        if (w > 0) {
            str.append(IMAGE_W_SIZE).append(w)
        }
        if (h > 0) {
            str.append(IMAGE_H_SIZE).append(h)
        }
        str.toString()
    }
}


/**
 * 获取模糊图片样式
 *
 * @param r 模糊半径
 * @param s 正态分布的标准差
 * @return OSS 图片处理参数
 */
fun getImageBlurStyle(r: Int = 30, s: Int = 20): String {
    return String.format(Locale.getDefault(), BLUR_STYLE, r, s)
}

private fun checkoutWH(w: Int, h: Int): Boolean {
    return w > 1 || h > 1
}


fun String.isStartsWithHttp(): Boolean {
    return this.startsWith("http://") || this.startsWith("https://")
}

/**
 * 如果图片为以下类型，不进行URL处理
 */
fun checkOtherScheme(url: String): Boolean {
    return url.startsWith("file:") || url.startsWith("content:") || url.startsWith("asset:") || url.startsWith("res:")
}
