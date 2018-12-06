package com.moli.module.net.http.download

import android.util.Log
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

import java.io.IOException

/**
 * Created by lijilong on 06/06.
 * 带进度条，下载请求体
 */

class DownloadResponseBody(
    private val responseBody: ResponseBody,
    private val progressListener: (progress: Int) -> Unit
) :
    ResponseBody() {

    // BufferedSource 是okio库中的输入流，这里就当作inputStream来使用。
    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource!!
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            internal var totalBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0L
                Log.e(
                    "download",
                    "read: " + (totalBytesRead * 100 / responseBody.contentLength()).toInt() + "totalRead=" + totalBytesRead + ",total=" + responseBody.contentLength()
                )
                if (bytesRead != -1L) {
                    progressListener((totalBytesRead * 100 / responseBody.contentLength()).toInt())
                }

                return bytesRead
            }
        }

    }
}
