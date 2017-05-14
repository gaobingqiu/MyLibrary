package com.gbq.mylibrary.base.page;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.gbq.mylibrary.util.LogUtil;

import static android.R.id.message;

/**
 * 生命周期
 * Created by gbq on 2017-5-3.
 */

public abstract class SuperActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        LogUtil.e("--->onCreate");
    }

    public abstract int getContentViewId();

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("--->onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("--->onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.e("--->onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("--->onpause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e("--->onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("--->onDestroy");
        mProgressDialog = null;
    }

    public void showToast(int rId) {
        if (isActivityFinish()) {
            return;
        }
        Toast.makeText(this, rId, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message) {
        if (isActivityFinish()) {
            return;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            if (TextUtils.isEmpty(message)) {
                mProgressDialog.setMessage("玩命加载中...");
            } else {
                mProgressDialog.setMessage(message);
            }
        }
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });
        mProgressDialog.show();
    }

    public void closeProcess() {
        if (isActivityFinish()) {
            return;
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected boolean isActivityFinish() {
        return this.isFinishing();
    }
}
