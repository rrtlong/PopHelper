package com.moli.module.framework.utils

import timber.log.Timber

import java.io.File
import java.io.FileInputStream
import java.text.DecimalFormat

/**
 * Created by fanxiaofeng on 2017/6/21.
 */

object FileSizeUtil {

    const val SIZE_TYPE_B = 1 // 获取文件大小单位为B的double值
    const val SIZE_TYPE_KB = 2 // 获取文件大小单位为KB的double值
    const val SIZE_TYPE_MB = 3 // 获取文件大小单位为MB的double值
    const val SIZE_TYPE_GB = 4 // 获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */

    fun getFileOrFilesSize(filePath: String, sizeType: Int): Double {
        val file = File(filePath)
        var blockSize: Long = 0
        try {
            if (file.isDirectory) {
                blockSize = getFileSizes(file)
            } else {
                if (file.exists()) {
                    blockSize = getFileSize(file)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.e("获取失败!")
        } finally {
            return formatFileSize(blockSize, sizeType)
        }
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    fun getAutoFileOrFilesSize(file: File): String {

        var blockSize: Long = 0
        try {
            blockSize = if (file.isDirectory) {
                getFileSizes(file)
            } else {
                getFileSize(file)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.e("获取失败!")
        }

        return formatFileSize(blockSize)
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun getFileSize(file: File?): Long {
        var size: Long = 0
        if (null != file && file.exists()) {
            var fis: FileInputStream? = null
            fis = FileInputStream(file)
            size = fis.available().toLong()
            fis.close()
        } else {
            Timber.e("文件不存在!")
        }
        return size
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun getFileSizes(f: File): Long {
        var size: Long = 0
        try {
            val flist = f.listFiles()
            if (null != flist) {
                for (i in flist.indices) {
                    if (flist[i].isDirectory) {
                        size += getFileSizes(flist[i])
                    } else {
                        if (null != flist[i] && flist[i].exists()) {
                            size += getFileSize(flist[i])
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return size
        }

    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private fun formatFileSize(fileS: Long): String {
        val df = DecimalFormat("#.00")
        val wrongSize = "0B"
        if (fileS == 0L) {
            return wrongSize
        }
        return when {
            fileS < 1024 -> df.format(fileS.toDouble()) + "B"
            fileS < 1048576 -> df.format(fileS.toDouble() / 1024) + "KB"
            fileS < 1073741824 -> df.format(fileS.toDouble() / 1048576) + "MB"
            else -> df.format(fileS.toDouble() / 1073741824) + "GB"
        }
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private fun formatFileSize(fileS: Long, sizeType: Int): Double {
        val df = DecimalFormat("#.00")
        var fileSizeLong = 0.0
        when (sizeType) {
            SIZE_TYPE_B -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble()))!!
            SIZE_TYPE_KB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1024))!!
            SIZE_TYPE_MB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1048576))!!
            SIZE_TYPE_GB -> fileSizeLong = java.lang.Double.valueOf(df
                    .format(fileS.toDouble() / 1073741824))!!
            else -> {
            }
        }
        return fileSizeLong
    }

}
