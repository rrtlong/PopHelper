package com.moli.module.model.http

/**
 * 项目名称：Aletter-App
 * 类描述：登录
 * 创建人：yuliyan
 * 创建时间：2018/8/14 下午8:11
 * 修改人：yuliyan
 * 修改时间：2018/8/14 下午8:11
 * 修改备注：
 * @version
 */
const val LOGIN_TYPE_PASSWORD = 1
const val LOGIN_TYPE_WX = 2
const val LOGIN_TYPE_WB = 3
const val LOGIN_TYPE_QQ = 4
const val LOGIN_TYPE_PHONE_CODE = 5 //验证码登录，（找回密码）

class ResonseLogin(
        val loginType: Int?=null, //登录类型 1密码登录 2wx 3wb 4qq 5验证码
        val phone: String? = null,
        val authCode: String? = null, //验证码
        val password: String? = null, //密码
        val accessToken: String? = null,
        val uid: String? = null,
        val openId: String? = null,
        val inviteUuid:String?=null)
