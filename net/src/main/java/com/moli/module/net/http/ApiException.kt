package com.moli.module.net.http

/**
 * 项目名称：Jasmine
 * 类描述：服务器返回错误
 * 创建人：LiuJun
 * 创建时间：16/8/25 11:02
 * 修改人：LiuJun
 * 修改时间：16/8/25 11:02
 * 修改备注：
 */
class ApiException(
        val code: Int,
        val msg: String?,
        var model: Any? = null
) : RuntimeException("code: $code  msg: $msg")
