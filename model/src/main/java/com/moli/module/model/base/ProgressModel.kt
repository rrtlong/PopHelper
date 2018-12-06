package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/6 18:16
 * 修改人：lijilong
 * 修改时间：2018/12/6 18:16
 * 修改备注：下载进度模型
 * @version
 */
data class ProgressModel(val downUrl: String, val progress: Int = 0) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(downUrl)
        writeInt(progress)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ProgressModel> = object : Parcelable.Creator<ProgressModel> {
            override fun createFromParcel(source: Parcel): ProgressModel = ProgressModel(source)
            override fun newArray(size: Int): Array<ProgressModel?> = arrayOfNulls(size)
        }
    }
}
