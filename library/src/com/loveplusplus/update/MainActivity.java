package com.loveplusplus.update;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		 UpdateChecker.checkForDialog(this);
		// UpdateChecker.checkForNotification(this);
		//UpdateChecker.checkForNotification(R.drawable.ic_launcher, this);

	}

}
