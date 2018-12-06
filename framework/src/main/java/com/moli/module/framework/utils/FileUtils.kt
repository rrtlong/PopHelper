package com.moli.module.framework.utils

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import com.blankj.utilcode.constant.MemoryConstants
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.*


/**
 * 项目名称：Jasmine
 * 类描述：
 * 创建人：liujun
 * 创建时间：2018/2/22 18:55
 * 修改人：liujun
 * 修改时间：2018/2/22 18:55
 * 修改备注：
 * @version
 */

/**
 * 文件可用空间是否大于传进来的参数
 * @param sizeMb
 * @return true: 空间足够  false：空间不足
 */
fun File.isAvailableSpace(sizeMb: Int = 20): Boolean {
    var isHasSpace = false
    if (!this.exists()) {
        return isHasSpace
    }
    val freeSpace = this.freeSpace
    val availableSpare = freeSpace / MemoryConstants.MB
    Timber.d("path: %s availableSpare = %s MB sizeMb: %s", this.absolutePath, availableSpare, sizeMb)
    if (availableSpare > sizeMb) {
        isHasSpace = true
    }
    return isHasSpace
}

/**
 * 将输入流写入文件
 *
 * @param inputString
 * @param filePath
 */
fun writeFileFromIS(
    file: File,
    input: InputStream
): Boolean {
    var os: OutputStream? = null
    try {
        os = BufferedOutputStream(FileOutputStream(file))
        val data = ByteArray(1024)
        var len: Int = 0
        do {
            os.write(data, 0, len)
            len = input.read(data, 0, 1024)
        } while (len != -1)
        return true
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    } finally {
        try {
            input.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            os?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}






