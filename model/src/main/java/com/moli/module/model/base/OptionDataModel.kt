package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/16 上午10:09
 * 修改人：yuliyan
 * 修改时间：2018/10/16 上午10:09
 * 修改备注：
 * @version
 */
data class OptionDataModel(
        @JvmField
        val dataType: Int, //数据类型 0：作品 1：悬赏 2：评论 3：举报人    ->1悬赏 2作品 3评论 4用户
        @JvmField
        val dataId: Long, //举报数据ID
        @JvmField
        val dataUserId: Long? = null, //举报数据对应的用户ID
        @JvmField
        val shareShowType: Int? = null, // 分享面板展示类型
        @JvmField
        val dataVideoUrl: String? = null, // 视频URL
        @JvmField
        val dataPosition: Int? = null, //视频在列表中位置
        @JvmField
        val dataIsReward: Int? = null, //视频是不是获得悬赏 0：未获得悬赏 1：已获得悬赏
        @JvmField
        val isShowUnInterest: Boolean? = null, // 是否展示不感兴趣
        @JvmField
        val dataVideoCover: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readLong(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(dataType)
        writeLong(dataId)
        writeValue(dataUserId)
        writeValue(shareShowType)
        writeString(dataVideoUrl)
        writeValue(dataPosition)
        writeValue(dataIsReward)
        writeValue(isShowUnInterest)
        writeString(dataVideoCover)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OptionDataModel> = object : Parcelable.Creator<OptionDataModel> {
            override fun createFromParcel(source: Parcel): OptionDataModel = OptionDataModel(source)
            override fun newArray(size: Int): Array<OptionDataModel?> = arrayOfNulls(size)
        }
    }
}
