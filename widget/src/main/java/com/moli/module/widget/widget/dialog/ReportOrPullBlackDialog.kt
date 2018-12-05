package com.moli.module.widget.widget.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moli.module.widget.R
import com.moli.module.widget.widget.dialog.base.BottomDialog
import kotlinx.android.synthetic.main.dialog_report_and_pullblack.*

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/9/26 20:11
 * 修改人：lijilong
 * 修改时间：2018/9/26 20:11
 * 修改备注：
 * @version
 */
class ReportOrPullBlackDialog(context: Context, styleId: Int, val onClick: (view: View) -> Unit) : BottomDialog(context, styleId) {
    init {
        build()
    }

    fun build() {
        var rootView = LayoutInflater.from(context).inflate(R.layout.dialog_report_and_pullblack, null)
        setContentView(rootView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        tvShare.setOnClickListener {
            onClick(it)
            dismiss()
        }
        tvReport.setOnClickListener {
            onClick(it)
            dismiss()
        }
        tvPullBlack.setOnClickListener {
            onClick(it)
            dismiss()
        }
        tvCancel.setOnClickListener { dismiss() }
    }

}
