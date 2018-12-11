package com.moli.pophelper.update.utils;

public interface DowloaderListener {
    void start();
    void onProgress(long downLoadFileSize, float progress);
    void stop();
    void finish();
    void exception(Exception exception);
}
