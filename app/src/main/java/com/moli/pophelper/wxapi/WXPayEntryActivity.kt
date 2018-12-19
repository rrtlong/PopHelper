package com.moli.pophelper.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.moli.module.framework.mvp.IView
import com.moli.module.model.constant.EventConstant
import com.moli.pophelper.BuildConfig
import com.moli.pophelper.R
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import org.simple.eventbus.EventBus
import timber.log.Timber

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/10/24 16:21
 * 修改人：lijilong
 * 修改时间：2018/10/24 16:21
 * 修改备注：
 */

class WXPayEntryActivity : Activity(), IWXAPIEventHandler, IView {

    private var api: IWXAPI? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pay_result)

        api = WXAPIFactory.createWXAPI(this, BuildConfig.APPID_WX)
        api!!.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        api!!.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq) {}

    override fun onResp(resp: BaseResp) {
        Timber.e("WXPayEntryActivityonPayFinish, errCode = ${resp.errCode}")

        /**
         * 结果码参考：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5
         */
        if (resp.type == ConstantsAPI.COMMAND_PAY_BY_WX) {
            val code = resp.errCode
            when (code) {
                0 -> {
                    showMessage("支付成功")
                    EventBus.getDefault().post("", EventConstant.PAY_SUCCESS)
                }
                -1 -> {
                    // 支付失败 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
                    showMessage("支付失败")
                }
                -2 -> {
                    showMessage("支付取消")
                }
            }
            finish()
        }
    }

}
