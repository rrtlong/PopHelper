package com.moli.module.net.http.api

import android.content.pm.PackageManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.moli.module.model.constant.EventConstant
import com.moli.module.model.http.ShareMessageModel
import com.moli.module.model.http.WebUserModel
import com.blankj.utilcode.util.Utils
import com.google.gson.Gson
import com.moli.module.net.BuildConfig
import com.moli.module.net.http.Header
import com.moli.module.net.json.jolyglot
import com.moli.module.net.manager.UserManager
import org.json.JSONObject
import org.simple.eventbus.EventBus
import timber.log.Timber

/**
 * 项目名称：Aletter-App
 * 类描述：Android和H5交互接口
 * 创建人：lijilong
 * 创建时间：2018/9/3 17:26
 * 修改人：lijilong
 * 修改时间：2018/9/3 17:26
 * 修改备注：
 * @version
 */
const val JS_FUNCTION_NAME = "jasmine"

class APIJSInterface(val webView: WebView) {
    /**
     * 分享方法
     * @param shareInfo 分享信息
     */
    @JavascriptInterface
    fun shareActive(shareInfo: String) {
        val shareMessageModel = jolyglot.fromJson<ShareMessageModel>(shareInfo, ShareMessageModel::class.java)
        EventBus.getDefault().post(shareMessageModel, EventConstant.SHARE_ACTION_H5)

    }

    @JavascriptInterface
    fun shareWithCopyWord(info: String) {
        Timber.e("jolyglotcopy=$info")
        val jsonObj = JSONObject(info)
        val copyString = jsonObj.getString("copyString")
        EventBus.getDefault().post(copyString, EventConstant.COPY_MESSAGE)
    }

    @JavascriptInterface
    fun js_startWithUser() {
        webView.let {
            it.post {
                val call = "javascript:APP_data('" + getUserInfo() + "')"
                it.loadUrl(call)
            }
        }
    }

    @JavascriptInterface
    fun getHttpHeader() {
        webView.let {
            it.post {
                val call = "javascript:APP_httpHeader('" + Header.getHeaderJson() + "')"
                webView.loadUrl(call)
            }
        }
    }


    private fun getUserInfo(): String {
        val user = UserManager.getSynSelf()
        var channel: String = BuildConfig.SevicePlateform
        try {
            val applicationInfo = Utils.getApp().packageManager.getApplicationInfo(Utils.getApp().packageName, PackageManager.GET_META_DATA)
            channel = applicationInfo.metaData.getString("UMENG_CHANNEL")
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        val webUserModel: WebUserModel = if (user != null) {
////            WebUserModel(user.id, BuildConfig.versionNumber, channel, user.accessToken)
//        } else {
//            WebUserModel(null, BuildConfig.versionNumber, channel)
//        }
//        return Gson().toJson(webUserModel)
        return ""
    }


}
