package com.moli.module.widget.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：16/9/19 21:01
 * 修改人：LiuJun
 * 修改时间：16/9/19 21:01
 * 修改备注：
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;
    private boolean hasHeader;


    /**
     * @param spanCount 跟布局里面的spanCount属性是一致的
     * @param spacing 每一个矩形的间距
     * @param includeEdge 如果设置成false那边缘地带就没有间距
     */
    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }


    /**
     * @param spanCount 跟布局里面的spanCount属性是一致的
     * @param spacing 每一个矩形的间距
     * @param includeEdge 如果设置成false那边缘地带就没有间距
     */
    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge, boolean hasHeader) {
        this(spanCount, spacing, includeEdge);
        this.hasHeader = hasHeader;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        if (hasHeader && position == 0) {
            return;
        }
        if (hasHeader) {
            position = position - 1;
        }
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing /
                    spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing /
                    spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing /
                    spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing /
                    spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}
