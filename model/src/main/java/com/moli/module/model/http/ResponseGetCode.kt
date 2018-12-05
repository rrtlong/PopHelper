package com.moli.module.model.http

/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/8/13 下午6:09
 * 修改人：yuliyan
 * 修改时间：2018/8/13 下午6:09
 * 修改备注：
 * @version
 */
/**
 * @param functionType  0:注册/登录 1:重置密码 2:三方绑定
 */
data class ResponseGetCode(val functionType: Int,
                           val phone: String)

