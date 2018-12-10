package com.moli.pophelper.item

import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.AppModel
import com.moli.pophelper.R
import com.moli.pophelper.widget.LayoutContainerItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.item_app_recommend.*
import timber.log.Timber
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.*
import zlc.season.rxdownload3.extension.ApkInstallExtension

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/6 01:31
 * 修改人：lijilong
 * 修改时间：2018/12/6 01:31
 * 修改备注：
 * @version
 */
class AppRecommendItem(val onClick: (model: AppModel) -> Unit) : LayoutContainerItem<AppModel>() {
    var disposable: Disposable? = null
    var status: Status? = null
    var start = true

    lateinit var model: AppModel
    override val layoutResId: Int
        get() = R.layout.item_app_recommend

    override fun setViews() {
        super.setViews()
        tvProgress.clicksThrottle().subscribe {
            //            onClick(model)
            Timber.e("status = ${status?.percent()}  ->${status?.toString()}")
            when (status) {
                is Normal -> {
                    Timber.e("开始")
                    RxDownload.start(model.downloadUrl!!).subscribe()
                }
                is Suspend -> {
                    Timber.e("已暂停")
                    RxDownload.start(model.downloadUrl!!).subscribe()
                }
                is Waiting -> {
                    Timber.e("等待中")
                    RxDownload.stop(model.downloadUrl!!).subscribe()
                }
                is Downloading -> {
                    Timber.e("正在下载")
                    RxDownload.stop(model.downloadUrl!!).subscribe()
                }
                is Failed -> Timber.e("失败")
                is Succeed -> Timber.e("安装")
                is ApkInstallExtension.Installing -> Timber.e("安装中")
                is ApkInstallExtension.Installed -> Timber.e("打开")
                else -> ""
            }


        }

    }

    override fun handleData(model: AppModel, position: Int) {
        this.model = model
    }

    override fun onAttach() {
        super.onAttach()
        disposable = RxDownload.create(model.downloadUrl!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { status ->
                this.status = status
                updateStatus(status)
            }

    }

    fun updateStatus(status: Status) {
        if (status.percent() == "0.00%") {
            tvProgress.text = "立即试玩"
            progressBar.progress = 0
        } else if (status.percent() == "100.00%") {
            tvProgress.text = "立即试玩"
            progressBar.progress = 100
        } else {
            var percent = (status.downloadSize * 100.0 / status.totalSize).toInt()
            if (percent > 100) {
                percent = 100
            }
            tvProgress.text = "$percent%"
            progressBar.progress = (status.downloadSize * 100.0 / status.totalSize).toInt()
        }
    }


}
