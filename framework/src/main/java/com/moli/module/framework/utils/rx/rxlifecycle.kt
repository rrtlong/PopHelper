package com.moli.module.framework.utils.rx

/**
 * 项目名称：Jasmine
 * 类描述：
 * 创建人：liujun
 * 创建时间：2018/1/11 22:44
 * 修改人：liujun
 * 修改时间：2018/1/11 22:44
 * 修改备注：
 * @version
 */
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event
import android.arch.lifecycle.LifecycleOwner
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import io.reactivex.*

fun <T> Observable<T>.bindToLifecycle(owner: LifecycleOwner?): Observable<T> {
    if (owner == null) return this
    return this.compose(AndroidLifecycle.createLifecycleProvider(owner).bindToLifecycle())
}

fun <T> Observable<T>.bindUntilEvent(owner: LifecycleOwner?, event: Event): Observable<T> {
    if (owner == null) return this
    return this.compose(AndroidLifecycle.createLifecycleProvider(owner).bindUntilEvent(event))
}

fun <T> Flowable<T>.bindToLifecycle(owner: LifecycleOwner?): Flowable<T> {
    if (owner == null) return this
    return this.compose(AndroidLifecycle.createLifecycleProvider(owner).bindToLifecycle())
}

fun <T> Flowable<T>.bindUntilEvent(owner: LifecycleOwner?, event: Event): Flowable<T> {
    if (owner == null) return this
    return this.compose(AndroidLifecycle.createLifecycleProvider(owner).bindUntilEvent(event))
}

fun <T> Single<T>.bindToLifecycle(owner: LifecycleOwner?): Single<T> {
    if (owner == null) return this
    return this.compose(AndroidLifecycle.createLifecycleProvider(owner).bindToLifecycle())
}

fun <T> Single<T>.bindUntilEvent(owner: LifecycleOwner?, event: Event): Single<T> {
    if (owner == null) return this
    return this.compose(AndroidLifecycle.createLifecycleProvider(owner).bindUntilEvent(event))
}

fun <T> Maybe<T>.bindToLifecycle(owner: LifecycleOwner?): Maybe<T> {
    if (owner == null) return this
    return this.compose(AndroidLifecycle.createLifecycleProvider(owner).bindToLifecycle())
}

fun <T> Maybe<T>.bindUntilEvent(owner: LifecycleOwner?, event: Event): Maybe<T> {
    if (owner == null) return this
    return this.compose(AndroidLifecycle.createLifecycleProvider(owner).bindUntilEvent(event))
}

fun Completable.bindToLifecycle(owner: LifecycleOwner?): Completable {
    if (owner == null) return this
    return this.compose(
            AndroidLifecycle.createLifecycleProvider(owner).bindToLifecycle<Completable>())
}

fun Completable.bindUntilEvent(owner: LifecycleOwner?, event: Lifecycle.Event): Completable {
    if (owner == null) return this
    return this.compose(
            AndroidLifecycle.createLifecycleProvider(owner).bindUntilEvent<Completable>(event))
}
