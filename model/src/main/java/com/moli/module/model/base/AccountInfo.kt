package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/10/16 14:24
 * 修改人：lijilong
 * 修改时间：2018/10/16 14:24
 * 修改备注：
 * @version
 */
@Entity
data class AccountInfo(
        @Id(assignable = true)
        @JvmField
        @field:SerializedName("id", alternate = ["userId"])
        var id: Long = 0,
        @JvmField
        var phone: String? = null,
        @JvmField
        var password: String? = null,
        @JvmField
        @field:SerializedName("qqUid")
        val qq_uid: String? = null,
        @JvmField
        @field:SerializedName("qqName")
        val qq_name: String? = null,
        @JvmField
        @field:SerializedName("qqAvatar")
        val qq_avatar: String? = null,
        @JvmField
        @field:SerializedName("wxUid")
        val wx_uid: String? = null,
        @JvmField
        @field:SerializedName("wxName")
        val wx_name: String? = null,
        @JvmField
        @field:SerializedName("wxAvatar")
        val wx_avatar: String? = null,
        @JvmField
        @field:SerializedName("wbUid")
        val wb_uid: String? = null,
        @JvmField
        @field:SerializedName("wbName")
        val wb_name: String? = null,
        @JvmField
        @field:SerializedName("wbAvatar")
        val wb_avatar: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(phone)
        writeString(password)
        writeString(qq_uid)
        writeString(qq_name)
        writeString(qq_avatar)
        writeString(wx_uid)
        writeString(wx_name)
        writeString(wx_avatar)
        writeString(wb_uid)
        writeString(wb_name)
        writeString(wb_avatar)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AccountInfo> = object : Parcelable.Creator<AccountInfo> {
            override fun createFromParcel(source: Parcel): AccountInfo = AccountInfo(source)
            override fun newArray(size: Int): Array<AccountInfo?> = arrayOfNulls(size)
        }
    }
}
