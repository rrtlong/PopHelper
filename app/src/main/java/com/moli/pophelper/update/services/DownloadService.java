package com.moli.pophelper.update.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.moli.module.model.constant.EventConstant;
import com.moli.pophelper.update.utils.DowloaderListener;
import com.moli.pophelper.update.utils.FileDownloader;
import com.moli.reward.app.update.utils.AppInfo;
import org.simple.eventbus.EventBus;
import timber.log.Timber;

import java.io.File;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DownloadService extends IntentService {

    public static final String ACTION_FOO = "com.aletter.receivers.action.FOO";
    public static final String ACTION_BAZ = "com.aletter.receivers.action.BAZ";


    public static final String EXTRA_PARAM1 = "com.aletter.receivers.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.aletter.receivers.extra.PARAM2";
    public static final String EXTRA_PARAM3 = "com.aletter.receivers.extra.PARAM3";

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                final boolean param3 = intent.getBooleanExtra(EXTRA_PARAM3, true);
                handleActionFoo(param1, param2, param3);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2, boolean param3) {
        download(param1, param2, param3);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    FileDownloader fd;

    public void download(String downloadUrl, final String apkPath, final boolean install) {
        fd = new FileDownloader();
        fd.setDowloaderListener(new DowloaderListener() {
            @Override
            public void start() {
            }

            int downProgress = 0;

            @Override
            public void onProgress(long downLoadFileSize, final float progress) {
                if ((int) (progress * 100) > downProgress) {
                    downProgress = (int) (progress * 100);
                    if (downProgress % 10 == 0) {
                        EventBus.getDefault().post(downProgress, EventConstant.APP_UPDATE_PROGRESS);
                    }
                }
            }

            @Override
            public void stop() {

            }

            @Override
            public void finish() {
                File apkFile = new File(apkPath);
                if (apkFile.exists()) {
                    SPUtils.getInstance().put("apk_downloaded", true);
                    if (install) {
                        DownloadService.this.startActivity(AppInfo.INSTANCE.createInstallApkIntent(DownloadService.this, apkFile));
                    }
                } else {
                    SPUtils.getInstance().put("apk_downloaded", false);
                    ToastUtils.showShort("安装失败");
                }
            }

            @Override
            public void exception(final Exception exception) {
                Timber.e(exception);
            }
        });
        fd.down(downloadUrl, new File(apkPath), 0);
    }


}
