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

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

/**
 * Extends SimpleDialogFragment class of StyledDialogs library.
 *
 * @see <a href="https://github.com/inmite/android-styled-dialogs">inmite - Android Styled Dialogs</a> Required.
 * @see SimpleDialogFragment class to extend.
 */
public class Dialog extends SimpleDialogFragment {
    private static final String TAG = "Dialog";

	

    @Override
    public Builder build(Builder builder) {
        Context context = getActivity().getApplicationContext();
        //String appName = null;
        //try {
        //    appName = (String) context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0));
       // } catch (NameNotFoundException ignored) {}
       
        builder.setTitle(context.getString(R.string.newUpdateAvailable));
        builder.setMessage(getArguments().getString("content"));
        
        builder.setPositiveButton(context.getString(R.string.dialogPositiveButton), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToMarket();
            	goToDownload();
                dismiss();
            }
        });
        /*builder.setNeutralButton(context.getString(R.string.dialogNeutralButton), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        }); */
        // TODO Create "Later" button
        builder.setNegativeButton(context.getString(R.string.dialogNegativeButton), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder;
    }

//    private void goToMarket() {
//        Context context = getActivity().getApplicationContext();
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UpdateChecker.ROOT_PLAY_STORE_DEVICE + context.getPackageName())));
//    }
    private void goToDownload() {
    	Log.d(TAG, "下载apk");
    	Intent intent=new Intent(getActivity().getApplicationContext(),DownloadService.class);
    	intent.putExtra("url", getArguments().getString("url"));
    	getActivity().startService(intent);
//        Context context = getActivity().getApplicationContext();
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UpdateChecker.ROOT_PLAY_STORE_DEVICE + context.getPackageName())));
    }
}
