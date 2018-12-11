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
    var title: String? = null,  //标题
    @JvmField
    var headImgUrl: String? = null, //icon
    @JvmField
    var tagText: String? = null,    //标签
    @JvmField
    var imgUrl: String? = null,  //海报图
    @JvmField
    var content: String? = null,    //描述
    @JvmField
    var contentUrl: String? = null,
    @JvmField
    var contentType: Int = 100,
    @JvmField
    var downloadUrl: String? = null,    //下载地址
    @JvmField
    var packageName: String? = null,    //app包名
    @JvmField
    var pv: Int? = null   //下载数
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(headImgUrl)
        writeString(tagText)
        writeString(imgUrl)
        writeString(content)
        writeString(contentUrl)
        writeInt(contentType)
        writeString(downloadUrl)
        writeString(packageName)
        writeValue(pv)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AppModel> = object : Parcelable.Creator<AppModel> {
            override fun createFromParcel(source: Parcel): AppModel = AppModel(source)
            override fun newArray(size: Int): Array<AppModel?> = arrayOfNulls(size)
        }
    }
}
