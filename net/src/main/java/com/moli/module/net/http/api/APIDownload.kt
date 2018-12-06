package com.moli.module.net.http.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/6 18:59
 * 修改人：lijilong
 * 修改时间：2018/12/6 18:59
 * 修改备注：
 * @version
 */
interface APIDownload {
    //下载文件 大文件下载要加这个注解（Streaming会实时下载字节码而不是将整个文件加入到内存中），不然会OOM
    @Streaming
    @GET
    fun downloadAPK(@Url url: String): Observable<ResponseBody>
}
