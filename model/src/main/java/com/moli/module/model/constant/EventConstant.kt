package com.moli.module.model.constant

/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/8/6 下午7:48
 * 修改人：yuliyan
 * 修改时间：2018/8/6 下午7:48
 * 修改备注：
 * @version
 */
object EventConstant {

    /**
     * 强制更新
     */
    const val SPLASH_FORCE_UPDATE = "splash_force_update"

    /**
     * 强制更新进度
     */
    const val APP_UPDATE_PROGRESS = "app_update_progress"

    /**
     * 退出登录
     */
    const val USER_LOGOUT = "user_logout"
    /**
     * 登录成功
     */
    const val LOGIN_SUCCESS = "login_success"
    /**
     * 登录取消绑定操作
     */
    const val LOGIN_CANCEL_BIND = "login_cancel_bind"
    /**
     * 完善用户信息
     */
    const val FINISH_USER_INFO = "finish_user_info"

    /**
     * H5分享事件
     */
    const val SHARE_ACTION_H5 = "share_action_h5"

    /**
     * H5 打开本地页面
     */
    const val OPEN_APP_ACTION_H5 = "OPEN_APP_ACTION_H5"

    /**
     * H5 发布事件
     */
    const val PUBLISH_VIDEO_ACTION_H5 = "publish_video_action_h5"

    /**
     * 复制信息
     */
    const val COPY_MESSAGE = "copy_message"
    /**
     * 保存当前输入内容
     */
    const val SAVE_CURRENT_COMMENT_TEXT = "save_current_comment_text"

    /**
     * 保存当前详情输入框输入内容
     */
    const val DETAIL_SAVE_CURRENT_COMMENT_TEXT = "detail_save_current_comment_text"

    /**
     * 搜索关键字
     */
    const val SEARCH_KEY_WORD = "search_key_word"
    /**
     * 刷新图片
     */
    const val REFRESH_PUBLISH_PHOTO_SET = "refresh_publish_photo_set"
    /**
     * 刷新相片选择结果
     */
    const val REFRESH_PUBLISH_PHOTO_RESULT = "refresh_publish_photo_result"
    /**
     * 关闭相册页
     */
    const val CLOSE_ALBUM_PHOTO_PAGE = "close_album_photo_page"
    /**
     * 刷新相册列表
     */
    const val REFRESH_PUBLISH_PHOTO_RECYCLEVIEW = "refresh_publish_photo_recycleview"
    /**
     * 草稿箱条目选择
     */
    const val DRAFT_ITEM_SELECT = "draft_item_select"

    /**
     *  发送评论成功
     */
    const val SEND_COMMENT_SUCCESS = "send_comment_success"

    /**
     *MainActivity中选择的fragment
     */
    const val MAIN_ACTIVITY_FRAGMENT_POSITION = "main_activity_fragment_position"

    /**
     * 发布时 跳转到关注页，使用eventbus关闭所有的页面
     */
    const val PUBLISH_FINISH_ACTIVITY = "publish_finish_activity"

    /**
     * 发布时 保存本地，刷新视频相册
     */
    const val PUBLISH_LOCAL_REFRESH_VIDEO = "publish_local_refresh_video"

    /**
     * 发布悬赏时 显示选择礼物列表
     */
    const val PUBLISH_REWARD_GIFT_DIALOG = "publish_reward_gift_dialog"

    /**
     * 剪切音乐时 显示剪切音乐对话框
     */
    const val PUBLISH_CUT_MUSIC_DIALOG = "publish_cut_music_dialog"

    /**
     * 发布悬赏时 确认选择礼物
     */
    const val PUBLISH_REWARD_GIFT_CONFIRM = "publish_reward_gift_confirm"

    /**
     * 刷新首页关注内容接口
     */
    const val REFRESH_ATTENTION_LIST = "refresh_attention_list"

    /**
     * 获取悬赏详情中的结束时间
     */
    const val REWARD_FINISH_TIME = "reward_finish_time"

    /**
     * 举报成功
     */
    const val REPORT_SUCCESS = "report_success"

    /**
     * 绑定手机号成功
     */
    const val BIND_PHONE_SUCCESS = "bind_phone_success"

    /**
     * 不感兴趣
     */
    const val UNINTEREST_POST = "uninterest_post"

    /**
     *  删除作品
     */
    const val OPTION_DELECTED_STORY = "option_delected_story"
    /**
     *  删除评论成功
     */
    const val OPTION_DELECTED_COMMENT = "option_delected_comment"

    /**
     * 获取钱包数据
     */
    const val GET_WALLET_DATA = "get_wallet_data"

    /**
     * 评选-给红包
     */
    const val SELECTION_GIVE_RED_PACKAGE = "selection_give_red_package"

    /**
     * 通知-刷新未读消息数(关注，点赞，评论，评选)
     */
    const val NOTIFY_REFRESH_UNREAD_NUM = "notify_refresh_unread_num"

