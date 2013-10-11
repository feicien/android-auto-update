/*
 * Copyright (C) 2013 Pietro Rampini "Rampo" - Piko Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.loveplusplus.update;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.loveplusplus.update.R;

public class UpdateChecker extends Fragment {

	private static final String LOG_TAG = "UpdateChecker";
	private static final String NOTIFICATION_ICON_RES_ID_KEY = "resId";
	private static final String INT_OF_LAUNCHES_PREF_KEY = "nLaunches";
	private static final String NOTICE_TYPE_KEY = "type";
	private static final String SUCCESSFUL_CHECKS_REQUIRED_KEY = "nChecks";
	private static final String PREFS_FILENAME = "updateChecker";
	private static final int NOTICE_NOTIFICATION = 2;
	private static final int NOTICE_DIALOG = 1;
	private static final String TAG = "UpdateChecker";

	private FragmentActivity mContext;
	private Thread mThread;
	private int mSuccessfulChecksRequired;
	private int mTypeOfNotice;
	private int mNotificationIconResId;

	/**
	 * Show a Dialog if an update is available for download. Callable in a
	 * FragmentActivity. Number of checks after the dialog will be shown:
	 * default, 5
	 * 
	 * @param fragmentActivity
	 *            Required.
	 */
	public static void checkForDialog(FragmentActivity fragmentActivity) {
		FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
		UpdateChecker updateChecker = new UpdateChecker();
		Bundle args = new Bundle();
		args.putInt(NOTICE_TYPE_KEY, NOTICE_DIALOG);
		args.putInt(SUCCESSFUL_CHECKS_REQUIRED_KEY, 5);
		updateChecker.setArguments(args);
		content.add(updateChecker, null).commit();
	}

	/**
	 * Show a Dialog if an update is available for download. Callable in a
	 * FragmentActivity. Specify the number of checks after the dialog will be
	 * shown.
	 * 
	 * @param fragmentActivity
	 *            Required.
	 * @param successfulChecksRequired
	 *            the number of checks after the dialog will be shown.
	 */
	public static void checkForDialog(FragmentActivity fragmentActivity, int successfulChecksRequired) {
		FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
		UpdateChecker updateChecker = new UpdateChecker();
		Bundle args = new Bundle();
		args.putInt(NOTICE_TYPE_KEY, NOTICE_DIALOG);
		args.putInt(SUCCESSFUL_CHECKS_REQUIRED_KEY, successfulChecksRequired);
		updateChecker.setArguments(args);
		content.add(updateChecker, null).commit();
	}

	/**
	 * Show a Notification if an update is available for download. Callable in a
	 * FragmentActivity Number of checks after the notification will be shown:
	 * default, 5
	 * 
	 * @param fragmentActivity
	 *            Required.
	 */
	public static void checkForNotification(FragmentActivity fragmentActivity) {
		FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
		UpdateChecker updateChecker = new UpdateChecker();
		Bundle args = new Bundle();
		args.putInt(NOTICE_TYPE_KEY, NOTICE_NOTIFICATION);
		args.putInt(NOTIFICATION_ICON_RES_ID_KEY, R.drawable.ic_stat_ic_menu_play_store);
		args.putInt(SUCCESSFUL_CHECKS_REQUIRED_KEY, 5);
		updateChecker.setArguments(args);
		content.add(updateChecker, null).commit();
	}

	/**
	 * Show a Notification if an update is available for download. Callable in a
	 * FragmentActivity Specify the number of checks after the notification will
	 * be shown.
	 * 
	 * @param fragmentActivity
	 *            Required.
	 * @param successfulChecksRequired
	 *            the number of checks after the notification will be shown.
	 */
	public static void checkForNotification(FragmentActivity fragmentActivity, int successfulChecksRequired) {
		FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
		UpdateChecker updateChecker = new UpdateChecker();
		Bundle args = new Bundle();
		args.putInt(NOTICE_TYPE_KEY, NOTICE_NOTIFICATION);
		args.putInt(NOTIFICATION_ICON_RES_ID_KEY, R.drawable.ic_stat_ic_menu_play_store);
		args.putInt(SUCCESSFUL_CHECKS_REQUIRED_KEY, successfulChecksRequired);
		updateChecker.setArguments(args);
		content.add(updateChecker, null).commit();
	}

	/**
	 * Show a Notification if an update is available for download. Callable in a
	 * FragmentActivity Specify the number of checks after the notification will
	 * be shown.
	 * 
	 * @param fragmentActivity
	 *            Required.
	 * @param notificationIconResId
	 *            R.drawable.* resource to set to Notification Icon.
	 */
	public static void checkForNotification(int notificationIconResId, FragmentActivity fragmentActivity) {
		FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
		UpdateChecker updateChecker = new UpdateChecker();
		Bundle args = new Bundle();
		args.putInt(NOTICE_TYPE_KEY, NOTICE_NOTIFICATION);
		args.putInt(NOTIFICATION_ICON_RES_ID_KEY, notificationIconResId);
		args.putInt(SUCCESSFUL_CHECKS_REQUIRED_KEY, 5);
		updateChecker.setArguments(args);
		content.add(updateChecker, null).commit();
	}

	/**
	 * Show a Notification if an update is available for download. Set the
	 * notificationIcon Resource Id. Callable in a FragmentActivity Specify the
	 * number of checks after the notification will be shown.
	 * 
	 * @param fragmentActivity
	 *            Required
	 * @param successfulChecksRequired
	 *            the number of checks after the notification will be shown.
	 * @param notificationIconResId
	 *            R.drawable.* resource to set to Notification Icon.
	 */
	public static void checkForNotification(FragmentActivity fragmentActivity, int successfulChecksRequired, int notificationIconResId) {
		FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
		UpdateChecker updateChecker = new UpdateChecker();
		Bundle args = new Bundle();
		args.putInt(NOTICE_TYPE_KEY, NOTICE_NOTIFICATION);
		args.putInt(NOTIFICATION_ICON_RES_ID_KEY, notificationIconResId);
		args.putInt(SUCCESSFUL_CHECKS_REQUIRED_KEY, successfulChecksRequired);
		updateChecker.setArguments(args);
		content.add(updateChecker, null).commit();
	}

	/**
	 * This class is a Fragment. Check for the method you have chosen.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = (FragmentActivity) activity;
		Bundle args = getArguments();
		mTypeOfNotice = args.getInt(NOTICE_TYPE_KEY);
		mSuccessfulChecksRequired = args.getInt(SUCCESSFUL_CHECKS_REQUIRED_KEY);
		mNotificationIconResId = args.getInt(NOTIFICATION_ICON_RES_ID_KEY);
		checkForUpdates();
	}

	/**
	 * Heart of the library. Check if an update is available for download
	 * parsing the desktop Play Store page of the app
	 */
	private void checkForUpdates() {
		mThread = new Thread() {
			@Override
			public void run() {
				if (isNetworkAvailable(mContext)) {

					HttpRequest request = HttpRequest.post("http://192.168.1.115:8080/mlhwggl/api/app/update");
					String json = request.body();
					finalStep(json);
				}
			}

		};
		mThread.start();
	}

	/**
	 * If the version dowloadable from the Play Store is different from the
	 * versionName installed notify it to the user.
	 * 
	 * @param versionDownloadable
	 *            String to compare to versionName of the app.
	 * @see UpdateChecker#CheckForDialog(android.support.v4.app.FragmentActivity)
	 * @see UpdateChecker#CheckForNotification(android.support.v4.app.FragmentActivity)
	 */
	private void finalStep(String json) {
		mThread.interrupt();
		Looper.prepare();
		try {

			// if (!versionDownloadable.equals()) { // New
			// // Available
			// if (iDontWantToBeTooMuchInvasive(versionDownloadable)) {
			// if (mTypeOfNotice == NOTICE_NOTIFICATION) {
			// showNotification();
			// } else if (mTypeOfNotice == NOTICE_DIALOG) {
			// showDialog();
			// }
			// }
			// } else {
			//
			// } // No new update available

			Gson gson = new Gson();
			AppVersionInfo info = gson.fromJson(json, AppVersionInfo.class);
			int versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;

			if (info.versionCode > versionCode) {
				if (mTypeOfNotice == NOTICE_NOTIFICATION) {
					showNotification(info);
				} else if (mTypeOfNotice == NOTICE_DIALOG) {
					showDialog(info);
				}
			} else {
				Log.d(TAG, "最新版本");
			}

		} catch (PackageManager.NameNotFoundException ignored) {
		}
	}

	/**
	 * Show dialog
	 * 
	 * @see Dialog#show(android.support.v4.app.FragmentActivity)
	 */
	public void showDialog(AppVersionInfo info) {
		// Dialog.show(mContext,info);
		 Dialog d=new Dialog();
         
         Bundle args=new Bundle();
         args.putString("content", info.updateMessage);
         args.putString("url", info.url);
			d.setArguments(args);
         d.show(mContext.getSupportFragmentManager(), null);
	}

	/**
	 * Show Notification
	 * 
	 * @param info
	 * 
	 * @see Notification#show(android.content.Context, int)
	 */
	public void showNotification(AppVersionInfo info) {
		android.app.Notification noti;
		Intent myIntent = new Intent(mContext,DownloadService.class);
		myIntent.putExtra("url", info.url);
		PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		int smallIcon = 0;
		if (mNotificationIconResId == 0) {
			smallIcon = R.drawable.ic_stat_ic_menu_play_store;
		} else {
			smallIcon = mNotificationIconResId;
		}
		noti = new NotificationCompat.Builder(mContext).setTicker(getString(R.string.newUpdateAvailable))
				.setContentTitle(getString(R.string.newUpdateAvailable)).setContentText(info.updateMessage).setSmallIcon(smallIcon)
				.setContentIntent(pendingIntent).build();

		noti.flags = android.app.Notification.FLAG_AUTO_CANCEL;
		NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, noti);
	}

	/**
	 * Log connection error
	 */
	public void logConnectionError() {
		Log.e(LOG_TAG, "Cannot connect to the Internet!");
	}

	/**
	 * Check if a network available
	 */
	public static boolean isNetworkAvailable(Context context) {
		boolean connected = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if (ni != null) {
				connected = ni.isConnected();
			}
		}
		return connected;
	}

	/**
	 * Show the Dialog/Notification only if it is the first time or divisible
	 * for 5.
	 */
	private boolean iDontWantToBeTooMuchInvasive(String versionDownloadable) {
		String prefKey = INT_OF_LAUNCHES_PREF_KEY + versionDownloadable;
		SharedPreferences prefs = mContext.getSharedPreferences(PREFS_FILENAME, 0);
		int mChecksMade = prefs.getInt(prefKey, 0);
		if (mChecksMade % mSuccessfulChecksRequired == 0 || mChecksMade == 0) {
			saveNumberOfChecksForUpdatedVersion(versionDownloadable, mChecksMade);
			return true;
		} else {
			saveNumberOfChecksForUpdatedVersion(versionDownloadable, mChecksMade);
			return false;
		}
	}

	/**
	 * Update number of checks for the versionName of the version downloadable
	 * from Play Store.
	 */
	private void saveNumberOfChecksForUpdatedVersion(String versionDownloadable, int mChecksMade) {
		mChecksMade++;
		SharedPreferences prefs = mContext.getSharedPreferences(PREFS_FILENAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(INT_OF_LAUNCHES_PREF_KEY + versionDownloadable, mChecksMade);
		editor.commit();
	}
}
