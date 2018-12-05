package com.moli.module.net.http.pingxx.converter

import com.moli.module.model.http.HttpResponse
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.moli.module.net.http.ApiException
import com.moli.module.net.http.ResponseCode
import com.moli.module.net.http.converter.EmptyNullModleUtils
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import timber.log.Timber
import java.io.IOException
import java.io.StringReader
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 项目名称：Aletter
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/7/31 下午2:08
 * 修改人：yuliyan
 * 修改时间：2018/7/31 下午2:08
 * 修改备注：
 * @version
 */
internal class PingXXGsonResponseBodyConverter<T>(private val gson: Gson,
                                                  private val adapter: TypeAdapter<T>,
                                                  private val type: Type
) : Converter<ResponseBody, T> {
    @Throws(IOException::class)
    override fun convert(body: ResponseBody): T {
        body.use { value ->
            val responseStr = value.string()
            val jsonObject = JSONObject(responseStr)
            val code = jsonObject.optInt("code")
            when (code) {
                ResponseCode.OK -> {
                    return getResultData(responseStr, jsonObject)
                }
                else -> {
                    var msg = jsonObject.optString("message")
                    if (msg == null)
                        msg = jsonObject.optString("msg")
                    throw ApiException(code, msg, getResultData(responseStr, jsonObject))
                }
            }

        }
    }


    private fun getResultData(responseStr: String, jsonObject: JSONObject): T {
        if (type is ParameterizedType && type.rawType == HttpResponse::class.java) {
            return formJson(responseStr)
        }
        var data = jsonObject.optString("res")
        if (data.isNullOrBlank()) {
            data = jsonObject.optString("resBody")
        }
        if (data.isNullOrBlank() || data == "{}" || data == "null") {
            return EmptyNullModleUtils.getEmptyObject(type) as T
        } else if (data == "[]") {
            return ArrayList<Any>() as T
        }
        if (type == String::class.java) {
            return data as T
        }
        return formJson(data)
    }


    private fun formJson(data: String?): T {
        try {
            val reader = gson.newJsonReader(StringReader(data))
            return adapter.read(reader)
        } catch (e: Throwable) {
            Timber.e("json error:$data")
            throw  e
        }
    }
}
