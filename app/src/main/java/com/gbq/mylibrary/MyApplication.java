package com.gbq.mylibrary;

import android.app.Application;

/**
 *  参考
 *  https://github.com/smuyyh/SprintNBA.git
 */
public class MyApplication extends Application{
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApplication getInstance(){
        return mInstance;
    }
}
