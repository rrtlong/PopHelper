package com.moli.pophelper.item

import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.AppModel
import com.moli.module.model.base.ProgressModel
import com.moli.module.model.constant.EventConstant
import com.moli.pophelper.R
import com.moli.pophelper.R.id.ivTryPlay
import com.moli.pophelper.utils.downloadPop
import com.moli.pophelper.widget.LayoutContainerItem
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.item_app_recommend.*
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber
import timber.log.Timber
import zlc.season.rxdownload3.core.Mission
import zlc.season.rxdownload3.core.Status
import zlc.season.rxdownload3.helper.dispose

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/6 01:31
 * 修改人：lijilong
 * 修改时间：2018/12/6 01:31
 * 修改备注：
 * @version
 */
class AppRecommendItem(val onClick: (model: AppModel) -> Unit) : LayoutContainerItem<AppModel>() {
    private var disposable: Disposable? = null
    private var currentStatus: Status? = null
    private var mission: Mission? = null

    lateinit var model: AppModel
    override val layoutResId: Int
        get() = R.layout.item_app_recommend

    override fun setViews() {
        super.setViews()
        ivTryPlay.clicksThrottle().subscribe {
            onClick(model)
        }

    }

    override fun handleData(model: AppModel, position: Int) {
        this.model = model
    }


}
