package com.loveplusplus.update.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.loveplusplus.update.UpdateChecker;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn1 = (Button) findViewById(R.id.button1);
		Button btn2 = (Button) findViewById(R.id.button2);

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UpdateChecker.checkForDialog(MainActivity.this);
			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UpdateChecker.checkForNotification(MainActivity.this);
			}
		});

	}

}
