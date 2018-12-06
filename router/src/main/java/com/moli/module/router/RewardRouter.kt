package com.moli.module.router

/**
 * 项目名称：RewardApp
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/9/10 下午3:41
 * 修改人：yuliyan
 * 修改时间：2018/9/10 下午3:41
 * 修改备注：
 * @version
 */
class RewardRouter {

    /**
     * Activity的路由表
     */
    object Activity {

        object Main {
            const val PATH = "/main/main"
        }


        /**
         * 手机登录
         */
        object Login {
            const val PATH = "/login/login"
        }

        /**
         * 三方登录
         */

        object LoginThreeWay {
            const val PATH = "/login/loginThreeWay"
        }

        /**
         * 输入密码页
         */
        object LoginPassword {
            const val PATH = "/login/loginPassword"
            const val PHONE = "phone"
        }

        /**
         * 绑定手机号
         */
        object LoginBindPhone {
            const val PATH = "/login/loginBindPhone"
        }

        /**
         * 登录绑定手机号next（下一步）
         */
        object LoginBindPhoneNext {
            const val PATH = "/login/loginBindPhoneNext"

            const val PHONE = "phone"
        }

        /**
         * 登录-找回密码
         */
        object LoginFindBackPassword {
            const val PATH = "/login/loginFindBackPassword"

            const val PHONE = "phone"
        }


        /**
         * web页
         */
        object GeneralWeb {
            const val PATH = "/general/generalWeb"
            const val TITLE = "title"
            const val URL = "url"
            const val IS_URL_COMPLETE = "isUrlComplete"
            const val TYPE = "type"  //默认0：普通类型 1：开屏广告点击进入，返回时跳到主界面
        }


        /**
         * 设置用户信息
         */
        object Set {
            const val PATH = "/mine/set"
        }

        /**
         * 发布页
         */
        object PublishVideoActivity {
            const val PATH = "/publish/publishVideo"
            const val REWARD_PARAM = "rewardParam"
        }

        /**
         * 发布列表
         */
        object PublishListActivity {
            const val PATH = "/publish/publishListActivity"
        }

        /**
         * 发布悬赏
         */
        object PublishRewardActivity {
            const val PATH = "/publish/publishReward"
            const val COME_TYPE = "comeType" ////0表示来自本地发布悬赏，1表示H5发布悬赏
        }

        /**
         * 第三方视频搜索
         */
        object LinkSearchActivity {
            const val PATH = "/publish/linkSearch"
            const val REWARD_PARAM = "rewardParam"
        }

        /**
         * 编辑视频
         */
        object EditVideoRecordActivity {
            const val PATH = "/publish/editVideo"
            const val REWARD_PARAM = "rewardParam"
            const val MUSIC_MODEL = "musicModel"
        }

        /**
         * 剪辑视频
         */
        object CutVideoActivity {
            const val PATH = "/publish/cutVideo"
            const val REWARD_PARAM = "rewardParam"
        }

        /**
         * 录制视频
         */
        object RecordVideoActivity {
            const val PATH = "/publish/video"
            const val REWARD_PARAM = "rewardParam"
            const val MUSIC_MODEL = "musicModel"
        }

        /**
         * 合拍视频
         */
        object RecordCompositeActivity {
            const val PATH = "/publish/compositeVideo"
            const val VIDEO_DATA = "videoData"
            const val REWARD_PARAM = "rewardParam"
        }


        /**
         * 个人信息页
         */
        object PersonalInfo {
            const val PATH = "/mine/personalinfo"
        }

        /**相册选择*/
        object AlbumDialog {
            const val PATH = "/album/dialog"
        }

        /**相册图片选择*/
        object AlbumSelect {
            const val PATH = "/album/select"
            const val CURRENT_POST = "currentPos"
            const val IMAGE_LIST = "imageList"
        }

        /**
         *搜索
         */
        object SearchActivity {
            const val PATH = "/search/search"
        }

        /**
         * 24小时
         */
        object HoursActivity {
            const val PATH = "/home/hours"
        }

