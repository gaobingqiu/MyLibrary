package com.gbq.mylibrary.net.api;

public interface HttpCallBack<T> {

    void onSuccess(T t);

    void onError(int code, String errorMsg);
}
