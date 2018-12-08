package com.moli.pophelper.item

import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.StrategyModel
import com.moli.module.net.imageloader.loadImage
import com.moli.pophelper.R
import com.moli.pophelper.utils.PageSkipUtils
import com.moli.pophelper.widget.LayoutContainerItem
import kotlinx.android.synthetic.main.item_strategy.*

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/5 11:19
 * 修改人：lijilong
 * 修改时间：2018/12/5 11:19
 * 修改备注：
 * @version
 */
class StrategyItem : LayoutContainerItem<StrategyModel>() {
    lateinit var model: StrategyModel
    override val layoutResId: Int
        get() = R.layout.item_strategy

    override fun setViews() {
        super.setViews()
        rootView.clicksThrottle().subscribe {
            PageSkipUtils.skipGenderWeb(model.contentUrl ?: "", model.title, true)
        }
    }

    override fun handleData(model: StrategyModel, position: Int) {
        this.model = model
       /* tvTitle.text = model.title
        mlCover.loadImage(model.imgUrl)
        tvType.text = when (model.contentType){
            1->""
            else->""
        }
        tvReads.text = "${model.pv}"*/

    }

}
