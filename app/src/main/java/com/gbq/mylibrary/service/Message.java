package com.gbq.mylibrary.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gbq.mylibrary.R;

public class Message extends Service {
    private final static String tag = "Service";
    Context context;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        context = getApplicationContext();
    }

    // 创建通知
    public void CreateInform() {
        // 定义一个PendingIntent，当用户点击通知时，跳转到某个Activity(也可以发送广播等)
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, Notification.class);                //启动另一个活动
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).
                setSmallIcon(R.mipmap.register_success).setTicker("巴拉巴拉~~").setWhen(System.currentTimeMillis())
                .setContentTitle("点击查看").setContentIntent(pi).setContentText("欢迎使用Nubia账户");
        Notification notification = builder.build();
        //启动通知
        manager.notify(1, notification);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(tag, "开始服务");
        CreateInform();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        CreateInform();
        return null;
    }

    /* 当从新尝试绑定时执行 */
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }
}
