package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/15 下午10:03
 * 修改人：yuliyan
 * 修改时间：2018/10/15 下午10:03
 * 修改备注：
 * @version
 */
data class UserLiteModel(
    @JvmField
    val userVO: UserInfo? = null,
    @JvmField
    var id: Int? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<UserInfo>(UserInfo::class.java.classLoader),
        source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(userVO, 0)
        writeValue(id)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserLiteModel> = object : Parcelable.Creator<UserLiteModel> {
            override fun createFromParcel(source: Parcel): UserLiteModel = UserLiteModel(source)
            override fun newArray(size: Int): Array<UserLiteModel?> = arrayOfNulls(size)
        }
    }
}
