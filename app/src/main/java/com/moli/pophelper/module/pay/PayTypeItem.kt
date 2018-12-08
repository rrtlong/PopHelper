package com.moli.pophelper.module.pay

import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.PayActionModel
import com.moli.pophelper.R
import com.moli.pophelper.widget.LayoutContainerItem
import kotlinx.android.synthetic.main.item_pay_type_layout.*

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/7 18:41
 * 修改人：lijilong
 * 修改时间：2018/12/7 18:41
 * 修改备注：
 * @version
 */
class PayTypeItem(val itemClick: (itemModel: PayActionModel) -> Unit) : LayoutContainerItem<PayActionModel>() {

    var itemModel: PayActionModel? = null
    override fun setViews() {
        super.setViews()
        rootView.clicksThrottle().subscribe {
            itemModel?.let(itemClick)
        }
    }

    override val layoutResId: Int
        get() = R.layout.item_pay_type_layout

    override fun handleData(model: PayActionModel, position: Int) {
        itemModel = model
        ivIcon.setImageResource(model.imageId)
        tvName.text = model.name
    }
}
