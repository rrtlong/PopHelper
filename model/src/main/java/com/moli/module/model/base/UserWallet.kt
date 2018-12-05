package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/18 下午7:41
 * 修改人：yuliyan
 * 修改时间：2018/10/18 下午7:41
 * 修改备注：
 * @version
 */
data class UserWallet(
        // "userId" : 90,
        @JvmField
        val userId: Long,
        // "diamondAmount": 0,
        @JvmField
        val diamondAmount: Double?=0.0,
        //"coinAmount": 0,
        @JvmField
        val coinAmount: Double?=0.0,
        //"txCoinAmount": 0,
        @JvmField
        val txCoinAmount: Double?=0.0,
        //"coinToDia": 0.6,
        @JvmField
        val coinToDia: Double?=0.0,
        //"convert": 100
        @JvmField
        val convert: Double?=0.0
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readLong(),
                parcel.readValue(Double::class.java.classLoader) as? Double,
                parcel.readValue(Double::class.java.classLoader) as? Double,
                parcel.readValue(Double::class.java.classLoader) as? Double,
                parcel.readValue(Double::class.java.classLoader) as? Double,
                parcel.readValue(Double::class.java.classLoader) as? Double) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeLong(userId)
                parcel.writeValue(diamondAmount)
                parcel.writeValue(coinAmount)
                parcel.writeValue(txCoinAmount)
                parcel.writeValue(coinToDia)
                parcel.writeValue(convert)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<UserWallet> {
                override fun createFromParcel(parcel: Parcel): UserWallet {
                        return UserWallet(parcel)
                }

                override fun newArray(size: Int): Array<UserWallet?> {
                        return arrayOfNulls(size)
                }
        }
}
