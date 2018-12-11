package com.aletter.xin.app.update.view

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.VersionModel
import com.moli.pophelper.R
import com.moli.pophelper.update.utils.BaseDialog
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.update_dialog_tip.*

class UpdateDialog(
    val context: Context,
    btnConfirmClick: (version: VersionModel) -> Unit,
    alertConfirmClick: () -> Unit
) : LayoutContainer {
    override val containerView: View?
        get() = rootView

    var rootView: View? = null
    private var updateDialog: BaseDialog

    private var alertDialogBuilder: AlertDialog.Builder? = null

    var version: VersionModel? = null

    init {
        rootView = View.inflate(context, R.layout.update_dialog_tip, null)
        updateDialog = BaseDialog(context, rootView, false)
        updateDialog.setCanceledOnTouchOutside(false)
        val width = context.resources.displayMetrics.widthPixels * 4 / 5
        updateDialog.setWidth(width)

        ivClose?.setOnClickListener {
            updateDialog.dismiss()
        }
        btnConfirm?.clicksThrottle()?.subscribe {
            updateDialog.dismiss()
            version?.let(btnConfirmClick)
        }

        alertDialogBuilder = AlertDialog.Builder(context)
            .setTitle("当前处于非wifi环境")
            .setMessage("是否下载")
            .setPositiveButton("是") { p0, _ ->
                p0?.dismiss()
                alertConfirmClick()
            }
            .setNegativeButton("否") { p0, _ ->
                p0.dismiss()
            }
    }


    fun initLayoutContent(version: VersionModel) {
        this.version = version
        tvVersion?.text = version.updateVersion
        tvUpdateInfo?.text = version.updateContent
    }

    fun setTitleVisibility(flag: Boolean) {
        if (flag) {
            tvTitle?.visibility = View.VISIBLE
            btnConfirm.text = "安装"
        } else {
            tvTitle?.visibility = View.GONE
            btnConfirm.text = "立即更新"
        }
    }


    fun showUpdateDialog(isForceUpdate: Boolean) {
        if (isForceUpdate) {
            ivClose?.visibility = View.INVISIBLE
        }
        updateDialog.show()
    }

    fun showAlertDialog() {
        alertDialogBuilder?.show()
    }

}

