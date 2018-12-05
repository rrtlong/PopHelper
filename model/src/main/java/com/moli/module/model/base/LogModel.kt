package com.moli.module.model.base

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/11/22 19:52
 * 修改人：lijilong
 * 修改时间：2018/11/22 19:52
 * 修改备注：{"platformId":"渠道号","networdType":"连接网络的类型","phoneType":"手机型号","phoneId":"设备ID","edition":"版本号","system":"访问系统","clientPackageName":"客户端包名","type":"手机号、QQ、微信、微博"}
 * @version
 */
class LogModel() {}

data class LauncherLog(val platformId: String? = null, val networdType: String? = null, val phoneType: String? = null, val phoneId: String? = null,
                       val edition: String? = null, val system: String = "Android", val clientPackageName: String? = null, val type: String? = null)
