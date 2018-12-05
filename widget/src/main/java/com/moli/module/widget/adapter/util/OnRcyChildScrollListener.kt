package com.moli.module.widget.adapter.util

import android.support.v7.widget.RecyclerView

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/9/19 下午3:08
 * 修改人：yuliyan
 * 修改时间：2018/9/19 下午3:08
 * 修改备注：
 * @version
 */
open class OnRcyChildScrollListener(offset: Int) : OnRcvScrollListener(offset) {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        var firstVisibleItemPosition = 0
        val layoutManager = recyclerView!!.layoutManager
        // 判断layout manager的类型
        val type = judgeLayoutManager(layoutManager)
        // 根据类型来计算出第一个可见的item的位置，由此判断是否触发到底部的监听器
        firstVisibleItemPosition = calculateFirstVisibleItemPos(type, layoutManager, firstVisibleItemPosition)
        // 计算并判断当前是向上滑动还是向下滑动
        val orientation = getOrientation(type, layoutManager)
        if (orientation == RecyclerView.VERTICAL) {
            calculateScrollUpOrDown(firstVisibleItemPosition, dy)
        } else if (orientation == RecyclerView.HORIZONTAL) {
            calculateScrollUpOrDown(firstVisibleItemPosition, dx)
        }
        // 移动距离超过一定的范围，我们监听就没有啥实际的意义了
        mScrolledXDistance += dx
        mScrolledYDistance += dy
        mScrolledXDistance = if (mScrolledXDistance < 0) 0 else mScrolledXDistance
        mScrolledYDistance = if (mScrolledYDistance < 0) 0 else mScrolledYDistance
        onScrolledPosition(firstVisibleItemPosition, mLastVisibleItemPosition,mScrolledXDistance,mScrolledYDistance)
        onScrolled(mScrolledXDistance, mScrolledYDistance)
    }



    open fun onScrolledPosition(firstVisiblePosition: Int, lastVisiblePosition: Int,  distanceX:Int,   distanceY: Int) {}

}
