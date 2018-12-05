package com.moli.module.model.base

import com.google.gson.annotations.SerializedName

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/15 下午10:46
 * 修改人：yuliyan
 * 修改时间：2018/10/15 下午10:46
 * 修改备注：
 * @version
 */
data class TradeModel(
        @field:SerializedName("id", alternate = ["tradeId"])
        var id: Long? = null
)
