package com.moli.module.widget.widget.dialog;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.moli.module.widget.R;
import com.moli.module.widget.widget.dialog.base.*;

import java.lang.ref.WeakReference;

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/9/22 14:13
 * 修改人：lijilong
 * 修改时间：2018/9/22 14:13
 * 修改备注：
 */
public class CommonAlertDialog extends BaseDialog<CommonAlertDialog> {

    TextView title;
    TextView content;
    TextView confirm;
    View rootView;

    private Message mButtonPositiveMessage, mButtonNegativeMessage;
    private CharSequence mPositiveButtonText;
    private CharSequence mNegativeButtonText;
    private DialogInterface.OnClickListener mPositiveButtonOnClickListener, mNegativeButtonOnClickListener;
    private Handler mHandler;
    private CharSequence mMessage;
    private Spanned spanMessage;
    private boolean mFromTop;
    private View view;

    public CommonAlertDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0f);


        // dismissAnim(this, new ZoomOutExit());
        rootView = View.inflate(mContext, R.layout.dialog_common_alert, null);
        content = rootView.findViewById(R.id.content);
        confirm = rootView.findViewById(R.id.confirm);
        GradientDrawable bg = new GradientDrawable();
        bg.setCornerRadius(dp2px(5));
        bg.setColor(Color.parseColor("#ffffff"));
        rootView.setBackgroundDrawable(bg);
        return rootView;
    }

    @Override
    public void setUiBeforeShow() {
        setupMessage();
        boolean hasButton = setupButton();

        if (!hasButton) {
            //            dialogCustomAlertLayoutButtons.setVisibility(View.GONE);
            if (view != null) {
                //                ((ViewGroup) view).removeView(dialogCustomAlertLayoutButtons);
            }
        }

        if (mFromTop) {
            showAnim(new BounceTopEnter());
        } else {
            showAnim(new BounceEnter());
        }

        mHandler = new ButtonHandler(this);
        setupButtonListener();
    }

    private void setupButtonListener() {
        setButton(BUTTON_POSITIVE, mPositiveButtonOnClickListener, mButtonPositiveMessage);
        setButton(BUTTON_NEGATIVE, mNegativeButtonOnClickListener, mButtonNegativeMessage);
    }

    public void setMessage(CharSequence msg) {
        mMessage = msg;
    }

    public void setMessage(Spanned msg) {
        spanMessage = msg;
    }

    public void setMessage(int resId) {
        mMessage = mContext.getString(resId);
    }

    public void setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
        mPositiveButtonText = text;
        mPositiveButtonOnClickListener = listener;

    }

    public void setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
        mNegativeButtonText = text;
        mNegativeButtonOnClickListener = listener;
    }

    public void setFromTop(boolean fromTop) {
        mFromTop = fromTop;
    }

    public void setButton(int whichButton, DialogInterface.OnClickListener listener, Message msg) {

        if (msg == null && listener != null) {
            msg = mHandler.obtainMessage(whichButton, listener);
        }

        switch (whichButton) {

            case DialogInterface.BUTTON_POSITIVE:
                // mPositiveButtonText = text;
                mButtonPositiveMessage = msg;
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                // mNegativeButtonText = text;
                mButtonNegativeMessage = msg;
                break;

            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    View.OnClickListener mButtonHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Message m = null;
            if (v == confirm && mButtonPositiveMessage != null) {
                m = Message.obtain(mButtonPositiveMessage);
            }
            if (m != null) {
                m.sendToTarget();
            }

            // Post a message so we dismiss after the above handlers are
            // executed
            mHandler.obtainMessage(ButtonHandler.MSG_DISMISS_DIALOG, CommonAlertDialog.this).sendToTarget();
        }
    };

    private boolean setupButton() {
        int BIT_BUTTON_POSITIVE = 1;
        int BIT_BUTTON_NEGATIVE = 2;
        int whichButtons = 0;
        confirm.setOnClickListener(mButtonHandler);

        if (TextUtils.isEmpty(mPositiveButtonText)) {
            confirm.setVisibility(View.GONE);
        } else {
            confirm.setVisibility(View.VISIBLE);
            confirm.setText(mPositiveButtonText);
            whichButtons = whichButtons | BIT_BUTTON_POSITIVE;
        }


        if (TextUtils.isEmpty(mNegativeButtonText)) {
        } else {
            whichButtons = whichButtons | BIT_BUTTON_NEGATIVE;
        }

        setButtonDividers(whichButtons);

        return whichButtons != 0;
    }

    private void setButtonDividers(int whichButtons) {
        if (whichButtons == (1 | 2)) {
            //            dialogCustomAlertLayoutVerticalLine.setVisibility(View.VISIBLE);
        } else {
            //            dialogCustomAlertLayoutVerticalLine.setVisibility(View.GONE);
        }
    }

    private boolean setupMessage() {
        if (!TextUtils.isEmpty(mMessage)) {
            content.setText(mMessage);
        } else if (spanMessage != null) {
            content.setText(mMessage);
        }
        return true;
    }

    private static final class ButtonHandler extends Handler {
        // Button clicks have Message.what as the BUTTON{1,2,3} constant
        private static final int MSG_DISMISS_DIALOG = 1;

        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialog) {
            mDialog = new WeakReference<>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case DialogInterface.BUTTON_POSITIVE:
                case DialogInterface.BUTTON_NEGATIVE:
                    ((DialogInterface.OnClickListener) msg.obj).onClick(mDialog.get(), msg.what);
                    break;

                case MSG_DISMISS_DIALOG:
                    ((DialogInterface) msg.obj).dismiss();
            }
        }
    }


    public interface DialogCallback {
        void okCallback(DialogInterface dialog);

        void cancelCallback(DialogInterface dialog);
    }

    public static void showConfirm(Context context, String message, String okButtonText, final DialogCallback dialogCallback) {
        showConfirm(context, message, okButtonText, false, dialogCallback, null);
    }

    public static void showConfirm(Context context, String message, String okButtonText, boolean fromTop,
                                   final DialogCallback dialogCallback, final DialogInterface.OnDismissListener onDismissListener) {


        CommonAlertDialog dialog = new CommonAlertDialog(context);

        dialog.setMessage(message == null ? "确认吗？" : message);
        dialog.setPositiveButton(okButtonText == null ? "确认" : okButtonText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (dialogCallback != null) {
                            dialogCallback.okCallback(dialog);
                        }
                    }
                });

        dialog.setFromTop(fromTop);
        if (fromTop) {
            dialog.dismissAnim(new SlideBottomExit()).show();
        } else {
            dialog.dismissAnim(new ZoomOutExit()).show();
        }
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (onDismissListener != null) {
                    onDismissListener.onDismiss(dialog);
                }
            }
        });
    }

}
