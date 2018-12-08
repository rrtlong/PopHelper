package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/8 15:04
 * 修改人：lijilong
 * 修改时间：2018/12/8 15:04
 * 修改备注：
 * @version
 */
data class SignConfig(
    @JvmField
    val id: Int,
    @JvmField
    val signTimes: Int = 0,
    @JvmField
    val signReward: String? = null,
    @JvmField
    val signText: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeInt(signTimes)
        writeString(signReward)
        writeString(signText)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SignConfig> = object : Parcelable.Creator<SignConfig> {
            override fun createFromParcel(source: Parcel): SignConfig = SignConfig(source)
            override fun newArray(size: Int): Array<SignConfig?> = arrayOfNulls(size)
        }
    }
}
