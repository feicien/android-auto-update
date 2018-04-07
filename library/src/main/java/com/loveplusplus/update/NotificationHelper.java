package com.loveplusplus.update;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * @author feicien (ithcheng@gmail.com)
 * @since 2018-04-07 16:49
 */
public class NotificationHelper extends ContextWrapper {

    private NotificationManager manager;

    private static String CHANNEL_ID = "dxy_app_update";

    private static final int NOTIFICATION_ID = 0;

    public NotificationHelper(Context base) {
        super(base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "应用更新", NotificationManager.IMPORTANCE_LOW);
            mChannel.setDescription("应用有新版本");
            mChannel.enableLights(true); //是否在桌面icon右上角展示小红点
            getManager().createNotificationChannel(mChannel);
        }
    }

    /**
     * Show Notification
     */
    public void showNotification(String content, String apkUrl) {

        Intent myIntent = new Intent(this, DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = getNofity(content)
                .setContentIntent(pendingIntent);

        getManager().notify(NOTIFICATION_ID, builder.build());
    }


    public void updateProgress(int progress) {


        String text = this.getString(R.string.android_auto_update_download_progress, progress);

        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = getNofity(text)
                .setProgress(100, progress, false)
                .setContentIntent(pendingintent);

        getManager().notify(NOTIFICATION_ID, builder.build());
    }

    private NotificationCompat.Builder getNofity(String text) {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setTicker(getString(R.string.android_auto_update_notify_ticker))
                .setContentTitle("应用更新")
                .setContentText(text)
                .setSmallIcon(getSmallIcon())
                .setLargeIcon(getLargeIcon())
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_LOW);

    }

    public void cancel() {
        getManager().cancel(NOTIFICATION_ID);
    }


    private int getSmallIcon() {
        //设置 nofication 的图标 直接读取小米推送配置的图标
        int icon = getResources().getIdentifier("mipush_small_notification", "drawable", getPackageName());
        if (icon == 0) {
            icon = getApplicationInfo().icon;
        }

        return icon;
    }

    private Bitmap getLargeIcon() {
        int bigIcon = getResources().getIdentifier("mipush_notification", "drawable", getPackageName());
        if (bigIcon != 0) {
            return BitmapFactory.decodeResource(getResources(), bigIcon);
        }
        return null;
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}
