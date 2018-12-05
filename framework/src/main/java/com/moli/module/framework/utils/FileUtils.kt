package com.moli.module.framework.utils

import com.blankj.utilcode.constant.MemoryConstants
import timber.log.Timber
import java.io.File


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



