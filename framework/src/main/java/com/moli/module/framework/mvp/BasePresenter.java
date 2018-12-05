package com.moli.module.framework.mvp;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.trello.rxlifecycle2.RxLifecycle;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 项目名称：heixiu
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：16/4/28 16:36
 * 修改人：LiuJun
 * 修改时间：16/4/28 16:36
 * 修改备注：
 */
public class BasePresenter<V extends IView> implements IPresenter, LifecycleObserver {

    private CompositeDisposable compositeDisposable;

    @Nullable
    protected V rootView;

    @Nullable
    protected LifecycleOwner owner;

    public BasePresenter(@Nullable V rootView) {
        this.rootView = rootView;
        ARouter.getInstance().inject(this);
        onStart();
    }

    public void onStart() {
        if (rootView != null && rootView instanceof LifecycleOwner) {
            owner = ((LifecycleOwner) rootView);
            owner.getLifecycle().addObserver(this);
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this);//注册eventbus
        }
    }

    @Override
    public void onDestroy() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);//解除注册eventbus
        }
        unDispose();//解除订阅
        rootView = null;
        owner = null;
        compositeDisposable = null;
    }

    /**
     * 是否使用eventBus,默认为不使用(false)，
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 只有当 {@code mRootView} 不为 null, 并且 {@code mRootView} 实现了 {@link LifecycleOwner} 时, 此方法才会被调用
     * 所以当您想在 {@link Service} 以及一些自定义 {@link View} 或自定义类中使用 {@code Presenter} 时
     * 您也将不能继续使用 {@link OnLifecycleEvent} 绑定生命周期
     *
     * @param owner link {@link SupportActivity} and {@link Fragment}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        /**
         * 注意, 如果在这里调用了 {@link #onDestroy()} 方法, 会出现某些地方引用 {@code mModel} 或 {@code mRootView} 为 null 的情况
         * 比如在 {@link RxLifecycle} 终止 {@link Observable} 时, 在 {@link io.reactivex.Observable#doFinally(Action)} 中却引用了 {@code mRootView} 做一些释放资源的操作, 此时会空指针
         * 或者如果你声明了多个 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) 时在其他 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
         * 中引用了 {@code mModel} 或 {@code mRootView} 也可能会出现此情况
         */
        owner.getLifecycle().removeObserver(this);
    }

    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 {@link Activity#onDestroy()} 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
     * 目前框架已使用 {@link RxLifecycle} 避免内存泄漏,此方法作为备用方案
     */
    public void addDispose(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);//将所有 Disposable 放入集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }
}
