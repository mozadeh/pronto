package com.hackathon.pronto;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class RegisterActivity extends Activity {

	// UI elements
	EditText txtName;
	EditText txtEmail;

	// Register button
	Button btnRegister;

	TextView lblMessage, welcome;
	Controller aController;

	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;

	public static String name, email;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"Roboto-BoldCondensed.ttf");
		Typeface myTypefaceitalic = Typeface.createFromAsset(getAssets(),
				"Roboto-CondensedItalic.ttf");

		welcome = (TextView) findViewById(R.id.welcome);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		welcome.setTypeface(myTypefaceitalic);
		btnRegister.setTypeface(myTypeface);

		// Get Global Controller Class object (see application tag in
		// AndroidManifest.xml)
		final Controller aController = (Controller) getApplicationContext();

		// Check if Internet Connection present
		if (!aController.isConnectingToInternet()) {

			// Internet Connection is not present
			aController.showAlertDialog(RegisterActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);

			// stop executing code by return
			return;
		}

		// Check if GCM configuration is set
		if (Config.YOUR_SERVER_URL == null || Config.GOOGLE_SENDER_ID == null
				|| Config.YOUR_SERVER_URL.length() == 0
				|| Config.GOOGLE_SENDER_ID.length() == 0) {

			// GCM sernder id / server url is missing
			aController.showAlertDialog(RegisterActivity.this,
					"Configuration Error!",
					"Please set your Server URL and GCM Sender ID", false);

			// stop executing code by return
			return;
		}

		txtName = (EditText) findViewById(R.id.txtName);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		if (GCMRegistrar.isRegisteredOnServer(RegisterActivity.this)) {

			// Skips registration.
			// Toast.makeText(getApplicationContext(),"Already registered with GCM Server",Toast.LENGTH_LONG).show();
			Intent intent1 = new Intent(RegisterActivity.this,
					discountscreenactivity.class);
			startActivity(intent1);
		}

		// Click event on Register button
		btnRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get data from EditText
				name = txtName.getText().toString();
				email = txtEmail.getText().toString();
				if (email.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter you name", Toast.LENGTH_LONG).show();
				} else {
					// Check if user filled the form
					
					SharedPreferences.Editor editor = getSharedPreferences("Myprefs", MODE_PRIVATE).edit();
					 editor.putString("name", email);
					 editor.commit();
					
					
					
					
					if (name.trim().length() > 0 && email.trim().length() > 0) {

						// Launch Main Activity
						Intent i = new Intent(getApplicationContext(),
								MainActivity.class);

						// Registering user on our server
						// Sending registraiton details to MainActivity
						i.putExtra("name", name);
						i.putExtra("email", email);
						startActivity(i);
						finish();

					}

					else {

						// user doen't filled that data
						aController.showAlertDialog(RegisterActivity.this,
								"Registration Error!",
								"Please enter your details", false);
					}
				}
			}
		});
	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String newMessage = intent.getExtras().getString(
					Config.EXTRA_MESSAGE);

			// Waking up mobile if it is sleeping
			aController.acquireWakeLock(getApplicationContext());

			// Display message on the screen
			Log.v("wtf", "broadcastreceiver");
			lblMessage.append(newMessage + "\n");

			// Toast.makeText(getApplicationContext(),"Got Message: " +
			// newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			aController.releaseWakeLock();
		}
	};

	@Override
	protected void onDestroy() {
		// Cancel AsyncTask
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			// Unregister Broadcast Receiver
			unregisterReceiver(mHandleMessageReceiver);

			// Clear internal resources.
			GCMRegistrar.onDestroy(this);

		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

}
