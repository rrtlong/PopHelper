package com.moli.module.widget.widget.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moli.module.widget.R
import com.moli.module.widget.widget.dialog.base.BottomDialog
import kotlinx.android.synthetic.main.dialog_sex.*

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/9/17 18:46
 * 修改人：lijilong
 * 修改时间：2018/9/17 18:46
 * 修改备注：
 * @version
 */
class SexDialog(context: Context, styleId: Int, val onClick: (view: View) -> Unit) : BottomDialog(context, styleId) {
    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.dialog_sex, null)
        setContentView(rootView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        tvMale.setOnClickListener {
            onClick(it)
            dismiss()
        }
        tvFemale.setOnClickListener {
            onClick(it)
            dismiss()
        }
        tvCancel.setOnClickListener { dismiss() }
    }


}
