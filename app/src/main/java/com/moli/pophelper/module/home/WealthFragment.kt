package com.moli.pophelper.module.home

import android.Manifest
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.AppUtils
import com.moli.module.framework.base.BaseMVPFragment
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.utils.LayoutManagerUtil
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.utils.downloadPop
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_wealth.*
import org.jetbrains.anko.support.v4.ctx

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 18:42
 * 修改人：lijilong
 * 修改时间：2018/12/4 18:42
 * 修改备注：
 * @version
 */
@Route(path = HelperArouter.Fragment.WealthFragment.PATH)
class WealthFragment : BaseMVPFragment<WealthFragmentPresenter>(), IListView {
    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.fragment_wealth, container, false)
    }

    override fun initData() {
        ivLaunchGame.clicksThrottle().subscribe {
            if (AppUtils.isAppInstalled("com.moli.reward.app")) {
                AppUtils.launchApp("com.moli.reward.app")
                return@subscribe
            }
            RxPermissions(activity!!)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) {
                        downloadPop("http://a3.res.meizu.com/source/4314/7e02c314966340efa82d0400131a8f1f?auth_key=1544163256-0-0-cbeca98671ffdfa3e561f126000555b0&fname=com.moli.reward.app_276")
                    }
                }
        }
    }

    override fun createPresenter(): WealthFragmentPresenter? {
        return WealthFragmentPresenter(this)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LayoutManagerUtil.getGridLayoutManager(ctx, 2)
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView
    }

}
