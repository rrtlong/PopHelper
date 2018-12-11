package com.moli.pophelper.item

import android.Manifest
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.model.base.AppModel
import com.moli.module.net.imageloader.loadImage
import com.moli.pophelper.MainActivity
import com.moli.pophelper.R
import com.moli.pophelper.widget.LayoutContainerItem
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.item_app_recommend.*
import timber.log.Timber
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.*
import zlc.season.rxdownload3.extension.ApkInstallExtension
import zlc.season.rxdownload3.extension.ApkOpenExtension

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
            if (AppUtils.isAppInstalled(model.packageName!!)) {
                AppUtils.launchApp(model.packageName)
            } else {
                RxPermissions(ActivityUtils.getTopActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it) {
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
                                is Failed -> {
                                    Timber.e("失败")
                                }
                                is Succeed -> {
                                    RxDownload.extension(model.downloadUrl!!, ApkInstallExtension::class.java)
                                        .subscribe()
                                    Timber.e("安装")
                                }
                                is ApkInstallExtension.Installing -> Timber.e("安装中")
                                is ApkInstallExtension.Installed -> {
                                    AppUtils.launchApp(model.packageName)
                                    Timber.e("打开")
                                }
                                else -> ""
                            }
                        } else {
                            var activity = ActivityUtils.getTopActivity()
                            if (activity != null && activity is MainActivity) {
                                activity.showMessage("请开启读写权限")
                            }

                        }
                    }
            }

        }

    }

    override fun handleData(model: AppModel, position: Int) {
        this.model = model
        progressBar.max = 100
        mlIcon.loadImage(model.headImgUrl)
        tvTitle.text = model.title
        tvPeoples.text = "有${model.pv}人在试玩"
        tvLabel.text = model.tagText
        tvProgress.text = "立即试玩"
        tvDesc.text = model.content
        mlBigCover.loadImage(model.imgUrl)
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
        if (AppUtils.isAppInstalled(model.packageName!!)) {
            tvProgress.text = "立即试玩"
            progressBar.progress = 100
        } else {
            when (status) {
                is Normal -> {
                    tvProgress.text = "立即试玩"
                    progressBar.progress = 0
                }
                is Suspend -> {
                    var percent = (status.downloadSize * 100.0 / status.totalSize).toInt()
                    if (percent > 100) {
                        percent = 100
                    }
                    tvProgress.text = "立即试玩"
                    progressBar.progress = (status.downloadSize * 100.0 / status.totalSize).toInt()
                    Timber.e("已暂停")
                }
                is Waiting -> {
                    Timber.e("等待中")
                    var percent = (status.downloadSize * 100.0 / status.totalSize).toInt()
                    if (percent > 100) {
                        percent = 100
                    }
                    tvProgress.text = "$percent%"
                    progressBar.progress = (status.downloadSize * 100.0 / status.totalSize).toInt()
                }
                is Downloading -> {
                    Timber.e("正在下载")
                    var percent = (status.downloadSize * 100.0 / status.totalSize).toInt()
                    if (percent > 100) {
                        percent = 100
                    }
                    tvProgress.text = "$percent%"
                    progressBar.progress = (status.downloadSize * 100.0 / status.totalSize).toInt()
                }
                is Failed -> {
                    tvProgress.text = "立即试玩"
                    progressBar.progress = 0
                    Timber.e("失败")
                }
                is Succeed -> {
                    Timber.e("安装")
                    tvProgress.text = "立即试玩"
                    progressBar.progress = 100
//                    RxDownload.extension(model.downloadUrl!!, ApkInstallExtension::class.java).subscribe()
                }
                is ApkInstallExtension.Installing -> {
                    tvProgress.text = "安装中"
                    progressBar.progress = 100
                    Timber.e("安装中")
                }
                is ApkInstallExtension.Installed -> {
                    tvProgress.text = "立即试玩"
                    progressBar.progress = 100
                    RxDownload.extension(model.downloadUrl!!, ApkOpenExtension::class.java).subscribe()
                    Timber.e("打开")
                }
                else -> ""
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable?.dispose()
        }
    }


}
