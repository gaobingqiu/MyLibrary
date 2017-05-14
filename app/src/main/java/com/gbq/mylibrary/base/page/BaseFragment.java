package com.gbq.mylibrary.base.page;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gbq.mylibrary.util.LogUtil;

import java.lang.reflect.Field;

public abstract class BaseFragment extends Fragment {
    protected LayoutInflater mInflater;
    /**
     * 依附的activity
     */
    protected SuperActivity mActivity;
    /**
     * 根view
     */
    protected View mRootView;

    private ViewGroup mViewGroup;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        this.mViewGroup = container;
        onCreateView(savedInstanceState);
        if (mRootView == null)
            return super.onCreateView(inflater, container, savedInstanceState);
        LogUtil.e(this.getClass().getName() + "--->onCreateView");
        initData(getArguments());
        return mRootView;
    }

    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SuperActivity) getActivity();
    }

    /**
     * 初始化数据
     *
     * @param arguments 接收到的从其他地方传递过来的参数
     */
    protected void initData(Bundle arguments) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(this.getClass().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("--->onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e("--->onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
        mViewGroup = null;
        mInflater = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("--->onDestroy");
    }

    protected void showToast(int rId) {
        if (isActivityFinish()) {
            return;
        }
        mActivity.showToast(rId);
    }

    protected void showToast(String message) {
        if (isActivityFinish()) {
            return;
        }
        mActivity.showToast(message);
    }

    public void showProcess(int rId) {
        this.showProcess(getString(rId));
    }

    public void showProcess(String message) {
        this.showProcess(message, null);
    }

    public void showProcess(String message, final ProcessListener listener) {
        if (isActivityFinish()) {
            return;
        }
        mActivity.showProcess(message, listener);
    }

    public void closeProcess() {
        if (isActivityFinish()) {
            return;
        }
        mActivity.closeProcess();
    }

    protected boolean isActivityFinish() {
        return mActivity == null || mActivity.isFinishing();
    }

    public void setContentView(View view) {
        mRootView = view;
    }

    /**
     * 设置根布局资源id
     */
    protected void setContentView(int layoutResID) {
        setContentView(mInflater.inflate(layoutResID, mViewGroup, false));
    }


    protected View getContentView() {
        return mRootView;
    }

    protected View findViewById(int id) {
        if (mRootView != null)
            return mRootView.findViewById(id);
        return null;
    }
}
