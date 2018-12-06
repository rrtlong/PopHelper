package com.moli.pophelper.module.home

import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.mvp.ListPresenter
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.model.base.MusicModel
import com.moli.module.model.http.ResponseMusicList
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.provider.APIService
import com.moli.module.widget.adapter.CommonRcvAdapter
import com.moli.module.widget.adapter.item.AdapterItem
import com.moli.module.widget.adapter.item.RcvAdapterItem
import com.moli.module.widget.adapter.itemDecoration.HorizontalDividerItemDecoration
import com.moli.module.widget.adapter.itemDecoration.VerticalDividerItemDecoration
import com.moli.pophelper.R
import com.moli.pophelper.item.AppRecommendItem

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
class ActivityFragmentPresenter(iListView: IListView) : ListPresenter<MusicModel>(iListView) {
    @Autowired
    lateinit var api: APIService

    lateinit var adapter: CommonRcvAdapter<MusicModel>

    override fun createAdapter(): RecyclerView.Adapter<RcvAdapterItem<MusicModel>> {
        forbidLoadMore = true
        adapter = object : CommonRcvAdapter<MusicModel>(dataList) {
            override fun createItem(type: Any): AdapterItem<MusicModel> {
                return AppRecommendItem()
            }

        }
        return adapter
    }

    override fun requestData(pullToRefresh: Boolean) {
        api.recommandOrHotMusics(ResponseMusicList(currentPage, pageLimit, 1)).bindToLifecycle(owner)
            .subscribe(object : HttpSubscriber<List<MusicModel>>() {
                override fun onNext(t: List<MusicModel>) {
                    onDataSuccess(t)
                }

            })
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        val recyclerView = iListView.getRecyclerView()
        val context = recyclerView.context
        recyclerView.addItemDecoration(
            HorizontalDividerItemDecoration.Builder(context).marginResId(R.dimen.dp_16, R.dimen.dp_16)
                .colorResId(R.color.black_10).sizeResId(R.dimen.dp_1).build()
        )
    }
}
