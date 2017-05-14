package com.gbq.mylibrary.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.gbq.mylibrary.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.util.Date;

public class PhoneUtil {

	public static String getMac() {
		String macSerial = null;
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (; null != str; ) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			// 赋予默认值
			ex.printStackTrace();
		}
		return macSerial;
	}

	public static String getTimeMillis(){
		return String.valueOf(System.currentTimeMillis());
	}

	public static String getCurrentTime(){
		DateFormat df = DateFormat.getTimeInstance(); //设置日期格式
		return df.format(new Date());
	}

	public static void savePhotoToSDCard(Bitmap photoBitmap, String path, String photoName) {
		File dir = new File(path);
		if (!dir.exists()) {
			if (!dir.mkdirs())
				return;
		}

		File photoFile = new File(path, photoName + ".png");
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(photoFile);
			if (photoBitmap != null) {
				if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
					fileOutputStream.flush();
					// fileOutputStream.close();
				}
			}
		} catch (IOException e) {
			if (photoFile.delete())
				Log.d("PhoneUtil", "delete failure");
			e.printStackTrace();
		} finally {
			try {
				assert fileOutputStream != null;
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置某个View的margin
	 * @param view   需要设置的view
	 * @param isDp   需要设置的数值是否为DP
	 * @param left   左边距
	 * @param right  右边距
	 * @param top    上边距
	 * @param bottom 下边距
	 */
	public static ViewGroup.LayoutParams setViewMargin(View view, boolean isDp, int left, int right, int top, int bottom) {
		if (view == null) {
			return null;
		}
		int leftPx = left;
		int rightPx = right;
		int topPx = top;
		int bottomPx = bottom;
		ViewGroup.LayoutParams params = view.getLayoutParams();
		ViewGroup.MarginLayoutParams marginParams = null;
		//获取view的margin设置参数
		if (params instanceof ViewGroup.MarginLayoutParams) {
			marginParams = (ViewGroup.MarginLayoutParams) params;
		} else {
			//不存在时创建一个新的参数
			marginParams = new ViewGroup.MarginLayoutParams(params);
		}

		//根据DP与PX转换计算值
		if (isDp) {
			leftPx = dip2px(left);
			rightPx = dip2px(right);
			topPx = dip2px(top);
			bottomPx = dip2px(bottom);
		}
		//设置margin
		marginParams.setMargins(leftPx, topPx, rightPx, bottomPx);
		view.setLayoutParams(marginParams);
		return marginParams;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	@SuppressWarnings("WeakerAccess")
	public static int dip2px(float dpValue) {
		final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	@SuppressWarnings("unused")
	public static int px2dip(float pxValue) {
		final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
