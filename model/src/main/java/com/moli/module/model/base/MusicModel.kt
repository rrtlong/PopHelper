package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/5 02:19
 * 修改人：lijilong
 * 修改时间：2018/12/5 02:19
 * 修改备注：
 * @version
 */
data class MusicModel(
    @JvmField
    var musicId: Long? = 0,
    @JvmField
    var categoryId: Long? = 0,
    @JvmField
    var name: String? = null,
    @JvmField
    var author: String? = null,
    @JvmField
    var thumb: String? = null,
    @JvmField
    var time: String? = null,
    @JvmField
    var url: String? = null,
    @JvmField
    var downloadCount: String? = null,
    @JvmField
    var createTime: Long = 0,
    @JvmField
    var updateTime: Long = 0,
    @JvmField
    var deleted: Long = 0,
    @JvmField
    var preSelect: Boolean = false, //底部button是否显示
    @JvmField
    var isRecommend: Int? = null,
    @JvmField
    var userId: Long? = null,
    @JvmField
    var isCollect: Int = 0,
    @JvmField
    var isPlaying: Boolean = false,  //是否正在播放
    @JvmField
    @field:SerializedName("shareMsgVO")
    var shareMsgVO: ShareDataModel? = null,
    @JvmField
    var storyCount: Int = 0
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readLong(),
        source.readLong(),
        source.readLong(),
        1 == source.readInt(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readInt(),
        1 == source.readInt(),
        source.readParcelable<ShareDataModel>(ShareDataModel::class.java.classLoader),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(musicId)
        writeValue(categoryId)
        writeString(name)
        writeString(author)
        writeString(thumb)
        writeString(time)
        writeString(url)
        writeString(downloadCount)
        writeLong(createTime)
        writeLong(updateTime)
        writeLong(deleted)
        writeInt((if (preSelect) 1 else 0))
        writeValue(isRecommend)
        writeValue(userId)
        writeInt(isCollect)
        writeInt((if (isPlaying) 1 else 0))
        writeParcelable(shareMsgVO, 0)
        writeInt(storyCount)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MusicModel> = object : Parcelable.Creator<MusicModel> {
            override fun createFromParcel(source: Parcel): MusicModel = MusicModel(source)
            override fun newArray(size: Int): Array<MusicModel?> = arrayOfNulls(size)
        }
    }
}
