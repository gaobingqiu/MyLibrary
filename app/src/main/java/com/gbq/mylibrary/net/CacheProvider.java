package com.gbq.mylibrary.net;



import com.gbq.mylibrary.net.api.ApiResponse;
import com.gbq.mylibrary.bean.TestBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

public interface CacheProvider {

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<ApiResponse<TestBean>> getDatas(Observable<ApiResponse<TestBean>> oRepos, EvictProvider evictDynamicKey);

}