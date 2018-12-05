package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/10/15 15:31
 * 修改人：lijilong
 * 修改时间：2018/10/15 15:31
 * 修改备注：
 * @version
 */
data class CustomNotifacationModel(
        @JvmField
        val uid: String,
        @JvmField
        val type: String) : Parcelable {
        constructor(source: Parcel) : this(
                source.readString(),
                source.readString()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
                writeString(uid)
                writeString(type)
        }

        companion object {
                @JvmField
                val CREATOR: Parcelable.Creator<CustomNotifacationModel> = object : Parcelable.Creator<CustomNotifacationModel> {
                        override fun createFromParcel(source: Parcel): CustomNotifacationModel = CustomNotifacationModel(source)
                        override fun newArray(size: Int): Array<CustomNotifacationModel?> = arrayOfNulls(size)
                }
        }
}
