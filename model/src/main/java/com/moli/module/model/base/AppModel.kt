package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/7 15:29
 * 修改人：lijilong
 * 修改时间：2018/12/7 15:29
 * 修改备注：
 * @version
 */
data class AppModel(
    @JvmField
    var downloadUrl: String? = null,
    @JvmField
    var packageName: String? = null,
    @JvmField
    var title: String? = null,
    @JvmField
    var imgUrl: String? = null,
    @JvmField
    var pv: Int? = null,
    @JvmField
    var label: String? = null,
    @JvmField
    var content: String? = null,
    @JvmField
    var contentUrl: String? = null,
    @JvmField
    var contentType: Int = 100
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(downloadUrl)
        writeString(packageName)
        writeString(title)
        writeString(imgUrl)
        writeValue(pv)
        writeString(label)
        writeString(content)
        writeString(contentUrl)
        writeInt(contentType)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AppModel> = object : Parcelable.Creator<AppModel> {
            override fun createFromParcel(source: Parcel): AppModel = AppModel(source)
            override fun newArray(size: Int): Array<AppModel?> = arrayOfNulls(size)
        }
    }
}
