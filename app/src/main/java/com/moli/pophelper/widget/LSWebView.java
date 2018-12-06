package com.moli.pophelper.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.*;
import com.blankj.utilcode.util.NetworkUtils;
import com.moli.module.net.http.api.APIJSInterface;
import com.moli.module.net.http.api.APIJSInterfaceKt;
import timber.log.Timber;

/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/8/27 15:50
 * 修改人：lijilong
 * 修改时间：2018/8/27 15:50
 * 修改备注：
 */
public class LSWebView extends WebView {
    private boolean interceptBack = true;
    private ValueCallback<Uri> mUploadMessage = null;
    private APIJSInterface jsInterface;

    @SuppressLint("SetJavaScriptEnabled")
    public LSWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            WebSettings settings = getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLoadWithOverviewMode(true); // 设置可以加载更多格式页面
            settings.setUseWideViewPort(true); // 使用广泛的视窗
            settings.setAppCacheEnabled(false); // 启用应用程序缓存api
            settings.setDomStorageEnabled(true); // 启用Dom storage api
            settings.setSupportZoom(false);
            settings.setTextZoom(100);
            settings.setJavaScriptCanOpenWindowsAutomatically(true); // 自动打开窗口
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS); // 排版适应屏幕
            if (NetworkUtils.isConnected()) {
                settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            } else {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            }
            String ua = getSettings().getUserAgentString();
            // settings.setUserAgentString(ua + ";jiajia/" + RT.AppInfo.Version);
            setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Timber.d("webView loading:" + url);
                    if (!TextUtils.isEmpty(url) && url.toLowerCase().endsWith(".apk")) {
                        try {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            getContext().startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                    loadUrl(url);
                    return true;
                }

                @Override
                public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                    super.onReceivedHttpAuthRequest(view, handler, host, realm);
                }


                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    Timber.d("webview ,url:" + url);
                    if (mOnOpenUrlListener != null) {
                        mOnOpenUrlListener.onOpenUrl(url);
                        mOnOpenUrlListener.showLoadingLayout();
                    }
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (mOnOpenUrlListener != null) {
                        mOnOpenUrlListener.onOpenUrl(url);
                        mOnOpenUrlListener.hideLoadingLayout();
                    }
                    //                if (url.contains("activity") && !TextUtils.isEmpty(APIJSInterface.tittleName)) {
                    //                    loadUrl("javascript:getActivity('" + APIJSInterface.tittleName + "')");
                    //                }
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    if (mOnOpenUrlListener != null) {
                        mOnOpenUrlListener.onReceivedError();
                    }
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }

            });

            setWebChromeClient(new WebChromeClient() {
                // For Android 3.0+
                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                    Timber.d("webview, openFileChooser");

                    mUploadMessage = uploadMsg;
                }

                // For Android < 3.0
                public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                    openFileChooser(uploadMsg, "");
                }

                // For Android > 4.1.1
                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                    openFileChooser(uploadMsg, acceptType);
                }

                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    Timber.d("webview, onReceivedTitle");
                    if (mOnReceiveListener != null) {
                        mOnReceiveListener.OnReceiveTitle(title);
                    }
                }

                @Override
                public void onReceivedIcon(WebView view, Bitmap icon) {
                    super.onReceivedIcon(view, icon);
                    Timber.d("webview, onReceivedIcon");
                    if (mOnReceiveListener != null) {
                        mOnReceiveListener.onReceiveIcon(icon);
                    }
                }

                @Override
                public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                    Timber.d("webview, onReceivedTouchIconUrl");
                    super.onReceivedTouchIconUrl(view, url, precomposed);
                }

                @SuppressLint("NewApi")
                @Override
                @Deprecated
                public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
                    super.onShowCustomView(view, requestedOrientation, callback);
                    Timber.d("WEBVIDEO, show:" + view.toString() + "orientation:" + requestedOrientation);
                }

                @Override
                public void onShowCustomView(View view, CustomViewCallback callback) {
                    showChild(LSWebView.this);
                    super.onShowCustomView(view, callback);
                    Timber.d("WEBVIDEO, show:" + view.toString());
                }

                @Override
                public void onHideCustomView() {
                    super.onHideCustomView();
                    Timber.d("WEBVIDEO, hide");
                }

            });

            setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (!interceptBack) {
                            return false;
                        }
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && canGoBack()) {
                            goBack();
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
        jsInterface = new APIJSInterface(this);
        this.addJavascriptInterface(jsInterface, APIJSInterfaceKt.JS_FUNCTION_NAME);
    }

    private void showChild(ViewGroup vg) {
        int count = vg.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = vg.getChildAt(i);
            if (v instanceof ViewGroup) {
                showChild(vg);
            } else {
                Timber.d("WEBVIDEO" + v.toString());
            }
        }
    }

    /**
     * 调用JS方法
     *
     * @param funcName 方法名
     */
    public void callJSFunction(String funcName) {
        loadUrl("javascript: " + funcName + "()");
    }


    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
        Timber.d("webView, url:" + url);
        if (mOnOpenUrlListener != null) {
            mOnOpenUrlListener.onOpenUrl(url);
        }
    }

    public void openUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            loadData("", "text/html", "UTF-8");
        } else {
            loadUrl(url);
        }

    }

    private OnOpenUrlListener mOnOpenUrlListener;

    public void setOnOpenUrlListener(OnOpenUrlListener mOnOpenUrlListener) {
        this.mOnOpenUrlListener = mOnOpenUrlListener;
    }

    public interface OnOpenUrlListener {
        void onOpenUrl(String url);

        void showLoadingLayout();

        void hideLoadingLayout();

        void onReceivedError();
    }

    /**
     * 设置是否拦截back事件
     *
     * @param mInterceptBack
     */
    public void setInterceptBack(boolean mInterceptBack) {
        this.interceptBack = mInterceptBack;
    }

    private onReceiveInfoListener mOnReceiveListener;

    public void setOnReceiveInfoListener(onReceiveInfoListener mOnReceiveListener) {
        this.mOnReceiveListener = mOnReceiveListener;
    }

    public interface onReceiveInfoListener {
        void OnReceiveTitle(String title);

        void onReceiveIcon(Bitmap icon);

        void setShareInfo(String title, String desc, String image);

    }

    public interface OnShareListener {
        void share(String title, String desc, String image, String url);
    }

    public void clear_view() {
        loadUrl("about:blank");
    }
}
