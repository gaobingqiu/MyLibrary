package com.gbq.mylibrary.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//开机自启动广播接受
public class AutoStartBroadcastReceiver extends BroadcastReceiver {
	private static final String ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(ACTION)) {

			// 后边的XXX.class就是要启动的服务
			Intent service = new Intent(context, AutoStartService.class);
			context.startService(service);
			Log.d("TAG2","test service");
			// 启动应用，参数为需要自动启动的应用的包名，只是启动app的activity的包名
//			Intent newIntent = context.getPackageManager().getLaunchIntentForPackage("com.example.demo");
//			context.startActivity(newIntent);
		}
	}

}
