package com.moli.pophelper.module.home

import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.mvp.ListPresenter
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.model.base.GoodsModel
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.provider.APIService
import com.moli.module.widget.adapter.CommonRcvAdapter
import com.moli.module.widget.adapter.item.AdapterItem
import com.moli.module.widget.adapter.item.RcvAdapterItem
import com.moli.module.widget.adapter.itemDecoration.SpacingDecoration
import com.moli.pophelper.item.WealthItem
import org.jetbrains.anko.dip
import timber.log.Timber

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 18:45
 * 修改人：lijilong
 * 修改时间：2018/12/4 18:45
 * 修改备注：
 * @version
 */
class WealthFragmentPresenter(iListView: IListView) : ListPresenter<GoodsModel>(iListView) {
    @Autowired
    lateinit var api: APIService

    lateinit var adapter: CommonRcvAdapter<GoodsModel>

    override fun createAdapter(): RecyclerView.Adapter<RcvAdapterItem<GoodsModel>> {
        forbidLoadMore = true
        pageLimit = 12
        adapter = object : CommonRcvAdapter<GoodsModel>(dataList) {
            override fun createItem(type: Any): AdapterItem<GoodsModel> {
                return WealthItem(this@WealthFragmentPresenter::onclick)
            }

        }
        return adapter
    }

    fun onclick(model: GoodsModel) {
        Timber.e("pay click good 0")
        MVPMessage.obtain(rootView!!, 1, model).handleMessageToTarget()
    }

    override fun requestData(pullToRefresh: Boolean) {
        api.getGoodsList().bindToLifecycle(owner)
            .subscribe(object : HttpSubscriber<List<GoodsModel>>() {
                override fun onNext(t: List<GoodsModel>) {
                    onDataSuccess(t.filter { it.hotFlag != 1 })
                }

            })
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        val recyclerView = iListView.getRecyclerView()
        val context = recyclerView.context
        recyclerView.addItemDecoration(SpacingDecoration(context.dip(8), context.dip(8), false))
    }

    fun loadMore() {
        if (hasMore) {
            loadData(false)
        }
    }
}
