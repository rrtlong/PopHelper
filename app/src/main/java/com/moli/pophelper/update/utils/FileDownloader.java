package com.moli.pophelper.update.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileDownloader {
    private DowloaderListener dowloaderListener;
    private byte downState;// 0 表示未开始 ，1 表示正在下载，2 表示暂停，3 表示停止，4表示完成

    public void down(String urlStr, File outFile, long startsPoint) {
        if (dowloaderListener != null) {
            dowloaderListener.start();
        }
        downState = 0;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            int contentLength = conn.getContentLength();
            if (contentLength <= 0) return;
            conn.connect();
            InputStream inputStream = conn.getInputStream();
            DownloadFileWriteUtil.writeFile(inputStream, outFile, startsPoint, contentLength, dowloaderListener);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void stopDown() {
        downState = 3;
    }


    public void setDowloaderListener(DowloaderListener dowloaderListener) {
        this.dowloaderListener = dowloaderListener;
    }
}
