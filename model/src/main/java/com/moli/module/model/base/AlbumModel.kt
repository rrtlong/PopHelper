package com.moli.module.model.base

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/9/18 01:20
 * 修改人：lijilong
 * 修改时间：2018/9/18 01:20
 * 修改备注：
 * @version
 */
data class AlbumModel(val name: String, val path: String, val count: Int = 0,val imageList:ArrayList<ImageModel> = ArrayList())
