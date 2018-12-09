package com.moli.pophelper.utils

import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 17:00
 * 修改人：lijilong
 * 修改时间：2018/12/4 17:00
 * 修改备注：
 * @version
 */
object PageSkipUtils {

    fun skipMain() {
        ARouter.getInstance().build(HelperArouter.Activity.MainActivity.PATH)
            .withTransition(R.anim.alpha_in, R.anim.alpha_out).navigation()
    }

    fun skipStrategyList() {
        ARouter.getInstance().build(HelperArouter.Activity.StrategyListActivity.PATH).navigation()
//            .withTransition(R.anim.abc_fade_in, R.anim.abc_fade_out).navigation()
    }

    /**
     * 跳转web
     */
    fun skipGenderWeb(url: String, title: String? = null, isUrlComplete: Boolean? = true) {
        val postcard = ARouter.getInstance().build(HelperArouter.Activity.GeneralWebActivity.PATH)
            .withString(HelperArouter.Activity.GeneralWebActivity.URL, url)
            .withBoolean(
                HelperArouter.Activity.GeneralWebActivity.IS_URL_COMPLETE, isUrlComplete
                    ?: true
            )
        if (title?.isNotEmpty() == true) {
            postcard.withString(HelperArouter.Activity.GeneralWebActivity.TITLE, title)
        }
        postcard.navigation(Utils.getApp())

    }

    fun skipCodeLogin() {
        ARouter.getInstance().build(HelperArouter.Activity.LoginActivity.PATH).navigation()
    }
}
