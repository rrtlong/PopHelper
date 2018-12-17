package com.moli.pophelper.module.mine

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.IPresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.net.manager.UserManager
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.utils.formatNumberWithCommaSplit
import kotlinx.android.synthetic.main.activity_get_cash.*

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/17 10:33
 * 修改人：lijilong
 * 修改时间：2018/12/17 10:33
 * 修改备注：
 * @version
 */
@Route(path = HelperArouter.Activity.GetCashActivity.PATH)
class GetCashActivity : BaseMVPActivity<IPresenter>(), IView {
    override val layoutResId: Int
        get() = R.layout.activity_get_cash

    override fun initData(savedInstanceState: Bundle?) {
        ivBack.clicksThrottle().subscribe { finish() }
        UserManager.getSynSelf()?.let {
            tvMoney.text = formatNumberWithCommaSplit(it.change!! / 100.0, 2)
        }

    }

    override fun createPresenter(): IPresenter? {
        return null
    }

}
