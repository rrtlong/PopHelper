package com.moli.pophelper.module.pay

import android.text.TextUtils

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/10/24 14:42
 * 修改人：lijilong
 * 修改时间：2018/10/24 14:42
 * 修改备注：
 */
class AuthResult(rawResult: Map<String, String>?, removeBrackets: Boolean) {

    var resultStatus: String? = null
    var result: String? = null
    var memo: String? = null
    var resultCode: String? = null
    var authCode: String? = null
    var alipayOpenId: String? = null

    init {
        if (rawResult != null) {
            for (key in rawResult!!.keys) {
                if (TextUtils.equals(key, "resultStatus")) {
                    resultStatus = rawResult[key]
                } else if (TextUtils.equals(key, "result")) {
                    result = rawResult[key]
                } else if (TextUtils.equals(key, "memo")) {
                    memo = rawResult[key]
                }
            }
            val resultValue = result!!.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (value in resultValue) {
                if (value.startsWith("alipay_open_id")) {
                    alipayOpenId = removeBrackets(getValue("alipay_open_id=", value), removeBrackets)
                    continue
                }
                if (value.startsWith("auth_code")) {
                    authCode = removeBrackets(getValue("auth_code=", value), removeBrackets)
                    continue
                }
                if (value.startsWith("result_code")) {
                    resultCode = removeBrackets(getValue("result_code=", value), removeBrackets)
                    continue
                }
            }

        }


    }

    private fun removeBrackets(str: String, remove: Boolean): String? {
        var str = str
        if (remove) {
            if (!TextUtils.isEmpty(str)) {
                if (str.startsWith("\"")) {
                    str = str.replaceFirst("\"".toRegex(), "")
                }
                if (str.endsWith("\"")) {
                    str = str.substring(0, str.length - 1)
                }
            }
        }
        return str
    }

    private fun getValue(header: String, data: String): String {
        return data.substring(header.length, data.length)
    }
}

