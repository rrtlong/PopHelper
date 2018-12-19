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
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.LayoutManagerUtil
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.GoodsModel
import com.moli.module.model.constant.EventConstant
import com.moli.module.model.http.ResponseOrder
import com.moli.module.net.manager.UserManager
import com.moli.pophelper.R
import com.moli.pophelper.constant.Constant
import com.moli.pophelper.constant.Constant.POP_DOWNLOAD_URL
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.utils.FragmentNavigationUtils
import com.moli.pophelper.utils.PageSkipUtils
import com.moli.pophelper.utils.downloadPop
import com.moli.pophelper.utils.formatNumberWithCommaSplit
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_wealth.*
import org.jetbrains.anko.support.v4.ctx
import org.simple.eventbus.Subscriber
import timber.log.Timber

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
        refreshView()
        ivLaunchGame.clicksThrottle().subscribe {
            installOrLauncher()
        }


        ivMoney.clicksThrottle().subscribe {
            //零钱记录
            if (checkSkip()) {
                PageSkipUtils.skipChangeRecord(0)
            }
        }

        ivDiamond.clicksThrottle().subscribe {
            //钻石记录
            if (checkSkip()) {
                PageSkipUtils.skipChangeRecord(1)
            }
        }

        tvReflect.clicksThrottle().subscribe {
            //体现
            if (checkSkip()) {
                PageSkipUtils.skipGetCash()
            }
        }

        tvExchangeDiamond.clicksThrottle().subscribe {
            //换钻石
            if (checkSkip()) {
                childFragmentManager.beginTransaction()
                    .add(FragmentNavigationUtils.exchangeDiamond(), HelperArouter.Fragment.ExchangeDiamondFragment.PATH)
                    .commitAllowingStateLoss()
            }
        }
    }

    override fun createPresenter(): WealthFragmentPresenter? {
        return WealthFragmentPresenter(this)
    }

    override fun handleMessage(message: MVPMessage) {
        super.handleMessage(message)
        when (message.what) {
            1 -> {
                if (!UserManager.isBind()) {
                    showMessage("请绑定游戏账号")
                    installOrLauncher()
                    return
                }
                Timber.e("pay click good")
                val good = message.obj as GoodsModel
                val orderRequest = ResponseOrder(
                    goodsId = good.id,
                    userId = UserManager.getSynSelf()?.id
                )

                val fragmentManager = childFragmentManager
                fragmentManager.let {
                    val ft = it.beginTransaction()
                    ft.add(FragmentNavigationUtils.payFragment(orderRequest), HelperArouter.Fragment.PayFragment.PATH)
                    ft.commitAllowingStateLoss()
                }
            }
        }
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LayoutManagerUtil.getGridLayoutManager(ctx, 2)
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView
    }


    override fun useEventBus(): Boolean {
        return true
    }

    @Subscriber(tag = EventConstant.LOGIN_SUCCESS)
    fun login(msg: String) {
        refreshView()
    }

    @Subscriber(tag = EventConstant.USER_LOGOUT)
    fun logout(msg: String) {
        refreshView()
    }

    fun installOrLauncher() {
        if (AppUtils.isAppInstalled(Constant.POP_PACKAGE)) {
            AppUtils.launchApp(Constant.POP_PACKAGE)
            return
        }
        RxPermissions(activity!!)
            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe {
                Timber.e("permission it=$it")
                if (it) {
                    downloadPop(POP_DOWNLOAD_URL)
                } else {
                    showMessage("请打开写内存权限")
                }
            }
    }

    fun refreshView() {
        var user = UserManager.getSynSelf()
        if (user == null) {
            tvMoney.text = "0"
            tvDiamond.text = "0"
        } else {
            tvMoney.text = formatNumberWithCommaSplit(user?.change!! / 100.0, 2)
            tvDiamond.text = formatNumberWithCommaSplit(user?.cash, 0)
        }
    }

    fun checkSkip(): Boolean {
        var canSkip = false
        if (!UserManager.isLogin()) {
            PageSkipUtils.skipCodeLogin()
            return canSkip
        }
        if (!UserManager.isBind()) {
            showMessage("请绑定游戏账号")
            installOrLauncher()
            return canSkip
        }
        return true
    }

    @Subscriber(tag = EventConstant.REFRESH_USER_INFO)
    fun refreshUser(msg: String) {
        if (rootView != null) {
            Timber.e("refresh mine data")
            refreshView()
        }
    }

}
