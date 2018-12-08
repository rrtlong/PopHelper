package com.moli.module.model.http

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/7 18:43
 * 修改人：lijilong
 * 修改时间：2018/12/7 18:43
 * 修改备注：
 * @version
 */
data class ChargeRequest(
    @JvmField
    var userId: Long,   //用户id
    @JvmField
    var goodsId: Int,   //商品id
    @JvmField
    var goodsNum: Int,  //商品数量
    @JvmField
    var platformType: Int   //支付平台0支付宝1微信
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readLong(),
        source.readInt(),
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(userId)
        writeInt(goodsId)
        writeInt(goodsNum)
        writeInt(platformType)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ChargeRequest> = object : Parcelable.Creator<ChargeRequest> {
            override fun createFromParcel(source: Parcel): ChargeRequest = ChargeRequest(source)
            override fun newArray(size: Int): Array<ChargeRequest?> = arrayOfNulls(size)
        }
    }
}