        /**
         * 草稿箱
         */
        object DraftListActivity {
            const val PATH = "/mine/draft"
        }

        /**
         * 我的钱包
         */
        object MyWalletActivity {
            const val PATH = "/mine/wallet"
        }

        /**
         * 我的钱包-账单
         */
        object BillActiviy {
            const val PATH = "/mine/bill/activity"
        }

        /**
         * 我的钱包-金币提现
         */
        object GetCashActivity {
            const val PATH = "/mine/getcash"
        }

        /**
         * 我的钱包-金币兑换
         */
        object GoldExchangeActivity {
            const val PATH = "/mine/goldExchange"
        }

        /**
         * 账号与安全
         */
        object AccountAndSecurityActivity {
            const val PATH = "/mine/accountAndSecurity"
        }

        /**
         * 账号与安全-设置密码或则修改密码
         */
        object SetOrModifyPasswordActivity {
            const val PATH = "/mine/setOrModify/password"
            const val TYPE = "type" //0:设置密码 1：修改密码
        }

        /**
         * 绑定手机号第一步
         */
        object BindPhoneStep1Activity {
            const val PATH = "/bindPhone/step1"
        }

        /**
         * 绑定手机号第二步
         */
        object BindPhoneStep2Activity {
            const val PATH = "/bindPhone/step2"
            const val PHONE = "phone"
        }

        /**
         * 邀请别人参与自己的悬赏
         */
        object InvitationActivity {
            const val PATH = "/mine/invitation"
        }

        /**
         *  悬赏视频
         */
        object RewardUserVideo {
            const val PATH = "/detail/rewardUserVideo"
            const val STORY_ID = "storyId"
            const val REWARD_ID = "rewardId"
        }

        /**
         * 其他个人主页
         */
        object OtherHomepage {
            const val PATH = "/detail/otherHomepage"
            const val USER_ID = "userId"
        }

        /**
         * 悬赏详情列表
         */
        object RewardDetailList {
            const val PATH = "/reward/rewardDetailList"
            //悬赏类型 1：红包悬赏 2：排名悬赏
            const val REWARD_TYPE = "rewardType"
            const val REWARD_ID = "rewardId"
        }

        object RewardStoryPlayList {
            const val PATH = "/reward/rewardStoryPlayList"
            const val POSITION = "position"
            const val REWARD_ID = "rewardId"
            const val USER_ID = "userId"
            const val TYPE = "type"
            const val SORT = "sort"  //sort 排序方式 0创建时间倒序 1获奖时间 2排名 3创建时间正序
            const val MUSIC_ID = "musicId"
        }

        object Test {
            const val PATH = "/home/test"
        }

        /**
         * 消息-粉丝
         */
        object MessageFansActivity {
            const val PATH = "/message/fans"
            const val TYPE = "type"
            const val USER_ID = "userId"
        }

        /**
         * 消息-赞
         */
        object MessagePraiseActivity {
            const val PATH = "/message/praise"
        }

        /**
         * 消息-评论
         */
        object MessageCommentActivity {
            const val PATH = "/message/comment"
        }

        /**
         * 消息-@我
         */
        object MessageAtActivity {
            const val PATH = "/message/at"
        }

        /**
         * 消息-评选
         */
        object MessageSelectionActivity {
            const val PATH = "/message/selection"
        }

        /**
         * 单聊
         */
        object ChatActivity {
            const val PATH = "/message/chat"
            const val SESSIONID = "sessionId"
        }

        /**
         * CC小助手，系统通知这类推送页面
         */
        object PushActivity {
            const val PATH = "/message/push"
            const val TYPE = "type"
            const val ACCOUNT = "account"
        }

        /**
         * 评选界面-选喜欢的作品
         */
        object SelectionDetailActivity {
            const val PATH = "/reward/selection/activity"
            const val REWARD_ID = "rewardId"
            const val CONTENT = "content"
            const val CONTENTS = "contents"
        }


