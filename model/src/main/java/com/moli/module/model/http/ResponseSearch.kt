package com.moli.module.model.http

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/15 下午10:14
 * 修改人：yuliyan
 * 修改时间：2018/10/15 下午10:14
 * 修改备注：
 * @version
 */
data class ResponseSearch(val content: String,
                          val pageNum: Int? = null,
                          val pageSize: Int,
                          val type: String) //类型 user|story|reward|music
