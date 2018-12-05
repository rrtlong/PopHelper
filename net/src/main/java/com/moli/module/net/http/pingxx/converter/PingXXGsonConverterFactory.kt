package com.moli.module.net.http.pingxx.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moli.module.net.http.converter.GsonRequestBodyConverter
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * 项目名称：Aletter
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/7/31 下午1:43
 * 修改人：yuliyan
 * 修改时间：2018/7/31 下午1:43
 * 修改备注：
 * @version
 */
class PingXXGsonConverterFactory private constructor(private val gson: Gson): Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return PingXXGsonResponseBodyConverter(gson, adapter, type)
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>?, methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(gson, adapter)
    }

    companion object {
        /**
         * Create an instance using a default [Gson] instance for conversion. Encoding to JSON and
         * decoding from JSON (when no charset is specified by a header) will use UTF-8.
         */
        fun create(): PingXXGsonConverterFactory {
            return create(Gson())
        }

        /**
         * Create an instance using `gson` for conversion. Encoding to JSON and
         * decoding from JSON (when no charset is specified by a header) will use UTF-8.
         */
        // Guarding public API nullability.
        fun create(gson: Gson): PingXXGsonConverterFactory {
            return PingXXGsonConverterFactory(gson)
        }
    }

}
