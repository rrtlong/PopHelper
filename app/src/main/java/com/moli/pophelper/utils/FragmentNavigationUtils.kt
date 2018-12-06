package com.moli.pophelper.utils

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.module.home.ActivityFragment
import com.moli.pophelper.module.home.HomeFragment
import com.moli.pophelper.module.home.MineFragment
import com.moli.pophelper.module.home.WealthFragment

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/5 15:00
 * 修改人：lijilong
 * 修改时间：2018/12/5 15:00
 * 修改备注：
 * @version
 */
object FragmentNavigationUtils {

    fun homeFragment(): HomeFragment {
        return ARouter.getInstance().build(HelperArouter.Fragment.HomeFragment.PATH).navigation(Utils.getApp()) as HomeFragment
    }

    fun wealthFragment(): WealthFragment {
        return ARouter.getInstance().build(HelperArouter.Fragment.WealthFragment.PATH).navigation(Utils.getApp()) as WealthFragment
    }

    fun actFragment(): ActivityFragment {
        return ARouter.getInstance().build(HelperArouter.Fragment.ActFragment.PATH).navigation(Utils.getApp()) as ActivityFragment
    }

    fun mineFragment(): MineFragment {
        return ARouter.getInstance().build(HelperArouter.Fragment.MineFragment.PATH).navigation(Utils.getApp()) as MineFragment
    }

}
