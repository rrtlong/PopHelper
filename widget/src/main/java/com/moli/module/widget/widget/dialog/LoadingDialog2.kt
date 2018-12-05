package com.moli.module.widget.widget.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.moli.module.widget.R
import kotlinx.android.synthetic.main.dialog2_avl_layout.*

/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：zixiaojun
 * 创建时间：2018/8/18 下午3:16
 * 修改人：zixiaojun
 * 修改时间：2018/8/18 下午3:16
 * 修改备注：
 */
class LoadingDialog2 : AlertDialog {

    constructor(context: Context?) : super(context, R.style.DialogAlert)
    constructor(context: Context?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)
    constructor(context: Context?, themeResId: Int) : super(context, themeResId)

    init {
        setCanceledOnTouchOutside(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.dialog2_avl_layout)
    }


    fun setLoadingInfo(info: String) {
        loadingText.text = info
    }
}
