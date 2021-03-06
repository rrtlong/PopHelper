package com.moli.pophelper.module.login

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.SPUtils
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.RxCountDown
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.framework.utils.rx.observeOnMain
import com.moli.module.framework.utils.rx.toIoAndMain
import com.moli.module.model.base.BannerModel
import com.moli.module.model.constant.EventConstant
import com.moli.module.model.constant.SPConstant
import com.moli.module.net.imageloader.loadImage
import com.moli.module.net.manager.UserManager
import com.moli.module.widget.widget.dialog.CommonAlertDialog
import com.moli.pophelper.R
import com.moli.pophelper.utils.PageSkipUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*
import org.simple.eventbus.Subscriber
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 15:04
 * 修改人：lijilong
 * 修改时间：2018/12/4 15:04
 * 修改备注：
 * @version
 */
class SplashActivity : BaseMVPActivity<SplashActivityPresenter>(), IView {
    var advDispose: Disposable? = null
    var bannerModel: BannerModel? = null
    var isFinishRequest = false
    var isFinishPermission = false
    private var isForceUpdate: Boolean = false

    override val layoutResId: Int
        get() = R.layout.activity_splash

    override fun initData(savedInstanceState: Bundle?) {
        isContentToStatusBar = true
        presenter?.getVersion()
    }

    override fun createPresenter(): SplashActivityPresenter? {
        return SplashActivityPresenter(this)
    }

    override fun handleMessage(message: MVPMessage) {
        super.handleMessage(message)
        when (message.what) {
            1 -> {
                isFinishRequest = true
                bannerModel = message.obj as BannerModel
                mlAdv.loadImage(bannerModel!!.imgUrl)
                if (isFinishPermission) {
                    showAdv()
                }

            }
            2 -> {
                isFinishRequest = true
                if (isFinishPermission) {
                    skipMain(true)
                }
            }
            3 -> {
                //跟新请求失败
                isForceUpdate = false
                CommonAlertDialog.showConfirm(this, "没有连接到网络哦～", "我知道了", false, null) {
                    Observable.timer(400, TimeUnit.MILLISECONDS).toIoAndMain().subscribe {
                        finish()
                    }
                }
            }
        }
    }

    fun showAdv() {
        mlAdv.visibility = View.VISIBLE
        skipBtn.visibility = View.VISIBLE
        advDispose?.dispose()
        advDispose = RxCountDown.countdown(3).observeOnMain().subscribe {
            Timber.e("showadv t $it")
            skipBtn.text = "${it} s"
            if (it == 0) {
                skipMain(false)
            }
        }
        skipBtn.clicksThrottle().subscribe {
            advDispose?.dispose()
            skipMain(false)
        }
        mlAdv.clicksThrottle().subscribe {
            //            advDispose?.dispose()

        }

    }

    fun skipMain(isDelay: Boolean) {
        if (isDelay) {
            Observable.timer(1, TimeUnit.SECONDS).toIoAndMain().subscribe {
                PageSkipUtils.skipMain()
                finish()
            }
        } else {
            PageSkipUtils.skipMain()
            finish()
        }

    }

    @SuppressLint("CheckResult")
    fun requestBasicPermission() {
        RxPermissions(this).request(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
        ).subscribe {
            Timber.e("requestBasicPermission it=$it")
            if (it) {
                //申请的权限全部允许
            } else {
                //至少有一个权限被拒绝
            }
            readPhoneState()
            isFinishPermission = true
            if (isFinishRequest) {
                if (bannerModel == null) {
                    skipMain(true)
                } else {
                    showAdv()
                }
            }


        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.alpha_out)
    }


    fun readPhoneState() {
        try {
            var brand = DeviceUtils.getModel()
            SPUtils.getInstance().put(SPConstant.DEVICE_BRAND, brand)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getUserInfo() {
        if (UserManager.isLogin()) {
            var user = UserManager.getSynSelf()?.let {
                presenter?.getUserInfo()
            }
        }
    }

    @Subscriber(tag = EventConstant.SPLASH_FORCE_UPDATE)
    fun updateForceState(flag: Boolean) {
        if (isAfterVersion) {
            return
        }
        isAfterVersion = true
        isForceUpdate = flag
        if (isForceUpdate) {
            pbUpdate.visibility = View.VISIBLE
            tvUpdate.visibility = View.VISIBLE
        } else {
            afterVersion()

        }
    }

    @Subscriber(tag = EventConstant.APP_UPDATE_PROGRESS)
    fun setUpdateProgress(progress: Int) {
        if (isForceUpdate) {
            pbUpdate.progress = progress
        }
    }

    var isAfterVersion = false
    fun afterVersion() {
        getUserInfo()
        presenter?.getBanner()
        requestBasicPermission()
    }

}
