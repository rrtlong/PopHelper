package com.moli.module.net.http.download

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/6 17:44
 * 修改人：lijilong
 * 修改时间：2018/12/6 17:44
 * 修改备注：
 * @version
 */

public interface ProgressListener {

    abstract fun onStartDownload()

    abstract fun onProgress(progress: Int)

    abstract fun onFinishDownload()

    abstract fun onFail(errorInfo: String)
}
