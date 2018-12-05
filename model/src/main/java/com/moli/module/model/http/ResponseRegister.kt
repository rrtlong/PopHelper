package com.moli.module.model.http

/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/8/14 下午4:45
 * 修改人：yuliyan
 * 修改时间：2018/8/14 下午4:45
 * 修改备注：
 * @version
 */
data class ResponseRegister(
        val phone: String?,
        val password: String?,
        val phoneCode: String?,
        val registerType: String?,
        val channel: String?)

data class ThreePlateformRegister(
        val accessToken: String,
        val registerType: Int,
        val openid: String,
        val phone: String,
        val phoneCode: String,
        val registerUID: String? = null,
        val channel: String? = null
)
