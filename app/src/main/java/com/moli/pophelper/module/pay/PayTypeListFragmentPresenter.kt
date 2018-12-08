package com.moli.pophelper.module.pay

import android.support.v4.app.DialogFragment
import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.mvp.ListPresenter
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.model.base.OrderModel
import com.moli.module.model.base.PayActionModel
import com.moli.module.model.http.ResponseOrder
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.provider.APIService
import com.moli.module.widget.adapter.CommonRcvAdapter
import com.moli.module.widget.adapter.item.AdapterItem
import com.moli.module.widget.adapter.item.RcvAdapterItem
import com.moli.module.widget.adapter.itemDecoration.HorizontalDividerItemDecoration
import com.moli.pophelper.R

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/7 18:41
 * 修改人：lijilong
 * 修改时间：2018/12/7 18:41
 * 修改备注：
 * @version
 */
class PayTypeListFragmentPresenter(iListView: IListView, val order: ResponseOrder?) :
    ListPresenter<PayActionModel>(iListView) {
    @Autowired
    lateinit var api: APIService

    var channelId: Int? = null

    lateinit var adapter: CommonRcvAdapter<PayActionModel>
    override fun createAdapter(): RecyclerView.Adapter<RcvAdapterItem<PayActionModel>> {
        adapter = object : CommonRcvAdapter<PayActionModel>(dataList) {
            override fun createItem(type: Any): AdapterItem<PayActionModel> {
                return PayTypeItem(this@PayTypeListFragmentPresenter::itemClick)
            }

        }
        return adapter
    }

    fun itemClick(itemModel: PayActionModel) {
        if (order == null) return
        order.platformTyp = itemModel.channel
        channelId = itemModel.channel
        api.paySign(order!!).bindToLifecycle(owner)
            .subscribe(object : HttpSubscriber<String>() {
                override fun onNext(t: String) {
                    MVPMessage.obtain(rootView!!, itemModel.channel, t).handleMessageToTarget()
                }
            })
    }

    override fun requestData(pullToRefresh: Boolean) {
        val list = mutableListOf<PayActionModel>()
        val idArray = (rootView as DialogFragment).resources.obtainTypedArray(R.array.payIdsArray)
        val imageIdsArray = (rootView as DialogFragment).resources.obtainTypedArray(R.array.payActionImage)
        val namesArray = (rootView as DialogFragment).resources.getStringArray(R.array.payNamesArray)
        val channelArray = (rootView as DialogFragment).resources.getIntArray(R.array.payChannelArray)
        for (i in 0 until idArray.length()) {
            list.add(
                PayActionModel(
                    idArray.getResourceId(i, 0),
                    imageIdsArray.getResourceId(i, 0),
                    namesArray[i],
                    channelArray[i]
                )
            )
        }
        idArray.recycle()
        imageIdsArray.recycle()
        onDataSuccess(list)
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        val recyclerView = iListView.getRecyclerView()
        val context = recyclerView.context
        recyclerView.addItemDecoration(
            HorizontalDividerItemDecoration.Builder(context).sizeResId(R.dimen.dp_1).colorResId(
                R.color.color_ededed
            ).build()
        )
    }


}
