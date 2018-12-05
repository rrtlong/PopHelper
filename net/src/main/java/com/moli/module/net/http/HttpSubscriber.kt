package com.moli.module.net.httpimport android.text.TextUtilsimport com.moli.module.model.constant.EventConstantimport com.blankj.utilcode.util.Utilsimport com.google.gson.JsonIOExceptionimport com.google.gson.JsonParseExceptionimport com.moli.module.widget.widget.safetyToastimport io.reactivex.Observerimport io.reactivex.annotations.NonNullimport io.reactivex.disposables.Disposableimport org.json.JSONExceptionimport org.simple.eventbus.EventBusimport retrofit2.HttpExceptionimport timber.log.Timberimport java.net.ConnectExceptionimport java.net.SocketTimeoutException/** * 项目名称：Android * 类描述：网络请求订阅者 * 创建人：LiuJun * 创建时间：16/8/24 21:25 * 修改人：LiuJun * 修改时间：16/8/24 21:25 * 修改备注： */abstract class HttpSubscriber<T>(        private val isShowMsg: Boolean = true //是否显示错误信息) : Observer<T> {    override fun onSubscribe(@NonNull d: Disposable) {}    override fun onComplete() {}    override fun onError(@NonNull e: Throwable) {        val msg = if (e is HttpException) {            if (e.code() != 11000) {                "系统异常"            } else {                "网络连接出错"            }        } else if (e is ApiException) {            if (e.code == 11111) {                EventBus.getDefault().post("1", EventConstant.USER_UN_AUTHORIZATION)            }            "token失效,请重新登录"        } else if (e is JSONException                || e is JsonIOException                || e is JsonParseException) {            "数据解析出错"        } else if (e is ConnectException) {            "网络连接失败"        } else if (e is SocketTimeoutException) {            "网络连接超时"        } else {            "请求失败"        }        if (isShowMsg && !TextUtils.isEmpty(msg)) {            Utils.getApp().safetyToast(msg!!)        }        Timber.e(e)    }}