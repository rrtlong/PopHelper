package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

data class GiftModel(
        // "giftId, " : 1,
        @JvmField
        var giftId: Long? = 0,
        // "price": 100,
        @JvmField
        val price: Int? = 0,
        //"giftName": "螺旋爆炸丸",
        @JvmField
        val giftName: String? = null,
        //"giftThumb": "http://file.xsawe.top/file/android_1532744265705fa258c7f-085e-4829-85b2-9914d4373e7e.jpg",
        @JvmField
        val giftThumb: String? = null,
        //"gold": 10000
        @JvmField
        val gold: Int = 0,
        //"giftNum": 1,
        @JvmField
        var giftNum: Int = 0,
        @JvmField
        var selected: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(giftId)
        parcel.writeValue(price)
        parcel.writeString(giftName)
        parcel.writeString(giftThumb)
        parcel.writeInt(gold)
        parcel.writeInt(giftNum)
        parcel.writeByte(if (selected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GiftModel> {
        override fun createFromParcel(parcel: Parcel): GiftModel {
            return GiftModel(parcel)
        }

        override fun newArray(size: Int): Array<GiftModel?> {
            return arrayOfNulls(size)
        }
    }
}