        /**
         * 评论列表面板
         */
        object Comment {
            const val PATH = "/main/comment"
            const val COMMENT_DATA = "commentData"

        }

        /**
         * 评论框box面板
         */
        object CommentBox {
            const val PATH = "/main/commentBox"
            const val TEXT = "text"
            const val COMMENT_TYPE = "commentType"
            const val TYPE_ID = "typeId"
            const val COMMENT_ID = "commentId"
            const val RESPONSE_COMMENT = "responseComment"
        }

        /**
         * 详情页的评论box面包
         */
        object DetailCommentBox {
            const val PATH = "/main/commentBoxDetail"
            const val TEXT = "text"
            const val COMMENT_TYPE = "commentType"
            const val TYPE_ID = "typeId"
            const val COMMENT_ID = "commentId"
        }

        /**
         * 关注列表视频播放全屏化
         */
        object FullAttentionActivity {
            const val PATH = "/main/fullAttention"
            const val MODEL = "model"
            const val SEEK_POSITION = "seekPosition"
            const val TOP = "top"
            const val LEFT = "left"
        }

        object CoProductionPL {
            const val PATH = "/publish/coProductionPL"
            const val VIDEO_DATA = "videoData"
            const val REWARD_PARAM = "rewardParam"
        }

        /**
         * @列表
         */
        object AtActivity {
            const val PATH = "/at/atActivity"
        }

        object SlideActivity {
            const val PATH = "/main/slide"
        }

        object VideoCoverSelectActivity {
            const val PATH = "/publish/cover"
            const val VIDEO_PATH = "videoPath"
        }

        object MusicSelectActivity {
            const val PATH = "/music/selectActivity"
        }

        object MusicDetailActivity {
            const val PATH = "/music/detail"
            const val MUSIC_ID = "musicId"
        }

        object IdentifyAuthActivity {
            const val PATH = "/auth/identify"
            const val AUTH_MODEL = "authModel"
        }

        object VideoAuthActivity {
            const val PATH = "/auth/video"
        }

        object VideoAuthPlayActivity {
            const val PATH = "/auth/play"
            const val IS_MINE = "isMine"
            const val AUTH_MODEL = "authModel"
        }

        object SubmitVideoAuthActivity {
            const val PATH = "/auth/submitVideo"
            const val REWARD_PARAM = "rewardParam"
        }
    }

    /**
     * Fragment的路由表
     */
    object Fragment {
        /**
         * 首页
         */
        object HomeMain {
            const val PATH = "/main/home"
        }

        /**
         * 悬赏
         */
        object Reward {
            const val PATH = "/main/reward"
        }

        /**
         * 消息
         */
        object Message {
            const val PATH = "/main/message"
        }

        /**
         * 我
         */
        object Mine {
            const val PATH = "/main/mine"
        }

        /**
         * 首页关注
         */
        object HomeAttention {
            const val PATH = "/home/attention"
        }

        object HomeAttentionUser {
            const val PATH = "/home/attentionUser"
        }

        /**
         * 首页推荐
         */
        object HomeRecommend {
            const val PATH = "/home/recommend"
        }

        /**
         *  分享面板
         */
        object Share {
            const val PATH = "/main/share"

            const val SHARE_DATA = "shareData"

            const val OPTION_DATA = "optionData"
        }


        /**
         * 发布面板
         */
        object Publish {
            const val PATH = "/publish/index"
        }

        /**
         * 发布红包面板
         */
        object PublishRewardRedPacket {
            const val PATH = "/publish/redPacket"
        }

        /**
         * 发布 排名面板
         */
        object PublishRank {
            const val PATH = "/publish/rank"
        }

        /**
         * 发布 礼物面板
         */
        object PublishGift {
            const val PATH = "/publish/gift"
        }

        /**
         * 发布 礼物面板
         */
        object PublishGiftSelect {
            const val PATH = "/publish/giftSelect"
        }


        /**
         * 发布 列表
         */
        object PublishList {
            const val PATH = "/publish/list"
        }

        /**
         * 搜索-推荐
         */

