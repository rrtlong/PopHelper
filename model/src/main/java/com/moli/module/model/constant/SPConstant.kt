package com.moli.module.model.constant

/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/8/1 下午2:09
 * 修改人：yuliyan
 * 修改时间：2018/8/1 下午2:09
 * 修改备注：
 * @version
 */
object SPConstant {
    /**
     *  异常引发闪退重启的时间戳
     */
    const val RESTART_APP_TIME_CONSTANT = "restart_app_time"
    /**
     * 登录成功之后的缓存的用户ID
     */
    const val USER_ID = "userId"


    /**
     * 绑定手机号请求验证码的手机号
     */
    const val BIND_VERIFICATION_REQUEST_PHONE = "bind_verification_request_phone"
    /**
     *  绑定手机号请求验证码的时间
     */
    const val BIND_VERIFICATION_REQUEST_TIME = "bind_verification_request_time"

    /**
     * 找回密码请求验证码的手机号
     */
    const val FIND_BACK_PASSWORD_REQUEST_PHONE = "find_back_password_request_phone"

    /**
     *  找回密码请求验证码的时间
     */
    const val FIND_BACK_PASSWORD_REQUEST_TIME = "find_back_password_request_time"

    /**
     * 经度
     */
    const val KEY_LONGITUDE = "key_longitude"
    /**
     * 纬度
     */
    const val KEY_LATITUDE = "key_latitude"

    /**
     * 城市
     */
    const val KEY_CITY = "key_city"
    /**
     * 用户设置用户密码 请求验证码的时间（接口调用成功之后存起来的时间戳）
     */
    const val SETTING_PASSWORD_VERIFICATION_REQUEST_TIME = "setting_password__verification_request_time"
    /**
     * 用户设置用户密码 上次请求验证码的手机号
     */
    const val SETTING_PASSWORD_VERIFICATION_REQUEST_PHONE = "register_verification_request_phone"
    /**
     * 是否是第一次定位
     */
    const val IS_FIRST_REQUEST_LOCATION = "is_first_request_location"

    /**
     *  关注通知未读消息数
     */
    const val NOTIFY_UNREAD_NUM_FOLLOW = "notify_unread_num_follow"

    /**
     *  点赞通知未读消息数
     */
    const val NOTIFY_UNREAD_NUM_PRAISE = "notify_unread_num_praise"

    /**
     *  评论通知未读消息数
     */
    const val NOTIFY_UNREAD_NUM_COMMENT = "notify_unread_num_comment"

    /**
     *  评选通知未读消息数
     */
    const val NOTIFY_UNREAD_NUM_SELECTION = "notify_unread_nun_selection"

    /**
     *  @我通知未读消息数
     */
    const val NOTIFY_UNREAD_NUM_AT = "notify_unread_nun_at_me"

    /**
     *  用户隐私政策概要显示次数
     */
    const val PRIVACY_SUMMARY_SHOW_NUM = "privacy_summary_show_num"

    /**
     * 邀请码
     */
    const val INVITATION_CODE = "invitation_code"

    /**
     * 显示滑动引导次数，显示一次后不再显示
     */
    const val SLIDE_GUIDE_NUM = "slie_guide_num"

    /**
     * 登录类型 1密码登录 2wx 3wb 4qq 5验证码
     */
    const val LOGIN_TYPE = "login_type"

    /**
     * 是否第一次启动应用
     */
    const val FIRST_LAUNCHER_APP = "first_launcher_app"

    const val DEVICE_BRAND = "device_brand"

    const val LOGIN_PHONE = "login_phone"

    const val IS_DEBUG = "is_debug"

    const val DYNAMIC_DOMAIN_URL = "dynamic_domain_url"


}
