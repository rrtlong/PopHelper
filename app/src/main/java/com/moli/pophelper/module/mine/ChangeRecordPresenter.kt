package com.moli.pophelper.module.mine

import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.mvp.ListPresenter
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.model.base.RecordModel
import com.moli.module.model.http.RecordRequest
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.provider.APIService
import com.moli.module.widget.adapter.CommonRcvAdapter
import com.moli.module.widget.adapter.item.AdapterItem
import com.moli.module.widget.adapter.item.RcvAdapterItem
import com.moli.pophelper.item.ChangeRecordItem

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/15 14:46
 * 修改人：lijilong
 * 修改时间：2018/12/15 14:46
 * 修改备注：
 * @version
 */
class ChangeRecordPresenter(iListView: IListView, val type: Int) : ListPresenter<RecordModel>(iListView) {
    @Autowired
    lateinit var api: APIService

    lateinit var adapter: CommonRcvAdapter<RecordModel>

    override fun createAdapter(): RecyclerView.Adapter<RcvAdapterItem<RecordModel>> {
        pageLimit = 12
        setMinHide(12)
        adapter = object : CommonRcvAdapter<RecordModel>(dataList) {
            override fun createItem(t: Any): AdapterItem<RecordModel> {
                return ChangeRecordItem(type)
            }

        }
        return adapter
    }

    override fun requestData(pullToRefresh: Boolean) {
        api.getRecordList("", RecordRequest(type, currentPage, pageLimit)).bindToLifecycle(owner)
            .subscribe(object : HttpSubscriber<List<RecordModel>>() {
                override fun onNext(t: List<RecordModel>) {
                    onDataSuccess(t)
                }

            })
    }

    override fun initRecyclerView() {
        super.initRecyclerView()

    }

}
