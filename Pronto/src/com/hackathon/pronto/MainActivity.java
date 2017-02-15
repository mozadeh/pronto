package com.hackathon.pronto;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity implements LocationListener{
	// label to display gcm messages
	TextView lblMessage;
	Controller aController;
	Intent intent;
	protected LocationManager locationManager;
	protected LocationListener locationListener;

	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;

	public static String name,email,lat,lon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try{
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
		final LocationManager mlocManager = (LocationManager) this
	            .getSystemService(Context.LOCATION_SERVICE);
	    final Location currentGeoLocation = mlocManager
	            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	    
	    lat = currentGeoLocation.getLatitude()+"";
	    Log.v("lat", lat);
	    lon = currentGeoLocation.getLongitude()+"";
	    Log.v("latlon", lon);
		}
		catch (Exception e){
			lat = "44";
		    Log.v("lat", lat);
		    lon = "44";
		}


		// Get Global Controller Class object (see application tag in
		// AndroidManifest.xml)
		aController = (Controller) getApplicationContext();

		// Check if Internet present
		if (!aController.isConnectingToInternet()) {

			// Internet Connection is not present
			aController.showAlertDialog(MainActivity.this,
					"Internet Connection Error",
					"Please connect to Internet connection", false);
			// stop executing code by return
			return;
		}

		// Getting name, email from intent
		Intent i = getIntent();

		name = i.getStringExtra("name");
		email = i.getStringExtra("email");
		
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest permissions was properly set
		GCMRegistrar.checkManifest(this);

		lblMessage = (TextView) findViewById(R.id.lblMessage);

		// Register custom Broadcast receiver to show messages on activity

		registerReceiver(mHandleMessageReceiver, new IntentFilter(Config.DISPLAY_MESSAGE_ACTION));
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (regId.equals("")) {

			// Register with GCM
			GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);

		} else {

			// Device is already registered on GCM Server
			if (GCMRegistrar.isRegisteredOnServer(this)) {

				// Skips registration.
				// Toast.makeText(getApplicationContext(),
				// "Already registered with GCM Server",
				// Toast.LENGTH_LONG).show();

			} else {

				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.

				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {

						// Register on our server
						// On server creates a new user
						aController.register(context, name, email, regId,lat,lon);

						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};

				// execute AsyncTask
				mRegisterTask.execute(null, null, null);
			}
		}

		intent = new Intent(this, discountscreenactivity.class);
		startActivity(intent);

	}

	// Create a broadcast receiver to get message and show on screen
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {

			String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);

			// Waking up mobile if it is sleeping
			aController.acquireWakeLock(getApplicationContext());

			// Display message on the screen
			//lblMessage.append(newMessage + "\n");
			//intent = new Intent(getApplicationContext(), discountscreenactivity.class);
			//startActivity(intent);
			//Log.v("yoyo", "here");
			//intent = new Intent(this, discountscreenactivity.class);
			//startActivity(intent);
			//Toast.makeText(getApplicationContext(),newMessage, Toast.LENGTH_LONG).show();
			
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

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		lat=location.getLatitude()+"";
		lon=location.getLongitude()+"";
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

}
