package com.moli.module.net.http;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * 项目名称：Android
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：16/8/24 16:35
 * 修改人：LiuJun
 * 修改时间：16/8/24 16:35
 * 修改备注：
 */
class HttpLoggerInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        printRequestLog(originalRequest);
        //发送网络请求
        Response response = chain.proceed(originalRequest);
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody body = clone.body();
        if (body == null) {
            return response;
        }
        MediaType mediaType = body.contentType();
        String resp = body.string();
        Response print = response.newBuilder().body(ResponseBody.create(mediaType, resp)).build();
        printResult(print);
        return response.newBuilder().body(ResponseBody.create(mediaType, resp)).build();
    }


    /**
     * 打印请求日志
     *
     * @throws IOException
     */
    private void printRequestLog(Request originalRequest) throws IOException {
        Timber.tag("Request").i(OkHttpLogParseKt.parseString(originalRequest));
    }


    /**
     * 打印返回日志
     *
     * @throws IOException
     */
    private void printResult(Response response) throws IOException {
        Timber.tag("Response").i(OkHttpLogParseKt.parseString(response));
    }
}
