package com.moli.pophelper.module.home

import android.Manifest
import android.animation.ValueAnimator
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
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
import com.moli.module.model.base.UserInfo
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
        leftPoint = ctx.dip(30 + 2 + 6)
        rightPoint = ScreenUtils.getScreenWidth() - leftPoint
        deltaX = (rightPoint - leftPoint) / 6
        tvSign.isSelected = true
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
        signing = true
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
        if (!reset && day == 0) {
            setReward(user!!, 1)
            setTips(user, 1)
            presenter?.sign()
            return
        } else if (reset && day == -1) {
            setReward(user!!, 0)
            setTips(user, 0)
            return
        }

        var left = deltaX * (day) + ctx.dip(17)
        var param = ivCursor.layoutParams as ConstraintLayout.LayoutParams
        var oldLeft = param.leftMargin
        var valueAnim = ValueAnimator.ofInt(oldLeft, left)
        valueAnim.addUpdateListener { animation ->
            var tempLeft = animation.animatedValue as Int
            param.leftMargin = tempLeft
            ivCursor.layoutParams = param
            if (tempLeft == left) {
                if (!reset) {
                    setReward(user!!, user.signTimes + 1)
                    setTips(user, user!!.signTimes + 1)
                    presenter?.sign()
                } else {
                    signing = false
                    setReward(user!!, user.signTimes)
                    setTips(user, user!!.signTimes)
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
        refreshSign()
    }

    fun refreshSign() {
        var user = UserManager.getSynSelf()
        if (user == null) {
            setReward(null, 0)
            setTips(null, 0)
            tvSign.isSelected = true
            return
        }
        user?.let {
            if (it.signTimes == 7 && (it.signDate == null || !TimeUtils.isToday(it.signDate!!))) {
                it.signTimes = 0
            }
            setReward(it, it.signTimes)
            setTips(it, it.signTimes)
            if (it.signDate == null) {
                tvSign.isSelected = true
            } else {
                tvSign.isSelected = !TimeUtils.isToday(it.signDate!!)
            }
        }
    }

    fun setTips(user: UserInfo?, day: Int) {
        if (user == null) {
            setTip(t1, v1, 0, "500", false)
            setTip(t2, v2, 0, "500", false)
            setTip(t3, v3, 0, "1000", false)
            setTip(t4, v4, 0, "1000", false)
            setTip(t5, v5, 0, "1500", false)
            setTip(t6, v6, 0, "1500", false)
            setTip(t7, v7, 0, "2500", false)
        } else {
            setTip(t1, v1, user.signConfig!![0].rewardType, user.signConfig!![0].rewardNum!!, day >= 1)
            setTip(t2, v2, user.signConfig!![1].rewardType, user.signConfig!![1].rewardNum!!, day >= 2)
            setTip(t3, v3, user.signConfig!![2].rewardType, user.signConfig!![2].rewardNum!!, day >= 3)
            setTip(t4, v4, user.signConfig!![3].rewardType, user.signConfig!![3].rewardNum!!, day >= 4)
            setTip(t5, v5, user.signConfig!![4].rewardType, user.signConfig!![4].rewardNum!!, day >= 5)
            setTip(t6, v6, user.signConfig!![5].rewardType, user.signConfig!![5].rewardNum!!, day >= 6)
            setTip(t7, v7, user.signConfig!![6].rewardType, user.signConfig!![6].rewardNum!!, day >= 7)
        }
    }

    fun setTip(tv: TextView, iv: ImageView, type: Int, rewardNum: String, isFinish: Boolean) {
        tv.setBackgroundResource(if (isFinish) R.drawable.shape_322200_corner_50 else R.drawable.shape_e2e2e2_corner_50)
        tv.setTextColor(ContextCompat.getColor(ctx, if (isFinish) R.color.color_fdf055 else R.color.color_909090))
        tv.text = rewardNum
        var drawableId = if (type == 0) {
            if (isFinish) R.drawable.icon_gold_light else R.drawable.icon_gold_dark
        } else {
            if (isFinish) R.drawable.icon_diamond_light else R.drawable.icon_diamond_drak
        }
        iv.setImageResource(drawableId)
    }


    fun setReward(user: UserInfo?, day: Int) {
        var drawableId = 0
        var reward = ""
        if (day == 0) {
            drawableId = R.drawable.icon_gold_light
            reward = "0"
            setCursorPosition(0)
            setCursorShow(false)
        } else {
            var left = deltaX * (day - 1) + ctx.dip(17)
            setCursorPosition(left)
            setCursorShow(true)
            drawableId = if (user!!.signConfig!![day - 1].rewardType == 0) {
                R.drawable.icon_gold_light
            } else {
                R.drawable.icon_diamond_light
            }
            reward = user!!.signConfig!![day - 1].rewardNum!!
        }

        var drawableLeft = resources.getDrawable(drawableId)
        drawableLeft.setBounds(0, 0, drawableLeft.minimumWidth, drawableLeft.minimumHeight)
        tvReward.setCompoundDrawables(drawableLeft, null, null, null)
        tvReward.text = reward

    }

    fun setCursorShow(isShow: Boolean) {
        tvReward.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
        ivCursor.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
    }

    fun setCursorPosition(lpx: Int) {
        var left = lpx
        var param = ivCursor.layoutParams as ConstraintLayout.LayoutParams
        param.leftMargin = left
        ivCursor.layoutParams = param
    }

}
