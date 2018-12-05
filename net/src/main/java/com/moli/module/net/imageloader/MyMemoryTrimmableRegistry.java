package com.moli.module.net.imageloader;

import com.blankj.utilcode.util.ObjectUtils;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;

import java.util.concurrent.CopyOnWriteArrayList;

public class MyMemoryTrimmableRegistry implements MemoryTrimmableRegistry {

    private CopyOnWriteArrayList<MemoryTrimmable> mList = new CopyOnWriteArrayList<>();


    @Override
    public void unregisterMemoryTrimmable(MemoryTrimmable trimmable) {
        mList.remove(trimmable);
    }


    @Override
    public void registerMemoryTrimmable(MemoryTrimmable trimmable) {
        mList.add(trimmable);
    }


    public void onTrimMemory(MemoryTrimType trimType) {
        if (ObjectUtils.isEmpty(mList)) {
            return;
        }
        for (MemoryTrimmable trimmable : mList) {
            trimmable.trim(trimType);
        }
    }
}
