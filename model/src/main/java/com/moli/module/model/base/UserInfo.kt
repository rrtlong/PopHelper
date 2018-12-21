package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
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
    var id: Long = -1,
    @JvmField
    var phone: String? = null,
    @JvmField
    var signTimes: Int = 0, //累计签到次数 每个星期一清零
    @JvmField
    var signDate: Long? = null,  //上次签到时间
    @JvmField
    var nick: String? = null,
    @JvmField
    var iconImg: String? = null,
    @JvmField
    var change: Long? = null, //零钱数
    @JvmField
    var ruby: Long? = null,  //宝石数
    @JvmField
    var cash: Long? = null,  //钻石数
    @JvmField
    var loginLastTime: Long? = null,  //最后一次登录时间
    @JvmField
    var createTime: Long? = null,  //创建时间
    @Convert(converter = SignConverter::class, dbType = String::class)
    @JvmField
    var signConfig: MutableList<SignConfig>? = null,  //签到奖励
    @Convert(converter = ListIntConverter::class, dbType = String::class)
    @JvmField
    var doubleRechargeFlag: MutableList<Int>? = null   //首冲双倍标志

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readLong(),
        source.readString(),
        source.readInt(),
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readString(),
        source.readString(),
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readValue(Long::class.java.classLoader) as Long?,
        source.createTypedArrayList(SignConfig.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(phone)
        writeInt(signTimes)
        writeValue(signDate)
        writeString(nick)
        writeString(iconImg)
        writeValue(change)
        writeValue(ruby)
        writeValue(cash)
        writeValue(loginLastTime)
        writeValue(createTime)
        writeTypedList(signConfig)
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
    GsonSpeaker(
        GsonBuilder()
            .setLenient().create()
    )
}

class ShareVoConverter : PropertyConverter<ShareDataModel?, String?> {
    override fun convertToDatabaseValue(entityProperty: ShareDataModel?): String? {
        if (entityProperty == null)
            return null

        return jolyglot.toJson(entityProperty)
    }

    override fun convertToEntityProperty(databaseValue: String?): ShareDataModel? {
        if (databaseValue == null) {
            return null
        }
        return jolyglot.fromJson(databaseValue, ShareDataModel::class.java)
    }
}

class SignConverter : PropertyConverter<MutableList<SignConfig>?, String?> {
    override fun convertToDatabaseValue(entityProperty: MutableList<SignConfig>?): String? {
        if (entityProperty == null || entityProperty.isEmpty()) {
            return null
        }
        return jolyglot.toJson(entityProperty)
    }

    override fun convertToEntityProperty(databaseValue: String?): MutableList<SignConfig>? {
        if (databaseValue == null) {
            return null
        }
        return jolyglot.fromJson(databaseValue, object : TypeToken<MutableList<SignConfig>>() {}.type)
    }

}

class ListIntConverter : PropertyConverter<MutableList<Int>?, String?> {
    override fun convertToDatabaseValue(entityProperty: MutableList<Int>?): String? {
        if (entityProperty == null || entityProperty.isEmpty()) {
            return null
        }
        return jolyglot.toJson(entityProperty)
    }

    override fun convertToEntityProperty(databaseValue: String?): MutableList<Int>? {
        if (databaseValue == null) {
            return null
        }
        return jolyglot.fromJson(databaseValue, object : TypeToken<MutableList<Int>>() {}.type)
    }

}
