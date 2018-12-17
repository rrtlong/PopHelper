package com.moli.pophelper.module.wealth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.base.BaseMVPDialogFragment
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import kotlinx.android.synthetic.main.fragment_exchange_diamond.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.support.v4.ctx

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/17 15:51
 * 修改人：lijilong
 * 修改时间：2018/12/17 15:51
 * 修改备注：
 * @version
 */
@Route(path = HelperArouter.Fragment.ExchangeDiamondFragment.PATH)
class ExchangeDiamondFragment : BaseMVPDialogFragment<ExchangeDiamondFragmentPresenter>(), IView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MLBottomDialogDark)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return inflater.inflate(R.layout.fragment_exchange_diamond, container, false)
    }

    override fun initData() {
        dialog?.window?.let {
            it.setLayout(ctx.dip(300), ctx.dip(370))
            it.setGravity(Gravity.CENTER)
            it.setWindowAnimations(R.style.windowScale300)
        }
        dialog?.setCanceledOnTouchOutside(true)
        maxExchangeBtn.clicksThrottle().subscribe {

        }
        etDiamond.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        tvConfirm.clicksThrottle().subscribe {

        }
        tvCancel.clicksThrottle().subscribe {
            dismiss()
        }
    }

    override fun createPresenter(): ExchangeDiamondFragmentPresenter? {
        return ExchangeDiamondFragmentPresenter(this)
    }

}
