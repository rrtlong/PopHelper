package com.moli.module.framework.utils

import android.os.Environment
import android.os.Environment.*
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.Utils
import timber.log.Timber
import java.io.File

/**
 * 项目名称：moli
 * 类描述：
 * 创建人：liujun
 * 创建时间：2018/4/21 13:32
 * 修改人：liujun
 * 修改时间：2018/4/21 13:32
 * 修改备注：
 * @version
 */
object FileDirUtils {

    /**
     * 获取SD卡上的私有目录，这里的文件会随着App卸载而被删除
     * SD卡写权限：
     * API < 19：需要申请
     * API >= 19：不需要申请
     * 文件目录：
     * Context.getExternalFilesDir() 绝对路径：SDCard/Android/data/应用包名/files/
     * 缓存目录：
     * Context.getExternalCacheDir()  绝对路径：SDCard/Android/data/应用包名/cache/
     *
     * 获取SD卡上的公有目录，APP卸载不会删除文件，需要SD卡写权限
     * Environment.getExternalStoragePublicDirectory()
     */

    //apk文件下载目录
    private const val APK_DIR = "apk"

    //apk文件命名规则，"moli_版本号.apk"
    private const val APK_NAME = "moli_%s.apk"

    //图片文件缓存路径
    private const val IMAGE = "image"

    //IM缓存目录
    private const val NIM = "nim"

    //录音文件缓存目录
    private const val RECORD = "record"

    //临时缓存文件，应用每次启动的时候进行清理
    private const val TEMP = "temp"

    //播放音频文件缓存目录（边播边存）
    private const val AUDIO_CACHE = "audioCache"

    //播放视频文件缓存目录（边播边存）
    private const val VIDEO_CACHE = "videoCache"

    private const val VIDEO_SAVE = "videoSave"

    private const val APP_ROOT_FILE_NAME = "/RewardVideo"
    /**
     * 获取临时缓存目录，应用每次启动的时候进行清理
     */
    fun getTempDir(): File {
        return getDiskCacheDir(TEMP)
    }

    /**
     * 获取录音文件缓存目录
     */
    fun getRecordDir(): File {
        return getDiskCacheDir(RECORD)
    }

    /**
     * 获取apk文件目录
     */
    fun getApkDir(): File {
        return getDiskCacheDir(APK_DIR)
    }

    /**
     * 根据版本号获取apk名
     */
    fun getApkName(version: String): String {
        return APK_NAME.format(version)
    }

    /**
     * 根据版本号获取apk文件
     */
    fun getApkFile(version: String): File {
        return File(getApkDir(), getApkName(version))
    }

    /**
     * 获取图片文件缓存目录
     */
    fun getImageDir(): File {
        return getDiskCacheDir(IMAGE)
    }


    /**
     * 获取IM缓存目录
     */
    fun getNimDir(): File {
        return getDiskCacheDir(NIM)
    }

    /**
     * 获取播放音频文件的缓存路径
     */
    fun getAudioCacheDir(): File {
        return getDiskCacheDir(AUDIO_CACHE)
    }


    fun getVideoCacheDir(): File {
        return getDiskCacheDir(VIDEO_CACHE)
    }

    /**
     *视频下载路径
     */

    fun getVideoSaveDir(): File {
        return getDiskSaveDir(VIDEO_SAVE)
    }

    /**
     * 获取缓存目录
     * @param uniqueName 需要获取的目录名
     */
    private fun getDiskCacheDir(uniqueName: String): File {
        val cacheFile = if (MEDIA_MOUNTED == getExternalStorageState() || !isExternalStorageRemovable()) {
            Utils.getApp().externalCacheDir.path
        } else {
            CountUtils.onError("外部存储不可用")
            Utils.getApp().cacheDir.path
        }
        val uniqueFile = File(cacheFile, uniqueName)
        FileUtils.createOrExistsDir(uniqueFile)
        return uniqueFile
    }


    private fun getDiskSaveDir(uniqueName: String): File {
        val uniqueFile = File(Environment.getExternalStorageDirectory().absolutePath + APP_ROOT_FILE_NAME, uniqueName)
        Timber.i("file-----%s", uniqueFile.absolutePath)
        FileUtils.createOrExistsDir(uniqueFile)
        return uniqueFile
    }


}
