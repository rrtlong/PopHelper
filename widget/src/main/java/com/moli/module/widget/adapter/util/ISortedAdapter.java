package com.moli.module.widget.adapter.util;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;

import com.moli.module.widget.adapter.item.AdapterItem;


/**
 * 项目名称：heixiu
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：2017/3/10 18:19
 * 修改人：LiuJun
 * 修改时间：2017/3/10 18:19
 * 修改备注：
 */
public interface ISortedAdapter<T> {

    /**
     * @param data 设置数据源
     */
    void setData(@NonNull SortedList<T> data);

    SortedList<T> getData();

    /**
     * @param t list中的一条数据
     * @return 强烈建议返回string, int, bool类似的基础对象做type，不要返回data中的某个对象
     */
    Object getItemType(T t);

    /**
     * 当缓存中无法得到所需item时才会调用
     *
     * @param type 通过{@link #getItemType(Object)}得到的type
     * @return 任意类型的 AdapterItem
     */
    @Keep
    @NonNull
    AdapterItem<T> createItem(Object type);


    /**
     * 通知adapter更新当前页面的所有数据
     */
    void notifyDataSetChanged();

    /**
     * 得到当前要渲染的最后一个item的position
     */
    int getCurrentPosition();
}
