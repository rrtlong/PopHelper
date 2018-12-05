package com.moli.module.framework.model

open class ListResponse<T>(
        @JvmField
        val result: List<T>? = null,
        @JvmField
        val nextPage: Boolean? = null, //是否有下一页
        @JvmField
        val currentPageNo: Int = 1 //当前页码
)
