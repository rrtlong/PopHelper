package com.moli.pophelper.item

import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.MusicModel
import com.moli.module.net.imageloader.loadImage
import com.moli.pophelper.R
import com.moli.pophelper.widget.LayoutContainerItem
import kotlinx.android.synthetic.main.item_wealth.*

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/5 17:55
 * 修改人：lijilong
 * 修改时间：2018/12/5 17:55
 * 修改备注：
 * @version
 */
class WealthItem : LayoutContainerItem<MusicModel>() {
    lateinit var model: MusicModel
    override val layoutResId: Int
        get() = R.layout.item_wealth

    override fun setViews() {
        super.setViews()
        rootView.clicksThrottle().subscribe {

        }
    }

    override fun handleData(model: MusicModel, position: Int) {
        this.model = model
        mlCover.loadImage(model.thumb)
    }

}
