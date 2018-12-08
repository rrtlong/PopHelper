package com.moli.pophelper.module.pay

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alipay.sdk.app.PayTask
import com.moli.module.framework.base.BaseMVPDialogFragment
import com.moli.module.framework.mvp.IListView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.LayoutManagerUtil
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.http.ResponseOrder
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.fragment_pay_list_layout.*
import org.jetbrains.anko.support.v4.ctx
import org.json.JSONObject
import timber.log.Timber
import java.lang.ref.WeakReference

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/10/10 上午11:51
 * 修改人：yuliyan
 * 修改时间：2018/10/10 上午11:51
 * 修改备注：
 * @version
 */
@Route(path = HelperArouter.Fragment.PayTypeList.PATH)
class PayTypeListFragment : BaseMVPDialogFragment<PayTypeListFragmentPresenter>(), IListView {
    companion object {
        const val SDK_PAY_FLAG = 1
        const val SDK_AUTH_FLAG = 2
    }

    private lateinit var mHandler: MyHandler

    @JvmField
    @Autowired
    var payData: ResponseOrder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MLBottomDialogLight)
        mHandler = MyHandler(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.fragment_pay_list_layout, container, false)
    }

    override fun initData() {
        dialog?.window?.let {
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            it.setGravity(Gravity.BOTTOM)
        }
        dialog?.setCanceledOnTouchOutside(true)
        ivClose.clicksThrottle().subscribe {
            dismiss()
        }
    }

    override fun createPresenter(): PayTypeListFragmentPresenter? {
        return PayTypeListFragmentPresenter(this, payData)
    }


    override fun handleMessage(message: MVPMessage) {
        super.handleMessage(message)
        when (message.what) {
            1 -> {
                val jsonStr = message.obj as String
                Timber.e("wx str=$jsonStr")
                payWx(jsonStr)
                dismiss()
            }
            0 -> {
                val jsonStr = message.obj as String
                Timber.e("zfb jsonstr=$jsonStr")
                payZfb(jsonStr)
            }
        }
    }


    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LayoutManagerUtil.getLinearLayoutManager(ctx)
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView
    }


    private fun showMsg(title: String?, msg1: String?, msg2: String?) {
        var str = title
        if (null != msg1 && msg1.isNotEmpty()) {
            str += "\n" + msg1
        }
        if (null != msg2 && msg2.isNotEmpty()) {
            str += "\n" + msg2
        }
        val builder = AlertDialog.Builder(ctx)
        builder.setMessage(str)
        builder.setTitle("提示")
        builder.setPositiveButton("OK", null)
        builder.create().show()
    }


    /**
     * 支付宝支付业务
     *
     * @param
     */
    fun payZfb(orderInfo: String) {
        val payRunnable = Runnable {
            val alipay = PayTask(this@PayTypeListFragment.activity)
            val result = alipay.payV2(orderInfo, true)
            Log.i("msp", result.toString())

            val msg = Message()
            msg.what = Companion.SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }

        val payThread = Thread(payRunnable)
        payThread.start()
    }

    fun payWx(jsonStr: String) {
        var json = JSONObject(jsonStr)
        if (json != null) {
            var req = PayReq()
            req.appId = json.getString("appid")
            req.partnerId = json.getString("partnerid")
            req.prepayId = json.getString("prepayid")
            req.nonceStr = json.getString("noncestr")
            req.timeStamp = json.getString("timestamp")
            req.packageValue = json.getString("package")
            req.sign = json.getString("sign")
            var api = WXAPIFactory.createWXAPI(this@PayTypeListFragment.context, req.appId)
            api.sendReq(req)
        }
    }

    internal class MyHandler(fragment: PayTypeListFragment) : Handler() {
        // WeakReference to the outer class's instance.
        var mOuter: WeakReference<PayTypeListFragment> = WeakReference<PayTypeListFragment>(fragment)

        override fun handleMessage(msg: Message): Unit {
            val outer = mOuter.get()
            if (outer != null) {
                // Do something with outer as your wish.
                when (msg.what) {
                    SDK_PAY_FLAG -> {
                        val payResult = PayResult(msg.obj as Map<String, String>)
                        /**
                         * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        val resultInfo = payResult.result // 同步返回需要验证的信息
                        val resultStatus = payResult.resultStatus
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            outer.showMessage("支付成功")
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            outer.showMessage("支付失败")
                        }
                        outer.dismiss()
                    }
                    SDK_AUTH_FLAG -> {
                        val authResult = AuthResult(msg.obj as Map<String, String>, true)
                        val resultStatus = authResult.resultStatus

                        // 判断resultStatus 为“9000”且result_code
                        // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                        if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.resultCode, "200")) {
                            // 获取alipay_open_id，调支付时作为参数extern_token 的value
                            // 传入，则支付账户为该授权账户
                            outer.showMessage("授权成功")
                        } else {
                            // 其他状态值则为授权失败
                            outer.showMessage("授权失败")

                        }
                    }
                    else -> {
                    }
                }

            }
        }
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }


}
