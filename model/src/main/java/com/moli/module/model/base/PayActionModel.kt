package com.moli.module.model.base

/**
 * 项目名称：moli
 * 类描述：分享菜单操作描述
 * 创建人：yuliyan
 * 创建时间：2018/6/12 上午11:04
 * 修改人：yuliyan
 * 修改时间：2018/6/12 上午11:04
 * 修改备注：
 * @version
 */
data class PayActionModel(val id: Int,
                          val imageId: Int,
                          val name:String,
                          val channel:Int,
                          val isSelect: Boolean? = false)
