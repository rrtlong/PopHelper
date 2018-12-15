package com.moli.pophelper.module.mine

import android.os.Bundle
import com.aletter.xin.app.update.AppUpdateUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.IPresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.constant.EventConstant
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.utils.PageSkipUtils
import kotlinx.android.synthetic.main.activity_set.*
import org.simple.eventbus.Subscriber

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/14 15:28
 * 修改人：lijilong
 * 修改时间：2018/12/14 15:28
 * 修改备注：
 * @version
 */
@Route(path = HelperArouter.Activity.SetActivity.PATH)
class SetActivity : BaseMVPActivity<IPresenter>(), IView {
    override val layoutResId: Int
        get() = R.layout.activity_set

    override fun initData(savedInstanceState: Bundle?) {
        ivBack.clicksThrottle().subscribe { finish() }
        tvAboutUs.clicksThrottle().subscribe { PageSkipUtils.skipAboutUs() }
        tvCheckUpdate.clicksThrottle().subscribe {
            AppUpdateUtil(this).compareVersionFromJson(false)
        }

    }

    override fun createPresenter(): IPresenter? {
        return null
    }



    @Subscriber(tag = EventConstant.BE_LATEST_VERSION)
    fun beLatestVersion(msg: String) {
        showMessage("已是最新版本")
    }
}
