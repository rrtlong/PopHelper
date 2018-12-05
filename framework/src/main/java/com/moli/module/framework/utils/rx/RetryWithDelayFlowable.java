package com.moli.module.framework.utils.rx;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import timber.log.Timber;

/**
 * 项目名称：heixiu_2.3
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：2016/11/12 11:05
 * 修改人：LiuJun
 * 修改时间：2016/11/12 11:05
 * 修改备注：
 */
public class RetryWithDelayFlowable implements Function<Flowable<? extends Throwable>, Publisher<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;


    /**
     * @param maxRetries       最大重试次数
     * @param retryDelayMillis 重试间隔时间，毫秒
     */
    public RetryWithDelayFlowable(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }


    @Override
    public Flowable<?> apply(@NonNull Flowable<? extends Throwable> attempts) throws Exception {
        return attempts.flatMap(new Function<Throwable, Flowable<?>>() {
            @Override
            public Flowable<?> apply(@NonNull Throwable throwable) throws Exception {
                if (++retryCount <= maxRetries) {
                    // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                    Timber.e("get error, it will try after " + retryDelayMillis +
                        " millisecond, retry count " + retryCount);
                    return Flowable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                // Max retries hit. Just pass the error along.
                return Flowable.error(throwable);
            }
        });
    }
}
