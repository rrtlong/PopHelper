package com.moli.module.net.http

/**
 * 项目名称：Aletter
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/7/31 下午2:15
 * 修改人：yuliyan
 * 修改时间：2018/7/31 下午2:15
 * 修改备注：
 * @version
 */
object ResponseCode {
    /**
     * 成功
     */
    const val OK = 10000

    /**
     * 已注册
     */
    const val HAS_REGISTER = 20007
    /**
     * 手机号或密码不能为空
     */
    const val CODE_LOGIN_ACCOUNT = 20001
    /**
     * 登录失败，需要注册
     */
    const val CODE_LOGIN_PHONE = 20002
    /**
     * 密码输入错误
     */
    const val CODE_LOGIN_PASSWORD = 20003
    /**
     * 第三方验证失败
     */
    const val CODE_LOGIN_THIRD = 20004

    /**
     * 三方登录失败，需要注册
     */
    const val CODE_LOGIN_THIRDLOGIN = 20005
    /**
     * 三方登录用户信息不完整
     */
    const val CODE_LOGIN_THIRDLOGIN_USERINF_INCOMPLETE = 20000
    /**
     * 三方登录需要绑定手机号
     */
    const val CODE_LOGIN_THIRDLOGIN_UNBIND_PHONE = 19999
    /**
     * 验证码发送失败,1分钟内不重新发送!
     */
    const val CODE_PHONECODE_ERROR = 20006
    /**
     * 验证码发送失败，已经注册过
     */
    const val CODE_REGISTER_ERROR = 20007
    /**
     * 验证码发送失败，需要注册
     */
    const val CODE_NOREGISTER_ERROR = 20008
    /**
     * 验证码错误
     */
    const val CODE_CODE_ERROR = 20010

    /**
     * empty option
     */
    const val CODE_EMPTY = 20011
    /**
     * 注册失败
     */
    const val CODE_REGISTER_FAIL = 20012
    /**
     * 验证码失效
     */
    const val CODE_CODE_INVALID = 20013
    /**
     * 已经注册过,请登录
     */
    const val CODE_REGISTER_REPEAT = 20014
    /**
     * 用户信息异常
     */
    const val CODE_USER_ERROR = 20015
    /**
     * 注册失败，手机号或验证码为空
     */
    const val CODE_REGISTER_NULLPHONE = 20016
    /**
     * 注册失败，三方UID或access_taken不能为空
     */
    const val CODE_REGISTER_THIRDINFO = 20017
    /**
     * 注册失败，三方用户信息获取失败
     */
    const val CODE_THIRDINFO_ERROR = 20018
    /**
     * 今日获取短信验证码次数已达上限
     */
    const val CODE_PHONECODE_DAY_ERROR = 20019

    /**
     * 未注册
     */
    const val UN_REGIST = 123700
    /**
     * 重复请求验证码
     */
    const val REPEAT_REQUEST_PHONECODE = 20006
    /**
     * 已绑定
     */
    const val HAS_BIND = 123500
    /**
     * 重复操作
     */
    const val REPEAT_ACTION = 129000
    /**
     * 被拉黑
     */
    const val BE_ADD_BLACK = 140100
    /**
     * 被封停
     */
    const val SUSPENED = 144444
    /**
     * 删除
     */
    const val DELETED = 155555
    /**
     * 频繁操作
     */
    const val TOOMUCH = 100906
    /**
     * 刷新token
     */
    const val REFRESH_TOKEN = 140011
    /**
     * 已经操作
     */
    const val DONE = 140004
    /**
     * 帖主无法切换匿名实名状态
     */
    const val TIEZHU_TAG = 140020
    /**
     * 签到红包
     */
    const val SIGN_RED_PACKAGE = 140006
    /**
     * 发布内容包含敏感信息
     */
    const val PUBLISH_CONTENT_SENSITIVE_INFORMATION = 200015

    /**
     * 声音匹配任务，验证已通过
     */
    const val VOICE_CHECK_SUCCESS = 200024

    /**
     * 主帖被删除
     */
    const val STORY_DELECTED = 140021

    /**
     * 密码为空去设置密码（场景一：小程序注册不需要输入手机号-密码 注册 场景二：app三方注册没有输入密码的逻辑）
     */
    const val PASSWORD_EMPTY = 20025

    const val USER_SUSPEND = 12009 //封号异常


}

