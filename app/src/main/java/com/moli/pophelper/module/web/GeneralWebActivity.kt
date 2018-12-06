package com.moli.pophelper.module.web

/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/8/27 16:39
 * 修改人：lijilong
 * 修改时间：2018/8/27 16:39
 * 修改备注：
 */

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.view.KeyEvent
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.BasePresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.constant.EventConstant
import com.moli.module.model.http.Plateform
import com.moli.module.model.http.ShareMessageModel
import com.moli.module.net.manager.UserManager
import com.moli.module.widget.widget.dialog.LoadingDialog
import com.moli.module.widget.widget.safetyToast
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.utils.UMShareUtil
import com.moli.pophelper.widget.LSWebView
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.activity_general_web.*
import org.simple.eventbus.Subscriber
import timber.log.Timber

@Route(path = HelperArouter.Activity.GeneralWebActivity.PATH)
class GeneralWebActivity : BaseMVPActivity<BasePresenter<IView>>(), IView, LSWebView.onReceiveInfoListener,
    LSWebView.OnOpenUrlListener {

    @Autowired
    @JvmField
    var url: String? = null

    @Autowired
    @JvmField
    var isUrlComplete: Boolean = false
    private val mLoadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    @Autowired
    @JvmField
    var title: String? = null

    @Autowired
    @JvmField
    var type: Int = 0

    private val mSingleFileCallback: ValueCallback<Uri>? = null
    private val mMultiFileCallback: ValueCallback<Array<Uri>>? = null

    internal var isSave = false

    override val layoutResId: Int
        get() = R.layout.activity_general_web


    override fun onPause() {
        super.onPause()
        try {
            hideLoadingLayout()
            mWebView!!.onPause()
        } catch (e: Exception) {
        }

    }

    override fun onResume() {
        super.onResume()
        try {
            mWebView!!.onResume()
        } catch (e: Exception) {
        }

    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {

        mWebView!!.settings.javaScriptEnabled = true
        //        mWebView.settings.blockNetworkImage = true // 把图片加载放在最后来加载渲染
        mWebView!!.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        // 支持多窗口
        mWebView!!.settings.setSupportMultipleWindows(true)
        // 开启 DOM storage API 功能
        mWebView!!.settings.domStorageEnabled = true
        // 开启 Application Caches 功能
        mWebView!!.settings.setAppCacheEnabled(true)
        mWebView.setBackgroundColor(0)
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
    }


    override fun onOpenUrl(url: String) {

    }

    override fun showLoadingLayout() {
        mLoadingDialog?.let {
            it.show()
        }
    }

    override fun hideLoadingLayout() {
        mLoadingDialog?.let {
            it.hide()
        }
    }

    override fun onReceivedError() {

    }

    override fun OnReceiveTitle(title: String) {
        if (this.title.isNullOrEmpty()) {
            topBar.setTitle(title)
        }
    }

    override fun onReceiveIcon(icon: Bitmap) {

    }

    override fun setShareInfo(title: String, desc: String, image: String) {

    }


    override fun onDestroy() {
        super.onDestroy()
        mLoadingDialog?.let {
            it.cancel()
        }
        try {
            mWebView!!.destroy()
        } catch (e: Exception) {
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
        }
        return false
    }

    override fun onBackPressed() {
        if (mWebView != null && mWebView!!.canGoBack()) {
            if (title == "关于我们") {
                super.onBackPressed()
            } else {
                mWebView!!.goBack()
            }
        } else {
            super.onBackPressed()
        }

    }

    //---------------------图库相关
    private fun setWebChromeClient() {
        mWebView!!.webChromeClient = object : WebChromeClient() {

            override fun onReceivedTitle(view: WebView, title: String) {
                this@GeneralWebActivity.OnReceiveTitle(title)
                Timber.d("view.url=${view.url}")
            }

            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: WebChromeClient.FileChooserParams
            ): Boolean {
                return true  // 一定要return true 防止下次回到 WebView页面重新调用，抛异常 duplicate result
            }

            //<3.0以下的文件上传监听方法
            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {}

            //For Android 3.0+
            fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String) {}

            // For Android  > 4.1.1
            fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String, capture: String) {
                isSave = false
            }
        }
    }


    /**
     * 防止点击dialog的取消按钮之后，就不再次响应点击事件了
     */
    fun cancelCallback() {
        mMultiFileCallback?.onReceiveValue(null)
        mSingleFileCallback?.onReceiveValue(null)
    }

    override fun initData(savedInstanceState: Bundle?) {
        topBar.addLeftBackImageButton().clicksThrottle().subscribe {
            onBackPressed()
        }
        if (!title.isNullOrEmpty()) {
            topBar.setTitle(title)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView!!.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        mWebView!!.setInterceptBack(true)
        mWebView!!.setOnReceiveInfoListener(this)
        mWebView!!.setOnOpenUrlListener(this)

        setWebChromeClient()
        //设置网页缓存
        initWebView()
        mLoadingDialog!!.setCanceledOnTouchOutside(false)

        if (!isUrlComplete) {
            mWebView!!.openUrl(url + "?" + "id" + "=" + UserManager.getSynSelf()!!.id)
        } else {
            mWebView!!.openUrl(url)

        }
        mWebView.loadUrl(url)
    }

    override fun createPresenter(): BasePresenter<IView>? {
        return null
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {

    }


    //分享
    private var platform: SHARE_MEDIA? = null
    private val shareUtil by lazy {
        object : UMShareUtil(this, object : UMShareListener {
            override fun onResult(platform: SHARE_MEDIA) {
                showMessage(R.string.share_success)
                hideLoadingLayout()
            }

            override fun onCancel(platform: SHARE_MEDIA) {
                showMessage(R.string.share_cancel)
                hideLoadingLayout()
            }

            override fun onError(platform: SHARE_MEDIA, throwable: Throwable?) {
                Timber.e(throwable)
                showMessage(R.string.share_failed)
                hideLoadingLayout()
            }

            override fun onStart(platform: SHARE_MEDIA) {

            }
        }) {}
    }

    /**
     * 点击事件
     */
    fun shareItemClick(item: ShareMessageModel) {
        // 分享平台 0，朋友圈,  1，微信   2,qq空间  3,qq  4,微博
        when (item.type) {
            //微信朋友圈
            Plateform.TYPE_WXCYCLE -> {
                if (!shareUtil.checkWXInstalled()) {
                    safetyToast(R.string.uninstallWx)
                    return
                }
                platform = SHARE_MEDIA.WEIXIN_CIRCLE
            }
            //微信好友
            Plateform.TYPE_WX -> {

                if (!shareUtil.checkWXInstalled()) {
                    safetyToast(R.string.uninstallWx)
                    return
                }
                platform = SHARE_MEDIA.WEIXIN

            }
            //QZone
            Plateform.TYPE_QZone -> {
                if (!shareUtil.checkQQInstalled()) {
                    safetyToast(R.string.uninstallQQ)
                    return
                }
                platform = SHARE_MEDIA.QZONE
            }
            //QQ
            Plateform.TYPE_QQ -> {
                if (!shareUtil.checkQQInstalled()) {
                    safetyToast(R.string.uninstallQQ)
                    return
                }
                platform = SHARE_MEDIA.QQ
            }

            //Sina
            Plateform.TYPE_SINA -> {
                if (!shareUtil.checkSinaInstalled()) {
                    safetyToast(R.string.uninstallWB)
                    return
                }
                platform = SHARE_MEDIA.SINA
            }
        }
        doShare(item)
    }

    private fun doShare(shareMessageModel: ShareMessageModel) {
        showLoadingLayout()
        val shareUrl = shareMessageModel.shareUrl
            ?: "http://a.app.qq.com/o/simple.jsp?pkgname=com.moli.jasmine"
        val title = shareMessageModel.shareTitle
            ?: "寻觅知音"
        val desc = shareMessageModel.shareTitle
            ?: "茉莉——听见陌生人的情感故事，寻觅知音"
        val shareImage = shareMessageModel.shareImg ?: ""
        if (shareMessageModel.dataType == 1) {
            shareUtil.shareWeb(platform, title, desc, shareImage, shareUrl)
        } else if (shareMessageModel.dataType == 2) {
            var imageBase64 =
                if (TextUtils.isEmpty(shareMessageModel.imageBase64)) "" else shareMessageModel.imageBase64
            if (!TextUtils.isEmpty(imageBase64)) {
                imageBase64 = imageBase64.substring(imageBase64.indexOf(","))
            }
            shareUtil.shareImage(platform, title, shareUrl, base64ToBitmap(imageBase64))
        }
    }


    @Subscriber(tag = EventConstant.SHARE_ACTION_H5)
    fun shareMessage(shareMessageModel: ShareMessageModel) {
        shareItemClick(shareMessageModel)
    }


    @Subscriber(tag = EventConstant.PUBLISH_VIDEO_ACTION_H5)
    fun publishVideo(param: String) {
        mWebView.let {
            it.post {
                val call = "javascript:APP_record('$param')"
                mWebView.loadUrl(call)
            }
        }
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    fun base64ToBitmap(base64Data: String): Bitmap {
        val bytes = Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}

