package com.moli.pophelper.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.moli.pophelper.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.*;
import timber.log.Timber;


/**
 * Created by guodong on 2016/4/21 17:22.
 */
public class UMShareUtil {
    private Activity mContext;
    private UMShareAPI mShareAPI = null;
    private UMShareListener shareListener;
    private String SHARE_DEFAULT_LOGO_URL = "http://file.xsawe.top/20170701/logo.png";

    public UMShareUtil(Activity context, UMShareListener shareListener) {
        this.mContext = context;
        this.shareListener = shareListener;
        mShareAPI = UMShareAPI.get(context);
    }

    public void shareWeb(SHARE_MEDIA platform, String title, String desc, String imageUrl, String targetUrl) {
        try {
            if (platform == SHARE_MEDIA.SINA) {
                shareImage(platform, title, desc, imageUrl);
                return;
            }
            UMWeb umWeb = new UMWeb(targetUrl);
            if (!TextUtils.isEmpty(title)) {
                umWeb.setTitle(title);
            }
            if (!TextUtils.isEmpty(desc)) {
                umWeb.setDescription(desc);
            }
            if (TextUtils.isEmpty(imageUrl)) {
                imageUrl = SHARE_DEFAULT_LOGO_URL;
            }
            Timber.d("share_image_url==" + imageUrl);
            UMImage umImage = new UMImage(mContext, imageUrl);
            umWeb.setThumb(umImage);
            ShareAction shareAction = new ShareAction(mContext);
            shareAction.withMedia(umWeb);
            shareAction.setPlatform(platform);
            if (!mContext.isDestroyed() && !mContext.isFinishing()) {
                try {
                    mShareAPI.doShare(mContext, shareAction, shareListener);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {

        }
    }

    public void shareWeb(SHARE_MEDIA platform, String title, String desc, Bitmap imageBitmap, String targetUrl) {
        try {
            if (platform == SHARE_MEDIA.SINA) {
                shareImage(platform, title, desc, imageBitmap);
                return;
            }
            UMWeb umWeb = new UMWeb(targetUrl);
            if (!TextUtils.isEmpty(title)) {
                umWeb.setTitle(title);
            }
            if (!TextUtils.isEmpty(desc)) {
                umWeb.setDescription(desc);
            }
            if (imageBitmap == null) {
                imageBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_launcher);
            }
            //水印
            //        UMImageMark umImageMark = new UMImageMark();
            //        umImageMark.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
            //        umImageMark.setMarkBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher));
            //        umImageMark.setAlpha(0.5f);//设置透明度
            //        umImageMark.setMargins(5, 5, 5, 5);//设置边距
            UMImage umImage = new UMImage(mContext, imageBitmap);
            umWeb.setThumb(umImage);
            ShareAction shareAction = new ShareAction(mContext);
            shareAction.withMedia(umWeb);
            shareAction.setPlatform(platform);
            if (!mContext.isDestroyed() && !mContext.isFinishing()) {
                try {
                    mShareAPI.doShare(mContext, shareAction, shareListener);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {

        }
    }

    public void shareLink(SHARE_MEDIA platform, String title, String desc, String targetUrl) {
        try {
            UMWeb umWeb = new UMWeb(targetUrl);
            if (!TextUtils.isEmpty(title)) {
                umWeb.setTitle(title);
            }
            if (!TextUtils.isEmpty(desc)) {
                umWeb.setDescription(desc);
            }
            ShareAction shareAction = new ShareAction(mContext);
            shareAction.withMedia(umWeb);
            shareAction.setPlatform(platform);
            try {
                if (!mContext.isDestroyed() && !mContext.isFinishing()) {
                    mShareAPI.doShare(mContext, shareAction, shareListener);
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {

        }
    }

    public void shareImage(SHARE_MEDIA platform, String title, String desc, String imageUrl) {
        try {
            UMImage umImage = new UMImage(mContext, imageUrl);
            if (!TextUtils.isEmpty(title)) {
                umImage.setTitle(title);
            }
            if (!TextUtils.isEmpty(desc)) {
                umImage.setDescription(desc);
            }
            ShareAction shareAction = new ShareAction(mContext);
            shareAction.withMedia(umImage);
            if (platform == SHARE_MEDIA.SINA && !TextUtils.isEmpty(desc)) {
                shareAction.withText(desc);
            }
            shareAction.setPlatform(platform);
            if (!mContext.isDestroyed() && !mContext.isFinishing()) {
                try {
                    mShareAPI.doShare(mContext, shareAction, shareListener);
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {

        }
    }


    /**
     * UMusic music = new UMusic(musicurl);//音乐的播放链接
     * music.setTitle("This is music title");//音乐的标题
     * music.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");//音乐的缩略图
     * music.setDescription("my description");//音乐的描述
     * music.setmTargetUrl(Defaultcontent.url);//音乐的跳转链接
     **/
    public void shareMusic(SHARE_MEDIA platform, String title, String desc, String musicurl, String targetUrl, String thumb) {
        try {
            if (TextUtils.isEmpty(musicurl)) {
                Timber.d("分享帖子链接为空");
                return;
            }
            UMusic uMusic = new UMusic(musicurl);
            if (!TextUtils.isEmpty(desc)) {
                uMusic.setDescription(desc);
            }
            if (!TextUtils.isEmpty(title)) {
                uMusic.setTitle(title);
            }
            if (!TextUtils.isEmpty(targetUrl)) {
                uMusic.setmTargetUrl(targetUrl);
            }
            if (!TextUtils.isEmpty(thumb)) {
                uMusic.setThumb(new UMImage(mContext, thumb));
            }
            ShareAction shareAction = new ShareAction(mContext);
            shareAction.withMedia(uMusic);
            shareAction.setPlatform(platform);
            //            shareAction.share();
            if (!mContext.isDestroyed() && !mContext.isFinishing()) {
                try {
                    mShareAPI.doShare(mContext, shareAction, shareListener);
                } catch (Exception e) {
                    Timber.e(e);
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            Timber.e(e);
            e.printStackTrace();
        }
    }

    public void shareVideo(SHARE_MEDIA platform, String title, String desc, String videoUrl) {
        try {
            UMVideo umVideo = new UMVideo(videoUrl);
            if (!TextUtils.isEmpty(title)) {
                umVideo.setTitle(title);
            }
            if (!TextUtils.isEmpty(desc)) {
                umVideo.setDescription(desc);
            }
            ShareAction shareAction = new ShareAction(mContext);
            shareAction.withMedia(umVideo);
            shareAction.setPlatform(platform);
            if (!mContext.isDestroyed() && !mContext.isFinishing()) {
                try {
                    mShareAPI.doShare(mContext, shareAction, shareListener);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {

        }
    }


    public void shareImage(SHARE_MEDIA platform, String title, String desc, Bitmap imageBitmap) {
        try {
            if (imageBitmap == null) {
                imageBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_launcher);
            }
            UMImage umImage = new UMImage(mContext, imageBitmap);
            if (!TextUtils.isEmpty(title)) {
                umImage.setTitle(title);
            }
            if (!TextUtils.isEmpty(desc)) {
                umImage.setDescription(desc);
            }
            ShareAction shareAction = new ShareAction(mContext);
            shareAction.withMedia(umImage);
            if (platform == SHARE_MEDIA.SINA && !TextUtils.isEmpty(desc)) {
                shareAction.withText(desc);
            }
            shareAction.setPlatform(platform);
            if (!mContext.isDestroyed() && !mContext.isFinishing()) {
                mShareAPI.doShare(mContext, shareAction, shareListener);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean checkWXInstalled() {
        return mShareAPI.isInstall(mContext, SHARE_MEDIA.WEIXIN);
    }

    public boolean checkQQInstalled() {
        return mShareAPI.isInstall(mContext, SHARE_MEDIA.QQ);
    }

    public boolean checkSinaInstalled() {
        return mShareAPI.isInstall(mContext, SHARE_MEDIA.SINA);
    }

    public void shareWXSmallProgram(SHARE_MEDIA platform, String url, Bitmap logo, String title, String miniProgramId,
                                    String miniProgramPath, String content) {
        try {
            if (logo == null) {
                logo = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_launcher);
            }
            UMImage umImage = new UMImage(mContext, logo);
            UMMin umMin = new UMMin(url);
            umMin.setThumb(umImage);
            umMin.setTitle(title);
            umMin.setDescription(content);
            umMin.setPath(miniProgramPath);
            umMin.setUserName(miniProgramId);
            new ShareAction(mContext)
                .withMedia(umMin)
                .setPlatform(platform)
                .setCallback(shareListener)
                .share();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
