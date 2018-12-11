package com.moli.pophelper.update.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class DownloadFileWriteUtil {


    public static void writeFile(InputStream in, File destination, long startsPoint, long contentLength) throws IOException {
        writeFile(in,destination,startsPoint,contentLength,null);
    }

    public static void writeFile(InputStream in, File destination, long startsPoint, long contentLength, DowloaderListener dowloaderListener) throws IOException {
        // 随机访问文件，可以指定断点续传的起始位置
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(destination, "rwd");
        //Chanel NIO中的用法，由于RandomAccessFile没有使用缓存策略，直接使用会使得下载速度变慢，亲测缓存下载3.3秒的文件，用普通的RandomAccessFile需要20多秒。
        FileChannel channelOut = randomAccessFile.getChannel();
        // 内存映射，直接使用RandomAccessFile，是用其seek方法指定下载的起始位置，使用缓存下载，在这里指定下载位置。
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, startsPoint, contentLength);
        byte[] buffer = new byte[1024];
        int len;
        long downLoadFileSize = 0;//进度条
        while ((len = in.read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            downLoadFileSize +=len;//更新进度条
            if(dowloaderListener!=null){
                dowloaderListener.onProgress(downLoadFileSize,downLoadFileSize/(float)contentLength);
            }
        }
        in.close();
        channelOut.close();
        randomAccessFile.close();
        if(dowloaderListener!=null){
            dowloaderListener.finish();
        }
    }
}
