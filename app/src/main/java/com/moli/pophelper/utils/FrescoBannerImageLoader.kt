package com.moli.pophelper.utils

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.moli.module.model.base.BannerModel
import com.moli.module.net.imageloader.loadImage
import com.moli.module.widget.widget.MLImageView
import com.youth.banner.loader.ImageLoader

/**
 * 项目名称：moli
 * 类描述：Fresco实现的Banner图片加载器
 * 创建人：liujun
 * 创建时间：2018/5/25 20:22
 * 修改人：liujun
 * 修改时间：2018/5/25 20:22
 * 修改备注：
 * @version
 */
class FrescoBannerImageLoader(val width: Int, val height: Int) : ImageLoader() {

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        if (imageView is MLImageView) {
            if (path is String) {
                imageView.loadImage(path, width, height)
            } else if (path is BannerModel) {
                imageView.loadImage(
                        if (path.image!!.isNotEmpty()) path.image else path.image,
                        width,
                        height
                )
            }
        }
    }

    override fun createImageView(context: Context?): ImageView {
        val view = MLImageView(context)
        view.layoutParams = ViewGroup.LayoutParams(width, height)
        return view
    }
}
