package com.gbq.mylibrary.net;

import android.util.Log;

import com.gbq.mylibrary.MyApplication;
import com.gbq.mylibrary.net.api.ApiResponse;
import com.gbq.mylibrary.net.api.ApiService;
import com.gbq.mylibrary.bean.TestBean;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gbq.mylibrary.net.NetDefine.BASE_URL;
import static com.gbq.mylibrary.net.NetDefine.DEFAULT_TIMEOUT;

/**
 * 网络请求
 * Created by gbq on 2017-5-3.
 */

public class HttpRequest {
    private volatile static HttpRequest instance;
    private ApiService mApiService;
    private final CacheProvider mCacheProvider;

    public static HttpRequest getInstance() {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                if (instance == null) {
                    instance = new HttpRequest();
                }
            }
        }
        return instance;
    }

    private HttpRequest() {
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("HttpManager",message);
            }
        });
        loggingInterceptor.setLevel(level);
        //拦截请求和响应日志并输出，其实有很多封装好的日志拦截插件，大家也可以根据个人喜好选择。
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor);

        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();

        mCacheProvider = new RxCache.Builder()
                .persistence(MyApplication.getInstance().getFilesDir(), new GsonSpeaker())
                .using(CacheProvider.class);

        mApiService = retrofit.create(ApiService.class);
    }

    private <T> void toSubscribe(Observable<ApiResponse<T>> o, Observer<T> s) {
        o.subscribeOn(Schedulers.io())
                .map(new Function<ApiResponse<T>, T>() {
                    @Override
                    public T apply(@NonNull ApiResponse<T> response) throws Exception {
                        int code=response.getCode();
                        if (response.isSuccess()) {
                            throw new ApiException(code, response.getMsg());
                        } else {
                            return response.getDatas();
                        }
                    }
                })
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public void getDatasWithCache(Observer<TestBean> subscriber, int pno, int ps, String dtype, boolean update) {
        toSubscribe(mCacheProvider.getDatas(mApiService.getDatas(pno, ps,dtype),new EvictProvider(update)), subscriber);
    }
    public void getDatasNoCache(Observer<TestBean> subscriber, int pno, int ps, String dtype) {
        toSubscribe(mApiService.getDatas(pno, ps,dtype), subscriber);
    }
}
