package com.moli.module.net.http.pingxx

import android.content.pm.PackageManager
import com.moli.module.model.constant.SPConstant
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.moli.module.net.BuildConfig
import com.moli.module.net.encryp.DigestRule
import com.moli.module.net.json.jolyglot
import com.ta.utdid2.device.UTDevice.*
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

private const val secretKey = "PaQhbHy3XbH"

/**
 * 项目名称：Jasmine
 * 类描述：
 * 创建人：liujun
 * 创建时间：2018/1/15 15:20
 * 修改人：liujun
 * 修改时间：2018/1/15 15:20
 * 修改备注：
 * @version
 */
class PingXXCommonInterceptor : Interceptor {

    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        return chain.proceed(sign(request))
    }

    private fun sign(request: Request): Request {
        val builder = request.newBuilder()
        return builder.build()
    }
}

/**
 * 获取请求头信息
 */
object Header {
    fun getHeaderJson(): String {
        val rule = getDigestRule()
        val map = mutableMapOf<String, String>()
        if (!rule.accessToken.isNullOrBlank()) {
            map["x-access-token"] = rule.accessToken
        }
        map["x-version"] = rule.version
        map["x-device-id"] = rule.deviceId
        map["x-platform"] = rule.platform
        if (!rule.userId.isNullOrBlank()) {
            map["x-user-id"] = rule.userId
        }
        //        map["distinctId"] = SensorsDataAPI.sharedInstance().anonymousId
        //    map["phoneid"] = PhoneInfoUtil.getUuid(RT.application)
        map["x-timestamp"] = rule.timestamp
        map["x-ticket"] = rule.disgest
        return jolyglot.toJson(map)
    }
}

private fun getDigestRule(): DigestRule {
    val uid = SPUtils.getInstance().getLong(SPConstant.USER_ID, 0)
    val accessToken = String()
    val utdId = getUtdid(Utils.getApp())
    var channel: String = BuildConfig.SevicePlateform
    try {
        val applicationInfo = Utils.getApp().packageManager.getApplicationInfo(Utils.getApp().packageName, PackageManager.GET_META_DATA)
        channel = applicationInfo.metaData.getString("UMENG_CHANNEL")
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return DigestRule(secretKey, accessToken, BuildConfig.versionNumber, utdId, channel, uid.toString(), System.currentTimeMillis().toString())
}
