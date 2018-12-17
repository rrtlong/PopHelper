package com.moli.pophelper.item

import android.support.v4.content.ContextCompat
import com.blankj.utilcode.util.TimeUtils
import com.moli.module.model.base.RecordModel
import com.moli.pophelper.R
import com.moli.pophelper.utils.formatNumberWithCommaSplit
import com.moli.pophelper.widget.LayoutContainerItem
import kotlinx.android.synthetic.main.item_change_record.*

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/15 15:19
 * 修改人：lijilong
 * 修改时间：2018/12/15 15:19
 * 修改备注：
 * @version
 */
class ChangeRecordItem(val type: Int) : LayoutContainerItem<RecordModel>() {
    override val layoutResId: Int
        get() = R.layout.item_change_record

    override fun handleData(model: RecordModel, position: Int) {
        tvTime.text = TimeUtils.millis2String(model.createTime)
        tvContent.text = model.operationType
        tvMoney.text = when (type) {
            0 -> {
                "${formatNumberWithCommaSplit((model.changeCount / 100.0), 2)}元"
            }
            else -> {
                "${model.changeCount}钻石"
            }
        }
        tvMoney.setTextColor(ContextCompat.getColor(tvMoney.context,if(model.changeCount>0) R.color.color_add else R.color.color_reduce))
    }

}
