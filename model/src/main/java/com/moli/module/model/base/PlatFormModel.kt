package com.moli.module.model.base

/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/8/15 上午10:45
 * 修改人：yuliyan
 * 修改时间：2018/8/15 上午10:45
 * 修改备注：
 * @version
 */
data class PlatFormModel(val openId: String?,
                         val access_token: String?,
                         val platNickName: String?,
                         val platAvatar: String?,
                         val unionid: String? = null //微信的unionid  当且仅当该移动应用已获得该用户的userinfo授权时，才会出现该字段  用于微信小程序用户打通信息唯一标示
)
