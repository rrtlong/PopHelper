package com.moli.pophelper.module.home

import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.mvp.ListPresenter
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.model.base.BannerModel
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.provider.APIService
import com.moli.module.widget.adapter.CommonRcvAdapter
import com.moli.module.widget.adapter.item.AdapterItem
import com.moli.module.widget.adapter.item.RcvAdapterItem
import com.moli.module.widget.adapter.itemDecoration.HorizontalDividerItemDecoration
import com.moli.pophelper.R
import com.moli.pophelper.item.AppRecommendItem
import com.moli.module.model.base.AppModel
import com.moli.module.model.http.BannerRequest
import com.moli.pophelper.constant.Constant

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
class ActivityFragmentPresenter(iListView: IListView) : ListPresenter<AppModel>(iListView) {
    @Autowired
    lateinit var api: APIService

    lateinit var adapter: CommonRcvAdapter<AppModel>

    override fun createAdapter(): RecyclerView.Adapter<RcvAdapterItem<AppModel>> {
        forbidLoadMore = true
        adapter = object : CommonRcvAdapter<AppModel>(dataList) {
            override fun createItem(type: Any): AdapterItem<AppModel> {
                return AppRecommendItem()
            }

        }
        return adapter
    }

    override fun requestData(pullToRefresh: Boolean) {
//        api.getAppList(ResponseMusicList(currentPage, pageLimit, 1)).bindToLifecycle(owner)
//            .subscribe(object : HttpSubscriber<List<AppModel>>() {
//                override fun onNext(t: List<AppModel>) {
//                    onDataSuccess(t)
//                }
//
//            })
        if (!pullToRefresh) {
            return
        }
        var newData = mutableListOf<AppModel>(AppModel(downloadUrl = Constant.POP_DOWNLOAD_URL))
        onDataSuccess(newData)
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

    fun getBanner() {
        api.getBanner(BannerRequest("3")).bindToLifecycle(owner).subscribe(object : HttpSubscriber<List<BannerModel>>() {
            override fun onNext(t: List<BannerModel>) {
                var temp = t.filter { it.bannerType != 1 }
                if(temp != null && temp.isNotEmpty()){
                    MVPMessage.obtain(rootView!!, 1, t).handleMessageToTarget()
                }
            }

        })
    }

    fun sign() {
        api.sign().bindToLifecycle(owner).subscribe(object : HttpSubscriber<String>() {
            override fun onNext(t: String) {
                MVPMessage.obtain(rootView!!, 3).handleMessageToTarget()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                MVPMessage.obtain(rootView!!, 4).handleMessageToTarget()
            }
        })

    }
}
