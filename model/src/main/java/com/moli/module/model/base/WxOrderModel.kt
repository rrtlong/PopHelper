package com.moli.module.model.base

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/19 10:57
 * 修改人：lijilong
 * 修改时间：2018/12/19 10:57
 * 修改备注：
 * @version
 */
data class WxOrderModel(
    @JvmField
    var resultCode: Int = 0,
    @JvmField
    var billno: String,
    @JvmField
    var rechargeInfo: String
)

data class RechargeInfo(
    @JvmField
    var `package`: String,
    @JvmField
    var appid: String,
    @JvmField
    var sign: String,
    @JvmField
    var partnerid: String,
    @JvmField
    var prepayid: String,
    @JvmField
    var billno: String,
    @JvmField
    var noncestr: String,
    @JvmField
    var timestamp: String
)

