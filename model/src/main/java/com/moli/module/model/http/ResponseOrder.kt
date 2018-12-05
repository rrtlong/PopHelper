package com.moli.module.model.http

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/13 下午4:40
 * 修改人：yuliyan
 * 修改时间：2018/10/13 下午4:40
 * 修改备注：
 * @version
 */
data class ResponseOrder(
        @JvmField
        var channelId: Int? = null,
        @JvmField
        var goodsId: Long) : Parcelable {
    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readLong()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(channelId)
        writeLong(goodsId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ResponseOrder> = object : Parcelable.Creator<ResponseOrder> {
            override fun createFromParcel(source: Parcel): ResponseOrder = ResponseOrder(source)
            override fun newArray(size: Int): Array<ResponseOrder?> = arrayOfNulls(size)
        }
    }
}

