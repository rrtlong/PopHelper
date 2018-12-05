package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/9/18 01:22
 * 修改人：lijilong
 * 修改时间：2018/9/18 01:22
 * 修改备注：
 * @version
 */
data class ImageModel(
        @JvmField
        @field:SerializedName("id", alternate = ["imageId"])
        var id: Long? = 0,
        @JvmField
        var src: String,
        @JvmField
        val width: Int = 0,
        @JvmField
        val height: Int = 0,
        @JvmField
        var isSelected: Boolean = false) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun equals(other: Any?): Boolean {
        if (other !is ImageModel) return false
        return src == other.src
    }

    override fun hashCode(): Int {
        return this.src.hashCode() * 31
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(src)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageModel> {
        override fun createFromParcel(parcel: Parcel): ImageModel {
            return ImageModel(parcel)
        }

        override fun newArray(size: Int): Array<ImageModel?> {
            return arrayOfNulls(size)
        }
    }


}
