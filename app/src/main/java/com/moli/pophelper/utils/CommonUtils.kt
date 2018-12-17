package com.moli.pophelper.utils

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import com.moli.module.framework.utils.FileDirUtils
import com.moli.module.framework.utils.rx.toIoAndMain
import com.moli.module.framework.utils.writeFileFromIS
import com.moli.module.net.http.RetrofitUtils
import com.moli.module.net.http.api.APIDownload
import com.moli.module.net.http.provider.impl.APIDownloadServiceImpl
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.math.RoundingMode
import java.text.NumberFormat

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/7 01:40
 * 修改人：lijilong
 * 修改时间：2018/12/7 01:40
 * 修改备注：
 * @version
 */
fun downloadPop(url: String) {
    var downloadApi = RetrofitUtils.downloadRetrofit.create(APIDownload::class.java)
    val dir = FileDirUtils.getApkDir()
    var apkName = url.substring(url.lastIndexOf("/") + 1, url.length)
    if(!apkName.endsWith(".apk")){
        apkName = apkName.replace(".","").trim()+".apk"
    }
    val apkFile = File(dir, apkName)
    if (apkFile.exists()) {
        apkFile.mkdir()
    }
    apkFile.createNewFile()
    Timber.e("apkFile=${apkFile.absolutePath}")
    downloadApi.downloadAPK(url).toIoAndMain().observeOn(Schedulers.io())
        .map {
            writeFileFromIS(apkFile, it.byteStream())
        }.subscribe(object : Observer<Boolean> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: Boolean) {
                Timber.e("downloadpop onext t =$t")
                if (t) {
                    installAPk(apkFile)
                }
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }


        })
}

private fun installAPk(apkFile: File) {
//        if (mProgress != 100)
//            return
    val intent = Intent(Intent.ACTION_VIEW)
    //如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
    try {
        val command = arrayOf("chmod", "777", apkFile.toString())
        val builder = ProcessBuilder(*command)
        builder.start()
    } catch (ignored: IOException) {
    }

    val data: Uri
    // 判断版本大于等于7.0
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        intent.action = Intent.ACTION_INSTALL_PACKAGE
        //清单文件中配置的authorities
        data = FileProvider.getUriForFile(Utils.getApp(), "com.moli.pophelper.fileProvider", apkFile)
        // 给目标应用一个临时授权
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION//重点！！
    } else {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK//重点！！！
        data = Uri.fromFile(apkFile)
    }
    intent.setDataAndType(data, "application/vnd.android.package-archive")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    ActivityUtils.getTopActivity().startActivity(intent)

}

/***
 * //格式化数字，用逗号分割
 * @param number 12345678.12345
 * @return 123, 456, 78.12345
 */
fun formatNumberWithCommaSplit(number: Double?, decimalNumber: Int): String {
    if (number == null) {
        if (decimalNumber == 0) {
            return "0"
        } else if (decimalNumber == 1) {
            return "0.0"
        } else if (decimalNumber == 2) {
            return "0.00"
        } else if (decimalNumber == 3) {
            return "0.000"
        } else if (decimalNumber == 4) {
            return "0.0000"
        }
        return ""
    }
    val numberFormat = NumberFormat.getInstance()
    numberFormat.roundingMode = RoundingMode.HALF_UP
    numberFormat.isGroupingUsed = true//使用000,000,00.0000方式显示
    numberFormat.minimumFractionDigits = decimalNumber//设置固定4位小数位
    numberFormat.maximumFractionDigits = decimalNumber
    return numberFormat.format(number)

}

/***
 * //格式化数字，用逗号分割
 * @param number 12345678.12345
 * @return 123, 456, 78.12345
 */
fun formatNumberWithCommaSplit(number: Long?, decimalNumber: Int): String {
    if (number == null) {
        if (decimalNumber == 0) {
            return "0"
        } else if (decimalNumber == 1) {
            return "0.0"
        } else if (decimalNumber == 2) {
            return "0.00"
        } else if (decimalNumber == 3) {
            return "0.000"
        } else if (decimalNumber == 4) {
            return "0.0000"
        }
        return ""
    }
    val numberFormat = NumberFormat.getInstance()
    numberFormat.roundingMode = RoundingMode.HALF_UP
    numberFormat.isGroupingUsed = true//使用000,000,00.0000方式显示
    numberFormat.minimumFractionDigits = decimalNumber//设置固定4位小数位
    numberFormat.maximumFractionDigits = decimalNumber
    return numberFormat.format(number)

}
