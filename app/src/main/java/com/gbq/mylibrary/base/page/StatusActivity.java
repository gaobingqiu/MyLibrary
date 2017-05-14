package com.gbq.mylibrary.base.page;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.gbq.mylibrary.R;

/**
 * 透明状态栏
 * Created by gbq on 2017-5-3.
 */

public abstract class StatusActivity extends SuperActivity {
    private View statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusColor(this, getResources().getColor(R.color.colorPrimary));//和toolbar一个颜色
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    public void setStatusColor(Activity activity, int color) {
        // 设置状态栏透明
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 生成一个状态栏大小的矩形

        // 添加 statusView 到布局中
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();

        if (statusView != null) {
            decorView.removeView(statusView);
        }
        statusView = createStatusView(activity, color);
        decorView.addView(statusView);
        // 设置根布局的参数
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }


    /**
     * 提供remove方法，在不需要状态栏时可以移除
     */
    public void removeView(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        if (statusView != null) {
            decorView.removeView(statusView);
        }
    }
}
