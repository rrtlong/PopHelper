package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import io.victoralbertos.jolyglot.GsonSpeaker
import io.victoralbertos.jolyglot.JolyglotGenerics
@Entity
data class UserInfo(
    @Id(assignable = true)
    @JvmField
    @field:SerializedName("id", alternate = ["userId"])
    var id: Long = 0,
    @JvmField
    @field:SerializedName("name", alternate = ["userName"])
    var name: String? = null,
    @JvmField
    @field:SerializedName("image", alternate = ["avatar"])
    var image: String? = null,
    @JvmField
    @field:SerializedName("age")
    val age: Int? = 0,
    @JvmField
    @field:SerializedName("introduce")
    val introduce: String? = null,
    @JvmField
    @field:SerializedName("commentName")
    var comment_name: String? = null,
    @JvmField
    @field:SerializedName("phone")
    var phone: String? = null,
    @JvmField
    @field:SerializedName("qqUid")
    val qq_uid: String? = null,
    @JvmField
    @field:SerializedName("qqName")
    val qq_name: String? = null,
    @JvmField
    @field:SerializedName("wxUid")
    val wx_uid: String? = null,
    @JvmField
    @field:SerializedName("wxName")
    val wx_name: String? = null,
    @JvmField
    @field:SerializedName("wbUid")
    val wb_uid: String? = null,
    @JvmField
    @field:SerializedName("wbName")
    val wb_name: String? = null,
    @JvmField
    @field:SerializedName("channel")
    val channel: String? = null,
    @JvmField
    @field:SerializedName("latitude")
    val latitude: String? = null,
    @JvmField
    @field:SerializedName("longitude")
    val longitude: String? = null,
    @JvmField
    @field:SerializedName("status")
    var status: Int? = null,
    @JvmField
    @field:SerializedName("power")
    val power: Int? = null,
    @JvmField
    @field:SerializedName("isOfficial")
    val isOfficial: Int? = null,
    @JvmField
    @field:SerializedName("createTime")
    val create_time: Long? = null,
    @JvmField
    @field:SerializedName("updateTime")
    val update_time: Long? = null,
    @JvmField
    var keyWord: String? = null,
    @JvmField
    @field:SerializedName("uuid", alternate = ["userUuid"])
    var uuid: String? = null,
    @JvmField
    @field:SerializedName("isFriend")
    var isFriend: Int = 0,  // 0:没有关注 1：已关注 2：互相关注
    @JvmField
    @field:SerializedName("signInfo")
    var signInfo: String? = null,
    @JvmField
    @field:SerializedName("beFavorCount")
    var beFavorCount: Int = 0,
    @JvmField
    @field:SerializedName("attentionCount")
    var attentionCount: Int = 0,
    @JvmField
    @field:SerializedName("fansCount")
    var fansCount: Int = 0,  // 0:没有关注 1：已关注 2：互相关注
    @JvmField
    @field:SerializedName("storyCount")
    var storyCount: Int = 0, //作品总数
    @JvmField
    @field:SerializedName("rewardCount")
    var rewardCount: Int = 0, //自己发布悬赏的总数
    @JvmField
    @field:SerializedName("favorCount")
    var favorCount: Int = 0, //喜欢的作品数
    @JvmField
    @field:SerializedName("birthDay")
    var birthDay: String? = null, //生日
    @JvmField
    var constellation: String? = null, //星座
    @JvmField
    @field:SerializedName("gender")
    var gender: Int? = null, //性别 1男2女
    @JvmField
    var city: String? = null,   //城市
    @JvmField
    var accessToken: String? = null,

    @Convert(converter = ShareVoConverter::class, dbType = String::class)
    @JvmField
    @field:SerializedName("shareMsgVO")
    var shareMsgVO: ShareDataModel? = null

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readLong(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
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
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readParcelable<ShareDataModel>(ShareDataModel::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(name)
        writeString(image)
        writeValue(age)
        writeString(introduce)
        writeString(comment_name)
        writeString(phone)
        writeString(qq_uid)
        writeString(qq_name)
        writeString(wx_uid)
        writeString(wx_name)
        writeString(wb_uid)
        writeString(wb_name)
        writeString(channel)
        writeString(latitude)
        writeString(longitude)
        writeValue(status)
        writeValue(power)
        writeValue(isOfficial)
        writeValue(create_time)
        writeValue(update_time)
        writeString(keyWord)
        writeString(uuid)
        writeInt(isFriend)
        writeString(signInfo)
        writeInt(beFavorCount)
        writeInt(attentionCount)
        writeInt(fansCount)
        writeInt(storyCount)
        writeInt(rewardCount)
        writeInt(favorCount)
        writeString(birthDay)
        writeString(constellation)
        writeValue(gender)
        writeString(city)
        writeString(accessToken)
        writeParcelable(shareMsgVO, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserInfo> = object : Parcelable.Creator<UserInfo> {
            override fun createFromParcel(source: Parcel): UserInfo = UserInfo(source)
            override fun newArray(size: Int): Array<UserInfo?> = arrayOfNulls(size)
        }
    }
}

val jolyglot: JolyglotGenerics by lazy {
    GsonSpeaker(GsonBuilder()
            .setLenient().create())
}

class ShareVoConverter : PropertyConverter<ShareDataModel?, String?> {
    override fun convertToDatabaseValue(entityProperty: ShareDataModel?): String? {
        if(entityProperty == null)
            return null

        return jolyglot.toJson(entityProperty)
    }

    override fun convertToEntityProperty(databaseValue: String?): ShareDataModel? {
        if(databaseValue == null){
            return null
        }
        return jolyglot.fromJson(databaseValue, ShareDataModel::class.java)
    }





}
