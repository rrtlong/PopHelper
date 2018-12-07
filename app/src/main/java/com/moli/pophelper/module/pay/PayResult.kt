package com.moli.reward.app.pay

import android.text.TextUtils

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/10/24 14:41
 * 修改人：lijilong
 * 修改时间：2018/10/24 14:41
 * 修改备注：
 */
class PayResult(rawResult: Map<String, String>?) {
    /**
     * @return the resultStatus
     */
    var resultStatus: String? = null
    /**
     * @return the result
     */
    var result: String? = null
    /**
     * @return the memo
     */
    var memo: String? = null
    
    init {
        if (rawResult != null) {
            for (key in rawResult.keys) {
                when {
                    TextUtils.equals(key, "resultStatus") -> resultStatus = rawResult[key]
                    TextUtils.equals(key, "result") -> result = rawResult[key]
                    TextUtils.equals(key, "memo") -> memo = rawResult[key]
                }
            }
        }
    }
}
