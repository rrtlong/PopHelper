package com.moli.pophelper.module

import `in`.srain.cube.views.ptr.PtrClassicFrameLayout
import android.icu.text.StringPrepParseException
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.utils.LayoutManagerUtil
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.layout_refresh.*

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
    override val layoutResId: Int
        get() = R.layout.layout_refresh

    override fun initData(savedInstanceState: Bundle?) {
        QMUIStatusBarHelper.translucent(this)
        ivBack.clicksThrottle().subscribe { finish() }
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

}
