package com.moli.pophelper.utils

import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.moli.module.model.http.ResponseOrder
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.module.home.ActivityFragment
import com.moli.pophelper.module.home.HomeFragment
import com.moli.pophelper.module.home.MineFragment
import com.moli.pophelper.module.home.WealthFragment
import com.moli.pophelper.module.pay.PayFragment
import com.moli.pophelper.module.pay.PayTypeListFragment
import com.moli.pophelper.module.wealth.ExchangeDiamondFragment

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

    /**
     * 支付菜单
     */
    fun payTypeListFragment(order: ResponseOrder): PayTypeListFragment {
        return ARouter.getInstance().build(HelperArouter.Fragment.PayTypeList.PATH)
            .withParcelable(HelperArouter.Fragment.PayTypeList.PAY_DATA, order)
            .navigation() as PayTypeListFragment
    }

    fun payFragment(order: ResponseOrder): PayFragment {
        return ARouter.getInstance().build(HelperArouter.Fragment.PayFragment.PATH)
            .withParcelable(HelperArouter.Fragment.PayFragment.ORDER_REQUEST, order).navigation() as PayFragment
    }

    fun exchangeDiamond(): ExchangeDiamondFragment {
        return ARouter.getInstance().build(HelperArouter.Fragment.ExchangeDiamondFragment.PATH).navigation() as ExchangeDiamondFragment
    }
}
