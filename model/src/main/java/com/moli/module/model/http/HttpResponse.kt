package com.moli.module.model.http

import com.google.gson.annotations.SerializedName

/**
 * 项目名称：Jasmine
 * 类描述：接口返回的最外层对象
 * 创建人：liujun
 * 创建时间：2018/1/12 17:47
 * 修改人：liujun
 * 修改时间：2018/1/12 17:47
 * 修改备注：
 * @version
 */
data class HttpResponse<T>(
        @JvmField
        val code: Int,
        @field:SerializedName("message", alternate = ["msg"])
        @JvmField
        val message: String,
        @JvmField
        val total: Int?,
        @field:SerializedName("res", alternate = ["resBody"])
        @JvmField
        val res: T? = null
)
