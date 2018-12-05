package com.moli.module.model.base

import android.graphics.Bitmap

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/11/19 21:29
 * 修改人：lijilong
 * 修改时间：2018/11/19 21:29
 * 修改备注：
 * @version
 */
data class CoverModel(val index: Int, val bitmap: Bitmap, var isCheck: Boolean = false)
