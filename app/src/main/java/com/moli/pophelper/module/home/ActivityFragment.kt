package com.moli.pophelper.module.home

import android.Manifest
import android.animation.ValueAnimator
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.TimeUtils
import com.moli.module.framework.base.BaseMVPFragment
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.LayoutManagerUtil
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.BannerModel
import com.moli.module.model.constant.EventConstant
import com.moli.module.net.manager.UserManager
import com.moli.module.widget.widget.BottomListener
import com.moli.module.widget.widget.FooterView
import com.moli.pophelper.R
import com.moli.pophelper.constant.Constant
import com.moli.pophelper.constant.Constant.POP_DOWNLOAD_URL
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.utils.FrescoBannerImageLoader
import com.moli.pophelper.utils.PageSkipUtils
import com.moli.pophelper.utils.downloadPop
import com.tbruyelle.rxpermissions2.RxPermissions
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_activity.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.support.v4.ctx
import org.simple.eventbus.Subscriber
import timber.log.Timber

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 18:43
 * 修改人：lijilong
 * 修改时间：2018/12/4 18:43
 * 修改备注：
 * @version
 */
@Route(path = HelperArouter.Fragment.ActFragment.PATH)
class ActivityFragment : BaseMVPFragment<ActivityFragmentPresenter>(), IListView {
    var bannerList: List<BannerModel>? = null
    var leftPoint = 0
    var rightPoint = 0
    var deltaX = 0

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun initData() {
        initBanner()
        presenter?.getBanner()
        leftPoint = ctx.dip(30 + 2 + 3)
        rightPoint = ScreenUtils.getScreenWidth() - leftPoint
        deltaX = (rightPoint - leftPoint) / 7
        tvSign.isSelected = true
        tvReward.text ="0"
        refreshSign()

        ivLaunchGame.clicksThrottle().subscribe {
            installOrLauncher()
        }

        tvSign.clicksThrottle().subscribe {
            if (!UserManager.isLogin()) {
                PageSkipUtils.skipCodeLogin()
                return@subscribe
            }
            if (!UserManager.isBind()) {
                showMessage("请绑定游戏账号")
                installOrLauncher()
                return@subscribe
            }
            startSign()
        }
        scrollView.setBottomListener(object : BottomListener {
            override fun onBottom() {
                presenter?.loadMore()
            }

        })


    }

    override fun createPresenter(): ActivityFragmentPresenter? {
        return ActivityFragmentPresenter(this)
    }

    override fun handleMessage(message: MVPMessage) {
        super.handleMessage(message)
        when (message.what) {
            1 -> {
                bannerList = message.obj as List<BannerModel>
                setBannerData()
            }
            3 -> {
                //签到成功
                signing = false
                tvSign.isSelected = false
                var user = UserManager.getSynSelf()
                user?.let {
                    user.signTimes = user.signTimes + 1
                    user.signDate = System.currentTimeMillis()
                    UserManager.refreshUserInfo(user, false)
                }

            }
            4 -> {
                //签到失败
                signing = false
                UserManager.getSynSelf()?.let {
                    signAnim(it.signTimes - 1, true)
                }

            }
        }
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LayoutManagerUtil.getLinearLayoutManager(ctx, true, false)
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView
    }


    fun initBanner() {
        banner.setImageLoader(FrescoBannerImageLoader(ScreenUtils.getScreenWidth(), ctx.dip(152)))
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner.setIndicatorGravity(BannerConfig.CENTER)
        banner.setDelayTime(5000)
        banner.setPadding(banner.paddingLeft, banner.paddingTop, banner.paddingRight, 0)
        banner.setOnBannerListener {
            if (bannerList?.size ?: 0 > it) {
                val item = bannerList!![it]
                PageSkipUtils.skipGenderWeb(item.contentUrl ?: "")
            }
        }
    }

    fun setBannerData() {
        banner.setImages(bannerList)
        banner.start()
    }

    fun startSign() {
        if (signing) {
            return
        }
        var user = UserManager.getSynSelf()
        user?.let {
            if (it.signDate != null && TimeUtils.isToday(it.signDate!!)) {
                showMessage("今天已签到")
                return@let
            }
            signAnim(user.signTimes)
        }
    }

    var signing = false
    fun signAnim(day: Int, reset: Boolean = false) {
        var user = UserManager.getSynSelf()
        signing = true
        var left = deltaX * (day + 1) + ctx.dip(12)
        var param = ivCursor.layoutParams as ConstraintLayout.LayoutParams
        var oldLeft = param.leftMargin
        var valueAnim = ValueAnimator.ofInt(oldLeft, left)
        valueAnim.addUpdateListener { animation ->
            var tempLeft = animation.animatedValue as Int
            param.leftMargin = tempLeft
            ivCursor.layoutParams = param
            if (tempLeft == left) {
                if (!reset) {
                    if (user!!.signTimes == 0) {
                        tvReward.text = user?.signConfig!![0].signText
                    } else {
                        tvReward.text = user?.signConfig!![user.signTimes].signText
                    }
                    presenter?.sign()
                } else {
                    signing = false
                    if (user!!.signTimes == 0) {
                        tvReward.text = "0"
                    } else {
                        tvReward.text = user?.signConfig!![user.signTimes - 1].signText
                    }
                }
            }
        }
        valueAnim.interpolator = AccelerateDecelerateInterpolator()
        valueAnim.duration = 500
        valueAnim.start()
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

    override fun getFooterView(): View? {
        return FooterView(ctx)
    }

    override fun useEventBus(): Boolean {
        return true
    }

    @Subscriber(tag = EventConstant.LOGIN_SUCCESS)
    fun loginSuccess(msg: String) {
        refreshSign()
    }

    @Subscriber(tag = EventConstant.USER_LOGOUT)
    fun logout(msg: String) {
        tvSign.isSelected = true
        var left = ctx.dip(12)
        var param = ivCursor.layoutParams as ConstraintLayout.LayoutParams
        param.leftMargin = left
        ivCursor.layoutParams = param
        tvReward.text = "0"
    }

    fun refreshSign() {
        var user = UserManager.getSynSelf()
        user?.let {
            if (it.signTimes == 0) {
                tvReward.text = "0"
            } else {
                tvReward.text = it.signConfig!![it.signTimes - 1].signText
                var left = deltaX * (it.signTimes) + ctx.dip(12)
                var param = ivCursor.layoutParams as ConstraintLayout.LayoutParams
                param.leftMargin = left
                ivCursor.layoutParams = param
            }
            if (it.signDate == null) {
                tvSign.isSelected = true
            } else {
                tvSign.isSelected = !TimeUtils.isToday(it.signDate!!)
            }
        }
    }
}
