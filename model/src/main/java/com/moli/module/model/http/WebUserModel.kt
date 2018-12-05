package com.moli.module.model.http

/**
 * 项目名称：Aletter-App
 * 类描述：网页需要的用户信息
 * 创建人：lijilong
 * 创建时间：2018/9/3 17:24
 * 修改人：lijilong
 * 修改时间：2018/9/3 17:24
 * 修改备注：
 * @version
 */
class WebUserModel(val userId: Long? = null, var appVersion: String, val platform: String = "test",val accessToken:String?=null)
