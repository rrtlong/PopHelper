package com.moli.pophelper.module.home

import android.Manifest
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ScreenUtils
import com.moli.module.framework.base.BaseMVPFragment
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.BannerModel
import com.moli.module.model.constant.EventConstant
import com.moli.module.net.imageloader.loadImage
import com.moli.module.net.manager.UserManager
import com.moli.module.widget.widget.dialog.CommonDialogWithoutTitle
import com.moli.pophelper.R
import com.moli.pophelper.constant.Constant
import com.moli.pophelper.constant.Constant.POP_DOWNLOAD_URL
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.utils.FrescoBannerImageLoader
import com.moli.pophelper.utils.PageSkipUtils
import com.moli.pophelper.utils.downloadPop
import com.tbruyelle.rxpermissions2.RxPermissions
import com.youth.banner.BannerConfig
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.support.v4.ctx
import org.simple.eventbus.Subscriber
import timber.log.Timber
import java.util.concurrent.TimeUnit

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
@Route(path = HelperArouter.Fragment.MineFragment.PATH)
class MineFragment : BaseMVPFragment<MineFragmentPresenter>(), IView {
    var bannerList: List<BannerModel>? = null
    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun initData() {
        refreshView()
        initBanner()
        presenter?.getBanner()
        ivLaunchGame.clicksThrottle().subscribe {
            installOrLauncher()
        }
        mlCover.clicksThrottle().subscribe {
            UserManager.getSynSelf()?.let {
                if (it.id == 100000L) {
                    installOrLauncher()
                }
            }
        }
        tvName.clicksThrottle().subscribe {
            UserManager.getSynSelf()?.let {
                if (it.id == 100000L) {
                    installOrLauncher()
                }
            }
        }
        tvExit.clicksThrottle().subscribe {
            CommonDialogWithoutTitle.showConfirm(
                ctx,
                "退出登录",
                "确认",
                "取消",
                object : CommonDialogWithoutTitle.DialogCallback {
                    override fun okCallback(dialog: DialogInterface?) {
                        Observable.timer(300, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                UserManager.logout()
                            }

                    }

                    override fun cancelCallback(dialog: DialogInterface?) {
                    }

                })
        }

    }

    override fun createPresenter(): MineFragmentPresenter? {
        return MineFragmentPresenter(this)
    }

    override fun handleMessage(message: MVPMessage) {
        super.handleMessage(message)
        when (message.what) {
            1 -> {
                bannerList = message.obj as List<BannerModel>
                setBannerData()
            }
        }
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
            mlCover.loadImage("")
            tvName.text = "消消小助手"
        } else {
            mlCover.loadImage(user.iconImg)
            if (user.id == 100000L) {
                tvName.text = "点击绑定游戏账号"
            } else {
                tvName.text = "${user.nick}"
            }
        }
    }

    override fun useEventBus(): Boolean {
        return true
    }

    @Subscriber(tag = EventConstant.USER_LOGOUT)
    fun logout(msg: String) {
        if (rootView != null) {
            refreshView()
        }

    }

    @Subscriber(tag = EventConstant.LOGIN_SUCCESS)
    fun loginSuccess(msg: String) {
        if (rootView != null) {
            refreshView()
        }
    }

    @Subscriber(tag = EventConstant.REFRESH_USER_INFO)
    fun refreshUser(msg: String) {
        if (rootView != null) {
            Timber.e("refresh mine data")
            refreshView()
        }
    }
}
