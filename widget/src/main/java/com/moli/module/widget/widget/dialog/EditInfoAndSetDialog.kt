package com.moli.module.widget.widget.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moli.module.widget.widget.dialog.base.BottomDialog
import com.moli.module.widget.R
import kotlinx.android.synthetic.main.dialog_edit_info_and_set.*

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/9/17 15:36
 * 修改人：lijilong
 * 修改时间：2018/9/17 15:36
 * 修改备注：
 * @version
 */
class EditInfoAndSetDialog(context: Context, styleId: Int, val onClick: (view: View) -> Unit) : BottomDialog(context, styleId) {
    init {
        build()
    }

    fun build() {
        var rootView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_info_and_set, null)
        setContentView(rootView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        tvShare.setOnClickListener {
            onClick(it)
            dismiss()
        }
        tvEditInfo.setOnClickListener {
            onClick(it)
            dismiss()
        }
        tvSet.setOnClickListener {
            onClick(it)
            dismiss()
        }
        tvCancel.setOnClickListener { dismiss() }
    }

}
