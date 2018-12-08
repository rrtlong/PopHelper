package com.moli.pophelper

import android.Manifest
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.ProgressModel
import com.moli.module.model.constant.EventConstant
import com.moli.module.net.manager.UserManager
import com.moli.module.widget.widget.dialog.CheckIsInstallAppDialog
import com.moli.module.widget.widget.dialog.DownloadProcessDialog
import com.moli.pophelper.constant.Constant
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.constant.Constant.POP_DOWNLOAD_URL
import com.moli.pophelper.module.home.*
import com.moli.pophelper.utils.FragmentNavigationUtils
import com.moli.pophelper.utils.PageSkipUtils
import com.moli.pophelper.utils.downloadPop
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import org.simple.eventbus.Subscriber
import timber.log.Timber

@Route(path = HelperArouter.Activity.MainActivity.PATH)
class MainActivity : BaseMVPActivity<MainActivityPresenter>(), IView {
    private var lastBackTime: Long = 0
    private val mFragmentManager by lazy { supportFragmentManager }
    lateinit var mHomeFragment: HomeFragment
    lateinit var mWealthFragment: WealthFragment
    lateinit var mActFragment: ActivityFragment
    lateinit var mMineFragment: MineFragment
    var mCurrentPosition = 0

    val checkInstallDialog by lazy {
        CheckIsInstallAppDialog(
            this,
            this@MainActivity::checkInstallClick
        )
    }
    val downloadDialog by lazy { DownloadProcessDialog(this) }


    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        QMUIStatusBarHelper.translucent(this)
        setFragment(mCurrentPosition)
        initClick()
//        checkInstallDialog.show()
//        downloadDialog.show()
//        downloadDialog.setPercent(100)
    }

    override fun createPresenter(): MainActivityPresenter? {
        return MainActivityPresenter(this)
    }

    fun setFragment(position: Int) {
        mCurrentPosition = position
        resetBottomView(position)
        val ft = mFragmentManager.beginTransaction()
        hideAllFragment(ft)
        when (position) {
            0 -> {
                if (!this::mHomeFragment.isInitialized) {
                    mHomeFragment = FragmentNavigationUtils.homeFragment() as HomeFragment
                    ft.add(R.id.homeContentLayout, mHomeFragment)
                } else {
                    ft.show(mHomeFragment)
                }
            }
            1 -> {
                if (!this::mWealthFragment.isInitialized) {
                    mWealthFragment = FragmentNavigationUtils.wealthFragment()
                    ft.add(R.id.homeContentLayout, mWealthFragment)
                } else {
                    ft.show(mWealthFragment)
                }
            }

            2 -> {
                if (!this::mActFragment.isInitialized) {
                    mActFragment = FragmentNavigationUtils.actFragment()
                    ft.add(R.id.homeContentLayout, mActFragment)
                } else {
                    ft.show(mActFragment)
                }
            }
            3 -> {
                if (!this::mMineFragment.isInitialized) {
                    mMineFragment = FragmentNavigationUtils.mineFragment()
                    ft.add(R.id.homeContentLayout, mMineFragment)
                } else {
                    ft.show(mMineFragment)
                }
            }
        }
        ft.commitAllowingStateLoss()
    }

    private fun hideAllFragment(ft: FragmentTransaction) {
        if (this::mHomeFragment.isInitialized) {
            ft.hide(mHomeFragment)
        }
        if (this::mWealthFragment.isInitialized) {
            ft.hide(mWealthFragment)
        }
        if (this::mActFragment.isInitialized) {
            ft.hide(mActFragment)
        }
        if (this::mMineFragment.isInitialized) {
            ft.hide(mMineFragment)
        }
    }


    private fun resetBottomView(position: Int) {
        ivHome.isSelected = position == 0
        ivWealth.isSelected = position == 1
        ivActivity.isSelected = position == 2
        ivMine.isSelected = position == 3
    }

    private fun initClick() {
        ivHome.clicksThrottle().subscribe { setFragment(0) }
        ivWealth.clicksThrottle().subscribe { setFragment(1) }
        ivActivity.clicksThrottle().subscribe { setFragment(2) }
        ivMine.clicksThrottle().subscribe {
            if (!UserManager.isLogin()) {
                PageSkipUtils.skipCodeLogin()
                return@subscribe
            }
            setFragment(3)
        }
    }


    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackTime > 2000) {
            showMessage(getString(R.string.app_exit))
            lastBackTime = currentTime
        } else {
            ActivityUtils.finishAllActivities()
        }

    }

    fun checkInstallClick(v: View) {
        when (v.id) {
            R.id.ivInstalled -> {

            }
            R.id.ivDownload -> {
                if (AppUtils.isAppInstalled(Constant.POP_PACKAGE)) {
                    AppUtils.launchApp(Constant.POP_PACKAGE)
                } else {
                    RxPermissions(this)
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
            }
        }
    }


    @Subscriber(tag = EventConstant.DOWNLOAD_PROGRESS)
    fun downloadAPK(model: ProgressModel) {
        if (ActivityUtils.getTopActivity() !is MainActivity) {
            return
        }
        if (!downloadDialog.isShowing) {
            downloadDialog.show()
        }
        downloadDialog.setPercent(model.progress)
        if (model.progress == 100) {
            downloadDialog.dismiss()
        }
    }

    @Subscriber(tag = EventConstant.USER_LOGOUT)
    fun logout(msg: String) {
        ivHome.performClick()
    }


}
