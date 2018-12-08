package com.moli.module.model.base

import android.os.Parcel
import android.os.Parcelable

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/7 02:45
 * 修改人：lijilong
 * 修改时间：2018/12/7 02:45
 * 修改备注：
 * @version
 */
data class StrategyModel(
    @JvmField
    val title: String? = null,  //标题
    @JvmField
    val imgUrl: String? = null, //图片地址
    @JvmField
    val content: String? = null,    //简要内容
    @JvmField
    val contentUrl: String? = null, //内容地址
    @JvmField
    val contentType: Int = 0,   //攻略类型0普通99推荐
    @JvmField
    val pv: Int = 0,    //浏览数
    @JvmField
    val createTime: Long? = null    //创建时间
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readValue(Long::class.java.classLoader) as Long?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(imgUrl)
        writeString(content)
        writeString(contentUrl)
        writeInt(contentType)
        writeInt(pv)
        writeValue(createTime)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StrategyModel> = object : Parcelable.Creator<StrategyModel> {
            override fun createFromParcel(source: Parcel): StrategyModel = StrategyModel(source)
            override fun newArray(size: Int): Array<StrategyModel?> = arrayOfNulls(size)
        }
    }
}
