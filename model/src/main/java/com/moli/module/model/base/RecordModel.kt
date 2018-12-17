package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/15 15:21
 * 修改人：lijilong
 * 修改时间：2018/12/15 15:21
 * 修改备注：
 * @version
 */
data class RecordModel(
    @JvmField
    val userId: Long = 0,
    @JvmField
    val moneyType: Int = 2000, //0000 标示钻石  40000零钱
    @JvmField
    val befCount: Long,//  变化前数值
    @JvmField
    val aftCount: Long,//  变化后数值
    @JvmField
    val changeCount: Int,//  变化值,单位分
    @JvmField
    val operationType: String,   //变更说明
    @JvmField
    val createTime: Long // 变更时间
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readLong(),
        source.readInt(),
        source.readLong(),
        source.readLong(),
        source.readInt(),
        source.readString(),
        source.readLong()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(userId)
        writeInt(moneyType)
        writeLong(befCount)
        writeLong(aftCount)
        writeInt(changeCount)
        writeString(operationType)
        writeLong(createTime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RecordModel> = object : Parcelable.Creator<RecordModel> {
            override fun createFromParcel(source: Parcel): RecordModel = RecordModel(source)
            override fun newArray(size: Int): Array<RecordModel?> = arrayOfNulls(size)
        }
    }
}
