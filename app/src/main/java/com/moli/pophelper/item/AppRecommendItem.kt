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
import zlc.season.rxdownload3.extension.ApkOpenExtension
import zlc.season.rxdownload3.helper.dispose

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
class AppRecommendItem() : LayoutContainerItem<AppModel>() {
    private var disposable: Disposable? = null
    private var currentStatus: Status? = null
    private var mission: Mission? = null

    lateinit var model: AppModel
    override val layoutResId: Int
        get() = R.layout.item_app_recommend

    override fun setViews() {
        super.setViews()
        tvProgress.clicksThrottle().subscribe {
            Timber.e("tvProgress click")
            mission = Mission("http://shouji.360tpcdn.com/170919/9f1c0f93a445d7d788519f38fdb3de77/com.UCMobile_704.apk")
            disposable?.dispose()
            disposable = RxDownload.create(mission!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { status ->
                    Timber.e("subscribe status")
                    currentStatus = status
                    progressBar.max = 100
                    progressBar.progress = (status.downloadSize * 100 / status.totalSize).toInt()
                    Timber.e("status=${status == null}")
                    Timber.e("percent=${status.percent()}")
                    if (status.totalSize != 0L && status.downloadSize != 0L && status.downloadSize != status.totalSize) {
                        tvProgress.text = status.percent()
                    } else {
                        tvProgress.text = "立即试玩"
                    }
                    if (status.percent() == "100.00%") {
                        install()
                    }
                }
            dispatchClick()
        }

    }

    override fun handleData(model: AppModel, position: Int) {
        this.model = model
        Timber.e("AppRecommendItem handleData")
    }

    override fun onAttach() {
        super.onAttach()

    }



    override fun onDetach() {
        super.onDetach()
        dispose(disposable)
    }

    private fun start() {
        Timber.e("mission=${mission?.url}")
        RxDownload.start(mission!!).subscribe()
    }

    private fun stop() {
        RxDownload.stop(mission!!).subscribe()
    }

    private fun install() {
        RxDownload.extension(mission!!, ApkInstallExtension::class.java).subscribe()
    }

    private fun open() {
        RxDownload.extension(mission!!, ApkOpenExtension::class.java).subscribe()
    }

    private fun dispatchClick() {
        if (currentStatus is Normal) {
            Timber.e("dispatchClick 1")
            start()
        } else if (currentStatus is Suspend) {
            Timber.e("dispatchClick 2")
            start()
        } else if (currentStatus is Failed) {
            Timber.e("dispatchClick 3")
            start()
        } else if (currentStatus is Downloading) {
            Timber.e("dispatchClick 4")
            stop()
        } else if (currentStatus is Succeed) {
            Timber.e("dispatchClick 5")
            install()
        } else if (currentStatus is ApkInstallExtension.Installed) {
            Timber.e("dispatchClick 6")
            open()
        }
    }

}
