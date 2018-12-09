package com.moli.module.widget.widget.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.blankj.utilcode.util.ScreenUtils
import com.moli.module.widget.R
import kotlinx.android.synthetic.main.dialog_download_game_progress.*
import org.jetbrains.anko.dip


/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/6 17:20
 * 修改人：lijilong
 * 修改时间：2018/12/6 17:20
 * 修改备注：
 * @version
 */
class DownloadProcessDialog(context: Context) : AlertDialog(context) {
    var totalWidth: Int? = null

    init {
        totalWidth = ScreenUtils.getScreenWidth() - context.dip(60)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)// android:windowNoTitle
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))// android:windowBackground
        window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)// android:backgroundDimEnabled默认是true的
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.dialog_download_game_progress)

    }

    fun setPercent(progress: Int) {
        layer2.layoutParams.width = (totalWidth!! * progress / 100.0f).toInt()
        tvPercent.text = "%d%%".format(progress)

    }

    override fun show() {
        super.show()
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        val layoutParams = window!!.attributes
        layoutParams.gravity = Gravity.CENTER
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        window!!.decorView.setPadding(0, 0, 0, 0)
        window!!.attributes = layoutParams
    }



}