    /**
     * 小助手，系统通知->已读
     */
    const val HELPER_OR_SYSTEM_HAS_READ = "helper_or_system_has_read"

    /**
     * 刷新个人详情（我的，他人的）中的作品、悬赏、喜欢fragment
     */
    const val REFRESH_MINE_CHILD_FRAGMENT = "refresh_mine_child_fragment"

    /**
     * 关注，取消关注
     */
    const val FOLLOW_CHANGE = "follow_change"

    /**
     * 关注，取消关注，刷新未展示页的关注数据，正在显示页的不刷新notify
     */
    const val REFRESH_FOLLOW_CHANGE_NO_SHOW = "refresh_follow_change_no_show"

    /**
     *  分享成功
     */
    const val SHARE_SUCCESS = "share_success"

    /**
     * 跟新推荐视频数据
     */
    const val UPDATE_VIDEO_ITEM_DATA = "update_video_item_data"

    /**
     * 点赞改变
     */
    const val PRAISE_CHANGE = "praise_change"

    /**
     * 跟新视频评论数
     */
    const val UPDATE_VIDEO_COMMENT_COUNT = "update_video_comment_count"

    /**
     * 双击点赞
     */
    const val SHOW_PRISE_DOUBLE_CLICK = "show_prise_double_click"

    /**
     * 用户未授权，拒绝访问
     */
    const val USER_UN_AUTHORIZATION = "user_un_authorization"

    /**
     * 视频全屏事件
     */
    const val VIDEO_FULL_SCREEN = "video_full_screen"

    /**
     * 已移除小窗口ijkvideo
     */
    const val HAS_REMOVE_SMALL_VIDEO_VIEW = "has_remove_small_video_view"

    /**
     * 视频缩小事件
     */
    const val VIDEO_SMALL_SCREEN = "video_small_screen"

    /**
     * 视频已经缩小了
     */
    const val VIDEO_HAS_SMALL = "video_has_small"

    /**
     * 已移除大窗口ijkvideo
     */
    const val HAS_REMOVE_FULL_VIDEO_VIEW = "has_remove_full_video_view"

    /**
     * 视频下载成功
     */
    const val VIDEO_DOWNLOAD_SUCCESS = "video_download_success"

    /**
     * 下载转码
     */
    const val VIDEO_DOWNLOAD_GOTO_EDIT = "video_download_goto_edit"

    /**
     * 返回at用户信息
     */
    const val RETURN_AT_USERINFO = "at_user"
    /**
     * 首页RecyclerView滚动事件发生变化
     */
    const val RECYCLEVIEW_MOVE = "recyclerview_move"

    /**
     * 展示来新消息红点
     */
    const val SHOW_NEW_MESSAGE_DOT = "show_new_message_dot"

    /**
     * 隐藏来新消息红点
     */
    const val HIDE_NEW_MESSAGE_DOT = "hide_new_message_dot"

    /**
     *滑动主页跳转 0:24H;1:主界面；2：个人主页
     */
    const val SLIDE_MAIN_HOME_PAGE = "slide_main_home_page"

    const val MAIN_FRAGMENT_POSITION = "main_fragment_position"

    const val MAIN_VIEWPAGER_INTERCEPT = "main_viewpager_intercept"

    const val SLIDE_VIEWPAGER_POSITION = "side_viewpager_position"

    const val RECOMMAND_POSITION_USEERID = "recommand_position_userid"

    /**
     * 选择视频封面的位置
     */
    const val VIDEO_COVER_SELECT_POSITION = "video_cover_select_position"

    /**
     * 选择音乐
     */
    const val SELECT_MUSIC = "select_music"

    /**
     * 当前位置处于首页fragment,再点击首页开始刷新当前页
     */
    const val REFRESH_HOME_FRAGMENT = "refresh_home_fragment"

    const val REFRESH_HOME_FRAGMENT_END = "refresh_home_fragment_end"

     /** 开始视频播放
     */
    const val START_VIDEO = "start_video"
    /**
     * 暂停视频播放
     */
    const val PAUSE_VIDEO = "pause_video"

    /**
     * 收藏音乐操作
     */
    const val COLLECT_MUSIC = "collect_music"


    /**
     * 取消收藏音乐操作
     */
    const val UN_COLLECT_MUSIC = "un_collect_music"

    /**
     * 视频认证完成
     */
    const val VIDEO_AUTH_SUCCESS = "video_auth_success"

    /**
     * 身份认证完成
     */
    const val IDENTIGY_AUTH_SUCCESS = "identify_auth_success"

    /**
     *
     */
    const val NIM_LOGIN_SUCCESS = "nim_login_success"

    /**
     * 取消正在加载dialog
     */
    const val DISMISS_LOADING = "dismiss_loading"

    /**
     * 下载跟新
     */
    const val DOWNLOAD_PROGRESS = "download_progress"
}