        object SearchRecommend {
            const val PATH = "/search/searchRecommend"
        }

        /**
         *  搜索用户
         */

        object SearchUser {
            const val PATH = "/search/searchUser"
            const val KEY_WORD = "keyWord"

        }

        /**
         * 我的页面-作品
         */
        object WorksListFragment {
            const val PATH = "/mine/worksList"
            const val USER_ID = "userId"
            const val REWARD_ID = "rewardId"
            const val IS_MINE = "isMine"
        }

        /**
         * 我的页面-悬赏
         */
        object RewardListFragment {
            const val PATH = "/mine/rewardList"
            const val USER_ID = "userId"
            const val IS_MINE = "isMine"
        }

        /**
         * 我的页面-喜欢
         */
        object LikeListFragment {
            const val PATH = "/mine/likeList"
        }

        /**
         * 我的钱包-钻石
         */
        object DiamondFragment {
            const val PATH = "/mine/diamond"
        }

        /**
         * 我的钱包-金币
         */
        object GoldFragment {
            const val PATH = "/mine/gold"
        }

        /**
         * 我的钱包-账单
         */
        object BillFragment {
            const val PATH = "/mine/bill/fragment"
            const val TYPE = "type"
        }

        /**
         *  首页- 举报
         */
        object ReportFragment {
            const val PATH = "/home/report"
            const val OPTION_DATA = "optionData"
        }


        /**
         * 悬赏-排名列表
         */
        object RewardSortList {
            const val PATH = "/reward/videoList"
            const val REWARD = "rewardData"
        }

        /**
         *悬赏- 视频列表
         */
        object RewardVideoList {
            const val PATH = "/reward/sortList"
            const val REWARD_ID = "rewardId"
        }

        /**
         * 悬赏-红包列表（获奖清单）
         */
        object RewardRedPackageList {
            const val PATH = "/reward/redPackageList"
            const val REWARD = "rewardData"
        }

        /**
         * 支付-菜单
         */
        object PayTypeList {
            const val PATH = "/main/payTypeList"
            const val PAY_DATA = "payData"
        }

        /**
         * 回复长按菜单
         */
        object CommentMenu {
            const val PATH = "/comment/commentMenu"
            const val COMMENT_DATA = "commentData"
            const val POSITION = "position"
            const val COMMENT_TYPE = "commentType"
            const val TYPE_ID = "typeId"
        }


        /**
         * 悬赏-评选 给红包列表
         */
        object SelectionDetailList {
            const val PATH = "/reward/selection"
            const val REWARD_ID = "rewardId"
            const val IS_REWARD = "isReward"
        }

        /**
         * 下载视频对话框
         */
        object VideoDownLoadDialog {
            const val PATH = "/reward/videoDownLoad"
            const val DOWNLOAD_URL = "downLoadUrl"
            const val IS_NEED_WATER = "isNeedWater"
        }

        /**
         * @时显示的关注列表fragment
         */
        object AtFollowFragment {
            const val PATH = "/at/atFollow"
        }

        object MainFragment {
            const val PATH = "/fragment/main"
        }

        object OtherHomepageFragment {
            const val PATH = "/fragment/other"
        }

        object TwentyFourFragment {
            const val PATH = "/fragment/twentyFour"
        }

        object MusicChildFragment {
            const val PATH = "/music/childFragment"
            const val TYPE = "type"
        }

        object MusicFragment {
            const val PATH = "/music/fragment"
        }
    }

    /**
     * Service  一些API服务和工具类的路由表
     */

    object Service {

        /**
         * 接口API
         */
        const val API = "/api/APIService"
        /**
         * 统计服务
         */
        const val STATISTICS = "/api/StatisticsService"
        /**
         * 传递自定义对象使用JSON序列化
         */
        const val JSON_SERIALIZATION = "/service/json"

        /**
         * 接口PingxxAPI
         */
        const val PINGXX_API = "/api/PingXXAPIService"

        /**
         * 下载接口
         */
        const val DOWNLOAD = "/api/download"

    }
}
