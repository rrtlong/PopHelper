package com.moli.module.model.http

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：moli
 * 类描述：分享携带信息
 * 创建人：yuliyan
 * 创建时间：2018/6/13 下午3:27
 * 修改人：yuliyan
 * 修改时间：2018/6/13 下午3:27
 * 修改备注：
 * @version
 */
data class ShareMessageModel(
        @JvmField
        val type: Int = -1, // Plateform
        @JvmField
        val dataType: Int? = 0, // 分享类型 1，一般的url内容   2，base64图片
        @JvmField
        val shareTitle: String? = null, //分享标题
        @JvmField
        val shareImg: String? = null, //分享图⽚，
        @JvmField
        val shareUrl: String? = null, //分享链接
        @JvmField
        val imageBase64: String //图片base64（图片类型）
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(type)
        parcel.writeValue(dataType)
        parcel.writeString(shareTitle)
        parcel.writeString(shareImg)
        parcel.writeString(shareUrl)
        parcel.writeString(imageBase64)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShareMessageModel> {
        override fun createFromParcel(parcel: Parcel): ShareMessageModel {
            return ShareMessageModel(parcel)
        }

        override fun newArray(size: Int): Array<ShareMessageModel?> {
            return arrayOfNulls(size)
        }
    }
}


object Plateform {
    const val TYPE_QQ: Int = 3   //QQ 好友
    const val TYPE_QZone: Int = 2 //QQ空间
    const val TYPE_SINA: Int = 4 //Sina微博
    const val TYPE_WX: Int = 1 //wx好友
    const val TYPE_WXCYCLE: Int = 0 //朋友圈
}
