package com.moli.pophelper.utils

import com.alibaba.android.arouter.launcher.ARouter
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
            .withTransition(R.anim.abc_fade_in, R.anim.abc_fade_out).navigation()
    }
}
