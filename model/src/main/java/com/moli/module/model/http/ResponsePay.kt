package com.moli.module.model.http

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/11 下午2:15
 * 修改人：yuliyan
 * 修改时间：2018/10/11 下午2:15
 * 修改备注：
 * @version
 */

data class ResponsePay(val channel: String,
                       val amount: Int? = null,
                       val livemode: Boolean? = false)
