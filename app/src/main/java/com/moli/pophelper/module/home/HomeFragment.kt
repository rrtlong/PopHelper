package com.moli.pophelper.module.home

import android.Manifest
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ScreenUtils
import com.moli.module.framework.base.BaseMVPFragment
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.LayoutManagerUtil
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.BannerModel
import com.moli.module.model.base.GoodsModel
import com.moli.module.model.constant.EventConstant
import com.moli.module.model.constant.SPConstant
import com.moli.module.model.http.ResponseOrder
import com.moli.module.net.imageloader.loadImage
import com.moli.module.net.manager.UserManager
import com.moli.module.widget.widget.dialog.DownloadProcessDialog
import com.moli.pophelper.MainActivity
import com.moli.pophelper.R
import com.moli.pophelper.constant.Constant
import com.moli.pophelper.constant.Constant.POP_DOWNLOAD_URL
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.utils.FragmentNavigationUtils
import com.moli.pophelper.utils.FrescoBannerImageLoader
import com.moli.pophelper.utils.PageSkipUtils
import com.moli.pophelper.utils.downloadPop
import com.tbruyelle.rxpermissions2.RxPermissions
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.dip
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
@Route(path = HelperArouter.Fragment.HomeFragment.PATH)
class HomeFragment : BaseMVPFragment<HomeFragmentPresenter>(), IListView {
    var bannerList: List<BannerModel>? = null
    var goods: List<GoodsModel>? = null
    var dotView = mutableListOf<TextView>()
    val downloadDialog by lazy { DownloadProcessDialog(ctx) }
    var isDebug = false


    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun initData() {
        isDebug = SPUtils.getInstance().getInt(SPConstant.IS_DEBUG, 0) == 1
        tvRecommandGoods.visibility = if (isDebug) View.GONE else View.VISIBLE
        ivGoodsMore.visibility = if (isDebug) View.GONE else View.VISIBLE
        ivGood1.visibility = if (isDebug) View.GONE else View.VISIBLE
        ivGood2.visibility = if (isDebug) View.GONE else View.VISIBLE

        ivLaunchGame.clicksThrottle().subscribe {
            Timber.e("Constant.POP_PACKAGE=${Constant.POP_PACKAGE}")
            installOrLauncher()
        }
        ivGoodsMore.clicksThrottle().subscribe {
            Timber.e("ivGoodsMore click")
            (activity as MainActivity).setFragment(1)
        }
        ivStrategyMore.clicksThrottle().subscribe {
            Timber.e("ivStrategyMore click")
            PageSkipUtils.skipStrategyList()
        }
        initBanner()
        presenter?.getBanner()
        presenter?.getRecommandGoods()
    }

    override fun createPresenter(): HomeFragmentPresenter? {
        return HomeFragmentPresenter(this)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LayoutManagerUtil.getLinearLayoutManager(ctx, true, false)
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView
    }

    fun initBanner() {
        banner.setImageLoader(FrescoBannerImageLoader(ScreenUtils.getScreenWidth(), ctx.dip(152)))
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR)
        banner.setIndicatorGravity(BannerConfig.LEFT)
        banner.setDelayTime(5000)
        banner.setPadding(banner.paddingLeft, banner.paddingTop, banner.paddingRight, 0)
        banner.setOnBannerListener {
            if (bannerList?.size ?: 0 > it) {
                val item = bannerList!![it]
                if (item.contentUrl != null && item.contentUrl != "") {
                    PageSkipUtils.skipGenderWeb(item.contentUrl!!)
                }
            }
        }
        banner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until bannerList!!.size) {
                    dotView[i].isSelected = (i == position)
                }
            }

        })
    }

    fun setBannerData() {
        banner.setImages(bannerList)
        for (i in 0 until bannerList!!.size) {
            var dot = LayoutInflater.from(ctx).inflate(R.layout.dot_layout, null, false) as TextView
            dot.isSelected = (i == 0)
            dotView.add(dot)
            llDotContainer.addView(dot)
        }
        banner.start()

    }

    override fun handleMessage(message: MVPMessage) {
        super.handleMessage(message)
        when (message.what) {
            1 -> {
                bannerList = message.obj as List<BannerModel>
                setBannerData()
            }
            2 -> {
                goods = message.obj as List<GoodsModel>
                refreshGoods()
            }
        }
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

    fun openPayFragment(goodsModel: GoodsModel) {
        if (!checkSkip()) return

        val orderRequest = ResponseOrder(
            goodsId = goodsModel.id,
            userId = UserManager.getSynSelf()?.id
        )

        val fragmentManager = childFragmentManager
        fragmentManager.let {
            val ft = it.beginTransaction()
            ft.add(
                FragmentNavigationUtils.payFragment(orderRequest),
                HelperArouter.Fragment.PayFragment.PATH
            )
            ft.commitAllowingStateLoss()
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

    fun refreshGoods() {
        goods?.let {
            var user = UserManager.getSynSelf()
            if (goods?.size ?: 0 > 0) {
                if (user != null && user.doubleRechargeFlag?.size ?: 0 >= goods!![0].id && user.doubleRechargeFlag!![goods!![0].id] == 0) {
                    ivGood1.loadImage(goods!![0].doubleImge)
                } else {
                    ivGood1.loadImage(goods!![0].imge)
                }
                ivGood1.clicksThrottle().subscribe {
                    openPayFragment(goods!![0])
                }
            }
            if (goods?.size ?: 0 > 1) {
                if (user != null && user.doubleRechargeFlag?.size ?: 0 >= goods!![1].id && user.doubleRechargeFlag!![goods!![1].id] == 0) {
                    ivGood2.loadImage(goods!![1].doubleImge)
                } else {
                    ivGood2.loadImage(goods!![1].imge)
                }

                ivGood2.clicksThrottle().subscribe {
                    openPayFragment(goods!![1])
                }
            }
        }
    }

    @Subscriber(tag = EventConstant.LOGIN_SUCCESS)
    fun login(msg: String) {
        refreshGoods()
    }

    @Subscriber(tag = EventConstant.USER_LOGOUT)
    fun logout(msg: String) {
        refreshGoods()
    }

    override fun useEventBus(): Boolean {
        return true
    }

}
