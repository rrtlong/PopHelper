package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/9 下午4:39
 * 修改人：yuliyan
 * 修改时间：2018/10/9 下午4:39
 * 修改备注：
 * @version
 */
data class ShareDataModel(
        //        "shareImg" : "http://file.xsawe.top/file/android_1532744265705fa258c7f-085e-4829-85b2-9914d4373e7e.jpg",
        val shareImg:String?=null,
        //"shareUrl": "http://dev.reward.yourmoli.com/index.html",
        val shareUrl:String?=null,
        //"shareTitle": "有赏分享标题",
        val shareTitle:String?=null,
        //"shareContent": "有赏分享内容"
        val shareContent:String?=null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(shareImg)
        parcel.writeString(shareUrl)
        parcel.writeString(shareTitle)
        parcel.writeString(shareContent)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShareDataModel> {
        override fun createFromParcel(parcel: Parcel): ShareDataModel {
            return ShareDataModel(parcel)
        }

        override fun newArray(size: Int): Array<ShareDataModel?> {
            return arrayOfNulls(size)
        }
    }
}
