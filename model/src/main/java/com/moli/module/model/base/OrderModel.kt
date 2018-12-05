package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/13 下午6:15
 * 修改人：yuliyan
 * 修改时间：2018/10/13 下午6:15
 * 修改备注：
 * @version
 */
data class OrderModel(
        // "orderId": 21,
        @field:SerializedName("id", alternate = ["orderId"])
        var id: Long? = null,
        // "userId" : 90,
        val userId: Long? = null,
        //"orderNo": "1955985813520384",
        val orderNo: String? = null,
        //"channel": "wx",
        val channel: String? = null,
        //"amount": 10,
        val amount: Int? = null,
        //"orderTitle": "钻石x100",
        val orderTitle: String? = null,
        //"orderDesc": "钻石x100",
        val orderDesc: String? = null,
        //"createTime": 1539425499766)
        val createTime: Long? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Long::class.java.classLoader) as? Long) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(userId)
        parcel.writeString(orderNo)
        parcel.writeString(channel)
        parcel.writeValue(amount)
        parcel.writeString(orderTitle)
        parcel.writeString(orderDesc)
        parcel.writeValue(createTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderModel> {
        override fun createFromParcel(parcel: Parcel): OrderModel {
            return OrderModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderModel?> {
            return arrayOfNulls(size)
        }
    }
}
