package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/10/17 17:10
 * 修改人：lijilong
 * 修改时间：2018/10/17 17:10
 * 修改备注：
 * @version
 */
data class PushMsgModel(
        @JvmField
        val id: Long = 0,
        @JvmField
        @field:SerializedName("baseUserVO")
        val baseUserVo: UserInfo? = null,
        @JvmField
        val content: String? = null, //谁点赞了你，谁回复了你，谁评论了你
        @JvmField
        val createTime: Long? = null,
        @JvmField
        val type: String? = null, //REWARD,STORY
        @JvmField
        val typeId: Long? = null, //storyId,rewardId
        @JvmField
        val commentContent: String? = null,
        @JvmField
        val avatar: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readParcelable<UserInfo>(UserInfo::class.java.classLoader),
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeParcelable(baseUserVo, 0)
        writeString(content)
        writeValue(createTime)
        writeString(type)
        writeValue(typeId)
        writeString(commentContent)
        writeString(avatar)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PushMsgModel> = object : Parcelable.Creator<PushMsgModel> {
            override fun createFromParcel(source: Parcel): PushMsgModel = PushMsgModel(source)
            override fun newArray(size: Int): Array<PushMsgModel?> = arrayOfNulls(size)
        }
    }
}
