package com.moli.pophelper.module

import `in`.srain.cube.views.ptr.PtrClassicFrameLayout
import android.Manifest
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.utils.LayoutManagerUtil
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.ProgressModel
import com.moli.module.model.constant.EventConstant
import com.moli.module.widget.widget.FooterView
import com.moli.module.widget.widget.dialog.DownloadProcessDialog
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.constant.Constant
import com.moli.pophelper.utils.downloadPop
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.layout_refresh.*
import org.simple.eventbus.Subscriber

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/6 02:23
 * 修改人：lijilong
 * 修改时间：2018/12/6 02:23
 * 修改备注：
 * @version
 */
@Route(path = HelperArouter.Activity.StrategyListActivity.PATH)
class StrategyListActivity : BaseMVPActivity<StrategyListActivityPresenter>(), IListView {
    val downloadDialog by lazy { DownloadProcessDialog(this) }
    override val layoutResId: Int
        get() = R.layout.layout_refresh

    override fun initData(savedInstanceState: Bundle?) {
        QMUIStatusBarHelper.translucent(this)
        ivBack.clicksThrottle().subscribe { finish() }
        ivLaunchGame.clicksThrottle().subscribe {
            if (AppUtils.isAppInstalled(Constant.POP_PACKAGE)) {
                AppUtils.launchApp(Constant.POP_PACKAGE)
                return@subscribe
            }
            RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) {
                        downloadPop(Constant.POP_DOWNLOAD_URL)
                    }
                }
        }
    }

    override fun createPresenter(): StrategyListActivityPresenter? {
        return StrategyListActivityPresenter(this)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LayoutManagerUtil.getLinearLayoutManager(this, true, false)
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView
    }

    override fun getRefreshLayout(): PtrClassicFrameLayout? {
        return ptrFrame
    }

    override fun getFooterView(): View? {
        return FooterView(this)
    }

    @Subscriber(tag = EventConstant.DOWNLOAD_PROGRESS)
    fun downloadAPK(model: ProgressModel) {
        if (ActivityUtils.getTopActivity() !is StrategyListActivity) {
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
}
