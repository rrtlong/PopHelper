package com.moli.pophelper.module.mine

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.IPresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.pophelper.BuildConfig
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import kotlinx.android.synthetic.main.activity_about_us.*

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/14 15:29
 * 修改人：lijilong
 * 修改时间：2018/12/14 15:29
 * 修改备注：
 * @version
 */
@Route(path = HelperArouter.Activity.AboutUsActivity.PATH)
class AboutUsActivity : BaseMVPActivity<IPresenter>(), IView {
    override val layoutResId: Int
        get() = R.layout.activity_about_us

    override fun initData(savedInstanceState: Bundle?) {
        tvVersion.text = "当前版本${BuildConfig.versionNumber}"
        ivBack.clicksThrottle().subscribe { finish() }


    }

    override fun createPresenter(): IPresenter? {
        return null
    }



}
