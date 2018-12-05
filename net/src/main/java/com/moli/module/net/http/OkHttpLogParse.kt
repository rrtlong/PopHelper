package com.moli.module.net.http

import android.text.TextUtils
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import timber.log.Timber
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset

/**
 * 项目名称：Jasmine
 * 类描述：
 * 创建人：liujun
 * 创建时间：2018/1/4 17:19
 * 修改人：liujun
 * 修改时间：2018/1/4 17:19
 * 修改备注：
 * @version
 */
// 换行符
private val BR = System.getProperty("line.separator")

// 空格
private const val SPACE = "\t"

private val UTF8 = Charset.forName("UTF-8")


fun parseString(headers: Headers): String {
    val builder = StringBuilder(headers.javaClass.simpleName)
    builder.append(" [").append(BR)
    for (name in headers.names()) {
        builder.append(name).append("=").append(headers.get(name)).append(BR)
    }
    return builder.append(']').toString()
}


fun parseString(response: Response?): String? {
    if (response == null) {
        return null
    }
    val builder = StringBuilder()
    builder.append("code = ").append(response.code()).append(BR)
    builder.append("isSuccessful = ").append(response.isSuccessful).append(BR)
    builder.append("url = ").append(response.request().url()).append(BR)
    builder.append("message = ").append(response.message()).append(BR)
    builder.append("protocol = ").append(response.protocol()).append(BR)
    builder.append("header = ").append(parseString(response.headers()))
            .append(BR)
    try {
        val body = response.body()
        if (body != null) {
            builder.append("body = ").append(body.string()).append(BR)
        }
    } catch (e: IOException) {
        Timber.e(e)
    }

    return builder.toString()
}

fun parseString(request: Request): String {
    val builder = StringBuilder()
    builder.append("method = ").append(request.method()).append(BR)
    builder.append("url = ").append(request.url().toString()).append(BR)
    builder.append("header = ").append(parseString(request.headers()))
            .append(BR)
    try {
        val requestBody = request.body()
        if (requestBody != null) {
            val charset: Charset?
            val mediaType = requestBody.contentType()
            if (mediaType == null) {
                return builder.toString()
            } else {
                charset = mediaType.charset(UTF8)
                builder.append("requestBody's contentType  = ").append(mediaType.toString())
                        .append(BR)
            }
            if (isText(mediaType)) {
                builder.append("requestBody's content  = ").append(bodyToString(request))
                        .append(BR)
            } else {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                if (isPlaintext(buffer)) {
                    builder.append("requestBody's content  = ")
                            .append(buffer.readString(charset ?: UTF8))
                            .append(BR)
                }
            }
        }
    } catch (e: IOException) {
        Timber.e(e)
    }

    return builder.toString()

}

private fun isText(mediaType: MediaType): Boolean {
    if (mediaType.type() != null && TextUtils.equals("text", mediaType.type())) {
        return true
    }
    if (mediaType.subtype() != null) {
        if (TextUtils.equals(mediaType.subtype(), "json") ||
                TextUtils.equals(mediaType.subtype(), "xml") ||
                TextUtils.equals(mediaType.subtype(), "html") ||
                TextUtils.equals(mediaType.subtype(), "webviewhtml")) {
            return true
        }
    }
    return false
}

private fun bodyToString(request: Request): String {
    try {
        val copy = request.newBuilder().build()
        val buffer = Buffer()
        val body = copy.body()
        body?.writeTo(buffer)
        return buffer.readUtf8()
    } catch (e: IOException) {
        return "something error when show requestBody."
    }

}


/**
 * Returns true if the body in question probably contains human readable
 * text. Uses a small sample
 * of code points to detect unicode control characters commonly used in
 * binary file signatures.
 */
private fun isPlaintext(buffer: Buffer): Boolean {
    try {
        val prefix = Buffer()
        val byteCount = if (buffer.size() < 64) buffer.size() else 64
        buffer.copyTo(prefix, 0, byteCount)
        for (i in 0..15) {
            if (prefix.exhausted()) {
                break
            }
            val codePoint = prefix.readUtf8CodePoint()
            if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                return false
            }
        }
        return true
    } catch (e: EOFException) {
        return false // Truncated UTF-8 sequence.
    }

}
