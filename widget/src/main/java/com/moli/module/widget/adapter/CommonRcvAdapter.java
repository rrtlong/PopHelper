package com.moli.module.widget.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.moli.module.widget.adapter.item.RcvAdapterItem;
import com.moli.module.widget.adapter.util.ItemTypeUtil;
import com.moli.module.widget.adapter.util.IAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T> extends RecyclerView.Adapter<RcvAdapterItem<T>>
    implements IAdapter<T> {

    private List<T> mDataList;

    private Object mType;

    private ItemTypeUtil mUtil;

    private int currentPos;

    public CommonRcvAdapter(@Nullable List<T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mDataList = data;
        mUtil = new ItemTypeUtil();
    }

    /**
     * 配合RecyclerView的pool来设置TypePool
     */
    public void setTypePool(HashMap<Object, Integer> typePool) {
        mUtil.setTypePool(typePool);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public void setData(@NonNull List<T> data) {
        mDataList = data;
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * instead by{@link #getItemType(Object)}
     * <p>
     * 通过数据得到obj的类型的type
     * 然后，通过{@link ItemTypeUtil}来转换位int类型的type
     */
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        this.currentPos = position;
        mType = getItemType(mDataList.get(position));
        return mUtil.getIntType(mType);
    }

    @NonNull
    @Override
    public Object getItemType(T t) {
        return -1; // default
    }

    @NonNull
    @Override
    public RcvAdapterItem<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RcvAdapterItem<>(parent.getContext(), parent, createItem(mType));
    }

    @Override
    public void onBindViewHolder(@NonNull RcvAdapterItem<T> holder, int position) {
        holder.getItem().handleData(mDataList.get(position), position);
    }

    @Override
    public int getCurrentPosition() {
        return currentPos;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RcvAdapterItem<T> holder) {
        super.onViewAttachedToWindow(holder);
        holder.getItem().onAttach();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RcvAdapterItem<T> holder) {
        super.onViewDetachedFromWindow(holder);
        holder.getItem().onDetach();
    }
}
