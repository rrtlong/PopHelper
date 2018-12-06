package com.moli.module.widget.widget.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.moli.module.widget.R
import kotlinx.android.synthetic.main.dialog_check_isinstall_app.*

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/6 15:06
 * 修改人：lijilong
 * 修改时间：2018/12/6 15:06
 * 修改备注：
 * @version
 */
class CheckIsInstallAppDialog(context: Context, val onClick: (view: View) -> Unit) :
    AlertDialog(context) {

    init {
        setCanceledOnTouchOutside(true)
        requestWindowFeature(Window.FEATURE_NO_TITLE)// android:windowNoTitle
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))// android:windowBackground
        window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)// android:backgroundDimEnabled默认是true的
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.dialog_check_isinstall_app)
        ivInstalled.setOnClickListener {
            onClick(it)
            dismiss()
        }
        ivDownload.setOnClickListener {
            onClick(it)
            dismiss()
        }
    }


}
