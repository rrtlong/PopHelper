package com.moli.pophelper.module.mine

import `in`.srain.cube.views.ptr.PtrClassicFrameLayout
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.utils.LayoutManagerUtil
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.widget.ListEmptyView
import com.moli.module.widget.widget.FooterView
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import kotlinx.android.synthetic.main.layout_refresh.*
import org.jetbrains.anko.ctx

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/15 14:45
 * 修改人：lijilong
 * 修改时间：2018/12/15 14:45
 * 修改备注：
 * @version
 */
@Route(path = HelperArouter.Activity.ChangeRecordActivity.PATH)
class ChangeRecordActivity : BaseMVPActivity<ChangeRecordPresenter>(), IListView {
    @Autowired
    @JvmField
    var type: Int = 0
    override val layoutResId: Int
        get() = R.layout.layout_refresh

    override fun initData(savedInstanceState: Bundle?) {
        ivBack.clicksThrottle().subscribe { finish() }
        tvTitle.text = if (type == 0) "零钱记录" else "钻石记录"
    }

    override fun createPresenter(): ChangeRecordPresenter? {
        return ChangeRecordPresenter(this, type)
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

    override fun getEmptyView(): View? {
        return ListEmptyView(ctx)
    }

    override fun getFooterView(): View? {
        return FooterView(this)
    }

}
