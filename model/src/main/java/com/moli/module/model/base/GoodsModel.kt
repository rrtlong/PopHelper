package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/7 15:35
 * 修改人：lijilong
 * 修改时间：2018/12/7 15:35
 * 修改备注：
 * @version
 */
data class GoodsModel(
    @JvmField
    var id: Int,   //商品id
    @JvmField
    val shopName: String? = null,   //商品名称
    @JvmField
    val shopId: String? = null, //APP物品ID
    @JvmField
    val price: String? = null,  //价格(分)
    @JvmField
    val cashNum: String? = null,    //获取物品#数量
    @JvmField
    val giveAway: String? = null,   //非活动赠送
    @JvmField
    val hotFlag: Int = 0,   //标签显示标示（1=畅销 2=划算 0=无）
    @JvmField
    val sort: Int? = null,  //排序权重
    @JvmField
    val discount: Int? = null,   //折扣
    @JvmField
    val imge: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(shopName)
        writeString(shopId)
        writeString(price)
        writeString(cashNum)
        writeString(giveAway)
        writeInt(hotFlag)
        writeValue(sort)
        writeValue(discount)
        writeString(imge)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GoodsModel> = object : Parcelable.Creator<GoodsModel> {
            override fun createFromParcel(source: Parcel): GoodsModel = GoodsModel(source)
            override fun newArray(size: Int): Array<GoodsModel?> = arrayOfNulls(size)
        }
    }
}
