package com.moli.pophelper.module.home

import android.Manifest
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ScreenUtils
import com.moli.module.framework.base.BaseMVPFragment
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.LayoutManagerUtil
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.BannerModel
import com.moli.module.model.base.MusicModel
import com.moli.module.net.imageloader.loadImage
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.utils.FrescoBannerImageLoader
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.support.v4.ctx
import android.Manifest.permission
import android.graphics.ColorSpace
import com.blankj.utilcode.util.AppUtils
import com.moli.module.model.base.ProgressModel
import com.moli.module.model.constant.EventConstant
import com.moli.module.widget.widget.dialog.DownloadProcessDialog
import com.moli.pophelper.R.id.recyclerView
import com.moli.pophelper.utils.downloadPop
import com.tbruyelle.rxpermissions2.RxPermissions
import org.simple.eventbus.EventBus
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
    var goods: List<MusicModel>? = null
    var dotView = mutableListOf<TextView>()
    val downloadDialog by lazy { DownloadProcessDialog(ctx) }
    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
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
                    Timber.e("permission it=$it")
                    if (it) {
                        downloadPop("http://a3.res.meizu.com/source/4314/7e02c314966340efa82d0400131a8f1f?auth_key=1544163256-0-0-cbeca98671ffdfa3e561f126000555b0&fname=com.moli.reward.app_276")
                    }else{
                        showMessage("请打开写内存权限")
                    }
                }
        }
        ivGoodsMore.clicksThrottle().subscribe {

        }
        ivStrategyMore.clicksThrottle().subscribe {

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
            var dot = LayoutInflater.from(ctx).inflate(R.layout.dot_layout, null, false)
            dot.isSelected = (i == 0)
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
                goods = message.obj as List<MusicModel>
                if (goods?.size ?: 0 > 0) {
                    ivGood1.loadImage(goods!![0].thumb)
                    ivGood1.clicksThrottle().subscribe {

                    }
                }
                if (goods?.size ?: 0 > 1) {
                    ivGood2.loadImage(goods!![1].thumb)
                    ivGood2.clicksThrottle().subscribe {

                    }
                }
            }
        }
    }
}
