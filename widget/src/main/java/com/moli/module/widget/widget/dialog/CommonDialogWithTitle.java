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
import com.moli.module.widget.widget.dialog.base.BaseDialog;
import com.moli.module.widget.widget.dialog.base.BounceEnter;
import com.moli.module.widget.widget.dialog.base.BounceTopEnter;
import com.moli.module.widget.widget.dialog.base.SlideBottomExit;
import com.moli.module.widget.widget.dialog.base.ZoomOutExit;

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
public class CommonDialogWithTitle extends BaseDialog<CommonDialogWithTitle> {

    TextView title;
    TextView content;
    TextView cancel;
    TextView confirm;
    View rootView;

    private Message mButtonPositiveMessage, mButtonNegativeMessage;
    private CharSequence mPositiveButtonText;
    private CharSequence mNegativeButtonText;
    private DialogInterface.OnClickListener mPositiveButtonOnClickListener, mNegativeButtonOnClickListener;
    private Handler mHandler;
    private CharSequence mTitle;
    private CharSequence mMessage;
    private Spanned spanMessage;
    private boolean mFromTop;
    private View view;

    public CommonDialogWithTitle(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0f);


        // dismissAnim(this, new ZoomOutExit());
        rootView = View.inflate(mContext, R.layout.dialog_common_with_title_layout, null);
        title = rootView.findViewById(R.id.title);
        content = rootView.findViewById(R.id.content);
        cancel = rootView.findViewById(R.id.cancel);
        confirm = rootView.findViewById(R.id.confirm);
        GradientDrawable bg = new GradientDrawable();
        bg.setCornerRadius(dp2px(5));
        bg.setColor(Color.parseColor("#ffffff"));
        rootView.setBackgroundDrawable(bg);
        return rootView;
    }

    @Override
    public void setUiBeforeShow() {
        setupTitle();
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

    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    public void setTitle(int resId) {
        mTitle = mContext.getString(resId);
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
            } else if (v == cancel && mButtonNegativeMessage != null) {
                m = Message.obtain(mButtonNegativeMessage);
            }
            if (m != null) {
                m.sendToTarget();
            }

            // Post a message so we dismiss after the above handlers are
            // executed
            mHandler.obtainMessage(ButtonHandler.MSG_DISMISS_DIALOG, CommonDialogWithTitle.this).sendToTarget();
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

        cancel.setOnClickListener(mButtonHandler);

        if (TextUtils.isEmpty(mNegativeButtonText)) {
            cancel.setVisibility(View.GONE);
        } else {
            cancel.setVisibility(View.VISIBLE);
            cancel.setText(mNegativeButtonText);
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

    private boolean setupTitle() {
        if (!TextUtils.isEmpty(mTitle)) {
            title.setText(mTitle);
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

    public static void showConfirm(Context context, String title, String message, String okButtonText, String cancelButtonText, final DialogCallback dialogCallback) {
        showConfirm(context, title, message, okButtonText, cancelButtonText, false, dialogCallback, null);
    }

    public static void showConfirm(Context context, String title, String message, String okButtonText, String cancelButtonText, boolean fromTop,
                                   final DialogCallback dialogCallback, final DialogInterface.OnDismissListener onDismissListener) {


        CommonDialogWithTitle dialog = new CommonDialogWithTitle(context);

        dialog.setTitle(title == null ? "提示" : title);
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
        dialog.setNegativeButton(cancelButtonText == null ? "取消" : cancelButtonText,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (dialogCallback != null) {
                        dialogCallback.cancelCallback(dialog);
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
