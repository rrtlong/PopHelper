package com.moli.pophelper.module.home

import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.mvp.ListPresenter
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.model.base.BannerModel
import com.moli.module.model.base.GoodsModel
import com.moli.module.model.base.StrategyModel
import com.moli.module.model.http.BannerRequest
import com.moli.module.model.http.ResponseListPage
import com.moli.module.model.http.ResponseMusicList
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.provider.APIService
import com.moli.module.widget.adapter.CommonRcvAdapter
import com.moli.module.widget.adapter.item.AdapterItem
import com.moli.module.widget.adapter.item.RcvAdapterItem
import com.moli.module.widget.adapter.itemDecoration.HorizontalDividerItemDecoration
import com.moli.module.widget.adapter.itemDecoration.VerticalDividerItemDecoration
import com.moli.pophelper.R
import com.moli.pophelper.item.StrategyItem

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 18:44
 * 修改人：lijilong
 * 修改时间：2018/12/4 18:44
 * 修改备注：
 * @version
 */
class HomeFragmentPresenter(iListView: IListView) : ListPresenter<StrategyModel>(iListView) {
    @Autowired
    lateinit var api: APIService


    lateinit var adapter: CommonRcvAdapter<StrategyModel>

    override fun createAdapter(): RecyclerView.Adapter<RcvAdapterItem<StrategyModel>> {
        adapter = object : CommonRcvAdapter<StrategyModel>(dataList) {
            override fun createItem(type: Any): AdapterItem<StrategyModel> {
                return StrategyItem()
            }

        }
        return adapter
    }

    override fun requestData(pullToRefresh: Boolean) {
        api.getRecommendList("",ResponseListPage(currentPage, pageLimit)).bindToLifecycle(owner)
            .subscribe(object : HttpSubscriber<List<StrategyModel>>() {
                override fun onNext(t: List<StrategyModel>) {
                    onDataSuccess(t)
                }

            })
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        val recyclerView = iListView.getRecyclerView()
        val context = recyclerView.context
        recyclerView.addItemDecoration(
            HorizontalDividerItemDecoration.Builder(context).sizeResId(R.dimen.dp_1).colorResId(
                R.color.black_10
            ).marginResId(R.dimen.dp_16, R.dimen.dp_16).build()
        )
    }

    fun getRecommandGoods() {
        api.getGoodsList("").bindToLifecycle(owner)
            .subscribe(object : HttpSubscriber<List<GoodsModel>>() {
                override fun onNext(t: List<GoodsModel>) {
                    MVPMessage.obtain(rootView!!, 2,t.filter { it.hotFlag ==1 }).handleMessageToTarget()
                }

            })

    }

    fun getBanner() {
        api.getBanner("",BannerRequest("2")).bindToLifecycle(owner)
            .subscribe(object : HttpSubscriber<List<BannerModel>>() {
                override fun onNext(t: List<BannerModel>) {
                    var temp = t.filter { it.bannerType != 1 }
                    if (temp != null && temp.isNotEmpty()) {
                        MVPMessage.obtain(rootView!!, 1, t).handleMessageToTarget()
                    }
                }

            })
    }

}
