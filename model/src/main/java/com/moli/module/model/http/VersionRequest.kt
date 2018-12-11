package com.moli.module.model.http

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/10 19:24
 * 修改人：lijilong
 * 修改时间：2018/12/10 19:24
 * 修改备注：
 * @version
 */
data class VersionRequest(
    val clientName: String,    //当前渠道号
    val clientType: String,    //当前包名
    val clientVersion: String
) //当前版本
