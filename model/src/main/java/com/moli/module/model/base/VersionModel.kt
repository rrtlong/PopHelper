package com.moli.module.model.baseimport android.os.Parcelimport android.os.Parcelabledata class VersionModel(        val platForm:String?=null,//版本        val version:String?=null,//更新版本        val title:String?=null,//更新title        val content:String?=null,//更新文本内容        var isImpose:Int=0,//是否强制更新,1表示强制更新        val isMaintain:Int=0,//维护状态        val downUrl:String?=null,//下载url        val createTime:Long?=0//时间//        val version:Long = 0//版本号	string) : Parcelable {    constructor(parcel: Parcel) : this(            parcel.readString(),            parcel.readString(),            parcel.readString(),            parcel.readString(),            parcel.readInt(),            parcel.readInt(),            parcel.readString(),            parcel.readValue(Long::class.java.classLoader) as? Long) {    }    override fun writeToParcel(parcel: Parcel, flags: Int) {        parcel.writeString(platForm)        parcel.writeString(version)        parcel.writeString(title)        parcel.writeString(content)        parcel.writeInt(isImpose)        parcel.writeInt(isMaintain)        parcel.writeString(downUrl)        parcel.writeValue(createTime)    }    override fun describeContents(): Int {        return 0    }    companion object CREATOR : Parcelable.Creator<VersionModel> {        override fun createFromParcel(parcel: Parcel): VersionModel {            return VersionModel(parcel)        }        override fun newArray(size: Int): Array<VersionModel?> {            return arrayOfNulls(size)        }    }}