package com.loveplusplus.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.loveplusplus.update.R;

public class DownloadService extends IntentService {
	//10-10 19:14:32.618: D/DownloadService(1926): 测试缓存：41234 32kb
	//10-10 19:16:10.892: D/DownloadService(2069): 测试缓存：41170 1kb
	//10-10 19:18:21.352: D/DownloadService(2253): 测试缓存：39899 10kb

	private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K
	private static final String TAG = "DownloadService";
	private NotificationManager mNotifyManager;
	private Builder mBuilder;

	public DownloadService() {
		super("DownloadService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle("Picture Download").setSmallIcon(R.drawable.ic_launcher);
		String url = intent.getStringExtra("url");
		FileOutputStream out = null;
		try {
			HttpRequest request = HttpRequest.get(url);
			if (request.ok()) {
				long bytetotal = request.contentLength();
				long bytesum = 0;
				int byteread = 0;
				InputStream in = request.stream();
				File dir = FileUtils.getExternalCacheDir(this);
				File apkFile=new File(dir, "xxx.apk");
				out = new FileOutputStream(apkFile);
				//测试buffer 
				long startTime = System.currentTimeMillis();
				byte[] buffer = new byte[BUFFER_SIZE];

				int oldProgress=0;
				
				while ((byteread = in.read(buffer)) != -1) {
					bytesum += byteread;
					out.write(buffer, 0, byteread);
					
					int progress=(int)(bytesum*100L/bytetotal);
					// 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿 
					if(progress!=oldProgress){
						updateProgress(progress);
					}
					oldProgress=progress;
				}
				long elapsedTime = System.currentTimeMillis() - startTime;
				Log.d(TAG, "测试缓存："+elapsedTime);
				// 下载完成
				mBuilder.setContentText("下载完成，点击安装").setProgress(0, 0, false);
				 
				Intent myIntent = new Intent(Intent.ACTION_VIEW).setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent, 0);
				
				mBuilder.setContentIntent(pendingIntent);
				Notification noti = mBuilder.build();
				noti.flags=android.app.Notification.FLAG_AUTO_CANCEL;
				mNotifyManager.notify(0, noti);

			} else {
				Log.e(TAG, "url error");
			}
		} catch (Exception e) {
			Log.e(TAG, "yyy", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void updateProgress(int progress) {
		mBuilder.setContentText("正在下载:"+progress+"%").setProgress(100, progress, false);
		mNotifyManager.notify(0, mBuilder.build());
	}

}
