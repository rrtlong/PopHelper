package com.moli.pophelper

import android.Manifest
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.aletter.xin.app.update.AppUpdateUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.ProgressModel
import com.moli.module.model.constant.EventConstant
import com.moli.module.model.constant.SPConstant
import com.moli.module.net.manager.UserManager
import com.moli.module.widget.widget.dialog.CheckIsInstallAppDialog
import com.moli.module.widget.widget.dialog.DownloadProcessDialog
import com.moli.pophelper.constant.Constant
import com.moli.pophelper.constant.Constant.POP_DOWNLOAD_URL
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.module.home.*
import com.moli.pophelper.utils.FragmentNavigationUtils
import com.moli.pophelper.utils.PageSkipUtils
import com.moli.pophelper.utils.downloadPop
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import org.simple.eventbus.Subscriber
import timber.log.Timber

@Route(path = HelperArouter.Activity.MainActivity.PATH)
class MainActivity : BaseMVPActivity<MainActivityPresenter>(), IView {
    var isDebug = false
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
        isDebug = SPUtils.getInstance().getInt(SPConstant.IS_DEBUG, 0) == 1
        ivWealth.visibility = if (isDebug) View.GONE else View.VISIBLE
        removeCacheFragment()
        setFragment(mCurrentPosition)
        initClick()
        if (!checkFirstLaunch()) {
            AppUpdateUtil(this).compareVersionFromJson(false)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter?.getUserInfo()
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
                    ft.add(R.id.homeContentLayout, mHomeFragment, HelperArouter.Fragment.HomeFragment.PATH)
                } else {
                    ft.show(mHomeFragment)
                }
            }
            1 -> {
                if (!this::mWealthFragment.isInitialized) {
                    mWealthFragment = FragmentNavigationUtils.wealthFragment()
                    ft.add(R.id.homeContentLayout, mWealthFragment, HelperArouter.Fragment.WealthFragment.PATH)
                } else {
                    ft.show(mWealthFragment)
                }
            }

            2 -> {
                if (!this::mActFragment.isInitialized) {
                    mActFragment = FragmentNavigationUtils.actFragment()
                    ft.add(R.id.homeContentLayout, mActFragment, HelperArouter.Fragment.ActFragment.PATH)
                } else {
                    ft.show(mActFragment)
                }
            }
            3 -> {
                if (!this::mMineFragment.isInitialized) {
                    mMineFragment = FragmentNavigationUtils.mineFragment()
                    ft.add(R.id.homeContentLayout, mMineFragment, HelperArouter.Fragment.MineFragment.PATH)
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
        if (model.downUrl != Constant.POP_DOWNLOAD_URL) {
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

    @Subscriber(tag = EventConstant.PAY_SUCCESS)
    fun paySuccess(msg: String) {
        presenter?.getUserInfo()
    }


    fun removeCacheFragment() {
        var transaction = mFragmentManager.beginTransaction()
        mFragmentManager.findFragmentByTag(HelperArouter.Fragment.HomeFragment.PATH)?.let { transaction.remove(it) }
        mFragmentManager.findFragmentByTag(HelperArouter.Fragment.WealthFragment.PATH)?.let { transaction.remove(it) }
        mFragmentManager.findFragmentByTag(HelperArouter.Fragment.ActFragment.PATH)?.let { transaction.remove(it) }
        mFragmentManager.findFragmentByTag(HelperArouter.Fragment.MineFragment.PATH)?.let { transaction.remove(it) }
        transaction.commitAllowingStateLoss()

    }

    fun checkFirstLaunch(): Boolean {
        var isFirst = false
        if (SPUtils.getInstance().getInt(SPConstant.FIRST_LAUNCHER_APP, 0) == 0) {
            isFirst = true
            SPUtils.getInstance().put(SPConstant.FIRST_LAUNCHER_APP, 1)
            checkInstallDialog.show()
        }
        return isFirst
    }


}
