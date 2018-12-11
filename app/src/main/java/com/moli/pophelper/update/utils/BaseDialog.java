package com.moli.pophelper.update.utils;import android.app.Dialog;import android.content.Context;import android.view.View;import android.view.ViewGroup;import android.view.Window;import android.view.WindowManager;import com.moli.pophelper.R;public class BaseDialog {    private Context context;    private View view;    private Dialog mDialog;    public BaseDialog(Context context, View view) {        this.context = context;        this.view = view;        initDialog(false);    }    public BaseDialog(Context context, View view, boolean isFullScreen) {        this.context = context;        this.view = view;        initDialog(isFullScreen);    }    private Dialog initDialog(boolean isFullScreen) {        if (isFullScreen) {            mDialog = new Dialog(context, R.style.MLBottomDialogLight);        } else {            mDialog = new Dialog(context, R.style.MLBottomDialogDark);        }        mDialog.setContentView(view);        mDialog.setCanceledOnTouchOutside(false);        mDialog.setCancelable(false);        if (isFullScreen) {            Window window = mDialog.getWindow();            if (window != null) {                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);            }        }        return mDialog;    }    public void setCanceledOnTouchOutside(boolean flag) {        mDialog.setCanceledOnTouchOutside(flag);    }    public void setCancelable(boolean flag) {        mDialog.setCancelable(flag);    }    public void setGravity(int gravity) {        Window window = mDialog.getWindow();        window.setGravity(gravity);    }    public void setSize(int width, int height) {        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();        params.width = width;        params.height = height;        mDialog.getWindow().setAttributes(params);    }    public void setFullWidth() {        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();        params.width = context.getResources().getDisplayMetrics().widthPixels;        mDialog.getWindow().setAttributes(params);    }    public void setWidth(int width) {        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();        params.width = width;        mDialog.getWindow().setAttributes(params);    }    public void setPostion(int x, int y) {        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();        params.x = x;        params.y = y;        mDialog.getWindow().setAttributes(params);    }    public void setPostion(int x, boolean isX) {        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();        if (isX) {            params.x = x;        } else {            params.y = x;        }        mDialog.getWindow().setAttributes(params);    }    public void setAlpha(float alpha) {        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();        params.alpha = alpha;        mDialog.getWindow().setAttributes(params);    }    public void show() {        if (mDialog!=null && !mDialog.isShowing()) {            mDialog.show();        }    }    public void dismiss() {        if (mDialog!=null && mDialog.isShowing()) {            mDialog.dismiss();        }    }}