package com.moli.pophelper.module.home

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.blankj.utilcode.util.Utils
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.mvp.ListPresenter
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.FileDirUtils
import com.moli.module.framework.utils.rx.bindToLifecycle
import com.moli.module.framework.utils.writeFileFromIS
import com.moli.module.model.base.BannerModel
import com.moli.module.model.base.MusicModel
import com.moli.module.model.http.ResponseMusicList
import com.moli.module.net.http.HttpSubscriber
import com.moli.module.net.http.api.APIDownload
import com.moli.module.net.http.provider.APIDownloadService
import com.moli.module.net.http.provider.APIService
import com.moli.module.router.RewardRouter
import com.moli.module.widget.adapter.CommonRcvAdapter
import com.moli.module.widget.adapter.item.AdapterItem
import com.moli.module.widget.adapter.item.RcvAdapterItem
import com.moli.module.widget.adapter.itemDecoration.VerticalDividerItemDecoration
import com.moli.pophelper.MainActivity
import com.moli.pophelper.R
import com.moli.pophelper.item.StrategyItem
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.File
import java.io.IOException

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
class HomeFragmentPresenter(iListView: IListView) : ListPresenter<MusicModel>(iListView) {
    @Autowired
    lateinit var api: APIService

    lateinit var adapter: CommonRcvAdapter<MusicModel>

    override fun createAdapter(): RecyclerView.Adapter<RcvAdapterItem<MusicModel>> {
//        forbidLoadMore = true
        adapter = object : CommonRcvAdapter<MusicModel>(dataList) {
            override fun createItem(type: Any): AdapterItem<MusicModel> {
                return StrategyItem()
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
        recyclerView.addItemDecoration(VerticalDividerItemDecoration.Builder(context).sizeResId(R.dimen.dp_1).colorResId(R.color.black_10).marginResId(R.dimen.dp_16,R.dimen.dp_16).build())
    }

    fun getRecommandGoods() {
        MVPMessage.obtain(rootView!!, mutableListOf(MusicModel(), 2, MusicModel()))
    }

    fun getBanner() {
        api.getBanner(2).bindToLifecycle(owner).subscribe(object : HttpSubscriber<List<BannerModel>>() {
            override fun onNext(t: List<BannerModel>) {
                MVPMessage.obtain(rootView!!, 1, t).handleMessageToTarget()
            }

        })
    }

}
