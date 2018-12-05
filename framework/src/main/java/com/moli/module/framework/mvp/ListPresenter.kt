package com.moli.module.framework.mvp

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrHandler
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.widget.RecyclerView
import android.view.View
import com.moli.module.framework.model.ListResponse
import com.moli.module.widget.adapter.RcvAdapterWrapper
import com.moli.module.widget.adapter.item.RcvAdapterItem
import com.moli.module.widget.adapter.util.OnRcvScrollListener

/**
 * 项目名称：Jasmine
 * 类描述：
 * 创建人：liujun
 * 创建时间：2018/1/10 17:46
 * 修改人：liujun
 * 修改时间：2018/1/10 17:46
 * 修改备注：
 * @version
 */
abstract class ListPresenter<T>(val iListView: IListView) : BasePresenter<IListView>(iListView) {

    protected lateinit var adapterWrapper: RcvAdapterWrapper

    protected var listResponse: ListResponse<T>? = null

    protected val dataList = mutableListOf<T>()

    protected var currentPage = 1

    protected var pageLimit = 20

    protected var isFirst = true

    protected var hasMore = true //是否能够加载下一页

    protected var isLoading = false //当前是否正在加载中

    protected var isClearList = false //是否清空列表

    open protected var autoLoad = true

    protected var emptyView: View? = null
        get() {
            if (field == null) {
                field = iListView.getEmptyView()
            }
            return field
        }

    protected var loadingView: View? = null
        get() {
            if (field == null) {
                field = iListView.getLoadingView()
            }
            return field
        }

    protected var errorView: View? = null
        get() {
            if (field == null) {
                field = iListView.getErrorView()
            }
            return field
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun hide() {
        val refreshLayout = iListView.getRefreshLayout()
        refreshLayout?.refreshComplete()
        isLoading = false
    }

    /**
     * 使用 2017 Google IO 发布的 Architecture Components 中的 Lifecycles 的新特性 (此特性已被加入 Support library)
     * 使 {@code Presenter} 可以与 {@link SupportActivity} 和 {@link Fragment} 的部分生命周期绑定
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onInitAdapter() {
        if (this::adapterWrapper.isInitialized) return
        initRecyclerView()
        if (autoLoad) {
            //            val refreshLayout = iListView.getRefreshLayout()
            //            if (refreshLayout != null) {
            //                refreshLayout.autoRefresh()
            //            } else {
            loadData(true)
            //            }
        }
    }

    protected open fun initRecyclerView() {
        val layoutManager = iListView.getLayoutManager()
        val recyclerView = iListView.getRecyclerView()
        recyclerView.layoutManager = layoutManager
        adapterWrapper = RcvAdapterWrapper(createAdapter(), layoutManager)
        adapterWrapper.headerView = iListView.getHeaderView()
        adapterWrapper.footerView = iListView.getFooterView()
        recyclerView.adapter = adapterWrapper
        val refreshLayout = iListView.getRefreshLayout()
        if (refreshLayout != null) {
            refreshLayout.setPtrHandler(object : PtrHandler {
                override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header)
                }

                override fun onRefreshBegin(frame: PtrFrameLayout) {
                    loadData(true)
                }
            })
            recyclerView.addOnScrollListener(object : OnRcvScrollListener(3) {
                override fun onBottom() {
                    if (hasMore) {
                        loadData(false)
                    }
                }
            })
        } else {
            recyclerView.addOnScrollListener(object : OnRcvScrollListener(3) {
                override fun onBottom() {
                    if (hasMore) {
                        loadData(false)
                    }
                }
            })
        }
    }

    @Synchronized
    open fun loadData(pullToRefresh: Boolean) {
        if (isLoading) {
            return
        }
        val no = if (pullToRefresh) {
            0
        } else {
            listResponse?.currentPageNo ?: if (dataList.size % pageLimit != 0) {
                dataList.size / pageLimit + 1
            } else {
                dataList.size / pageLimit
            }
        }
        currentPage = no + 1
        isLoading = true
        isClearList = pullToRefresh
        if (currentPage == 1) {
            showLoading()
        }
        requestData(pullToRefresh)
    }

    protected fun onDataSuccess(response: ListResponse<T>?) {
        listResponse = response
        onDataSuccess(response?.result)
    }

    /**
     * requestData方法请求数据成功后将数据集传入本方法进行刷新列表
     */
    open protected fun onDataSuccess(newData: List<T>?) {
        if (rootView == null) return
        isLoading = false
        if (!this::adapterWrapper.isInitialized) return
        hasMore = checkLoadMore(newData)
        val refreshLayout = iListView.getRefreshLayout()
        if (isClearList) {
            dataList.clear()
            adapterWrapper.notifyDataSetChanged()
            refreshLayout?.refreshComplete()
//            refreshLayout?.finishRefresh(newData != null)
//            refreshLayout?.setNoMoreData(!hasMore)
        } else {
//            refreshLayout?.finishLoadMore(newData != null)
//            refreshLayout?.setNoMoreData(!hasMore)
//            refreshLayout?.finishLoadMore()
            refreshLayout?.refreshComplete()
        }

        if (null != newData && newData.isNotEmpty()) {
            val positionStart = dataList.size
            dataList.addAll(newData)
            adapterWrapper.notifyItemRangeInserted(
                positionStart + adapterWrapper.headerCount, newData.size
            )
        }
        showEmpty()
    }


    protected open fun showEmpty() {
        if (!this::adapterWrapper.isInitialized) return
        if (adapterWrapper.emptyView != null && adapterWrapper.emptyView == emptyView) return
        adapterWrapper.setEmptyView(emptyView, iListView.getRecyclerView())
    }

    fun showError() {
        onDataSuccess(newData = null)
        if (!this::adapterWrapper.isInitialized) return
        if (adapterWrapper.emptyView != null && adapterWrapper.emptyView == errorView) return
        val view = errorView ?: emptyView
        adapterWrapper.setEmptyView(view, iListView.getRecyclerView(), RcvAdapterWrapper.TYPE_ERROR)
    }

    protected fun showLoading() {
        if (!this::adapterWrapper.isInitialized) return
        if (adapterWrapper.emptyView != null && adapterWrapper.emptyView == loadingView) return
        adapterWrapper.setEmptyView(loadingView, iListView.getRecyclerView(), RcvAdapterWrapper.TYPE_LOADING)
    }


    /**
     * 判断是否能够加载下一页
     *
     * @return true：有下一页  false：没有下一页
     */
    protected open fun checkLoadMore(newData: List<T>?): Boolean {
        if (listResponse != null) {
            return listResponse?.nextPage == true
        }
        //        return newData != null && newData.size >= pageLimit
        return newData != null && newData.size >= 0
    }

    override fun onDestroy() {
        super.onDestroy()
        dataList.clear()
        if (this::adapterWrapper.isInitialized)
            adapterWrapper.notifyDataSetChanged()
    }

    abstract fun createAdapter(): RecyclerView.Adapter<RcvAdapterItem<T>>

    /**
     * 请求列表数据
     * @param pullToRefresh 是否下拉刷新
     */
    protected abstract fun requestData(pullToRefresh: Boolean)

}
