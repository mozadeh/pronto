package com.hackathon.pronto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class discountscreenactivity extends FragmentActivity {

	static TextView when, what, where, distance, share,getit;
	ImageButton navigate;
	private static final String GET_OFFER_URL = "http://www.mailivy.com/discountapp/offer.php";
	private static final String STORE_OFFERCLICK_URL = "http://www.mailivy.com/discountapp/offerclick.php";
	private static final String TAG_POSTS = "posts";
	String dboffer, address, lat, lon, expires;
	GoogleMap map;
	long diffInMillies;

	private JSONArray Offers = null;
	ProgressDialog LoadingOffer;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LoadingOffer = new ProgressDialog(this);
		Log.v("test", "before");
		new UpdateOffer().execute();
		Log.v("test", "after");
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discountscreen);
		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"Roboto-BoldCondensed.ttf");
		Typeface myTypefaceitalic = Typeface.createFromAsset(getAssets(),
				"Roboto-CondensedItalic.ttf");
		Typeface myTypefacebold = Typeface.createFromAsset(getAssets(),
				"Roboto-BoldCondensedItalic.ttf");
		int isit = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		// Toast.makeText(getApplicationContext(),
		// "this is my Toast message!!! =)"+isit, Toast.LENGTH_LONG).show();

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		// map.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

		// map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(37.869748,
		// -122.267203)));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(
				37.869748, -122.267203)), 17f));
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		when = (TextView) findViewById(R.id.when);
		what = (TextView) findViewById(R.id.what);
		where = (TextView) findViewById(R.id.where);
		share = (TextView) findViewById(R.id.share);
		getit= (TextView) findViewById(R.id.getit);
		distance = (TextView) findViewById(R.id.distance);
		navigate = (ImageButton) findViewById(R.id.navigate);
		what.setTypeface(myTypeface);
		when.setTypeface(myTypefacebold);
		where.setTypeface(myTypefaceitalic);
		share.setTypeface(myTypefacebold);
		getit.setTypeface(myTypefacebold);
		distance.setTypeface(myTypefaceitalic);

		

		navigate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
						Uri.parse("http://maps.google.com/maps?q=" + lat + ","
								+ lon));
				// if (isAppInstalled("com.google.android.apps.maps")) {
				intent.setClassName("com.google.android.apps.maps",
						"com.google.android.maps.MapsActivity");
				// }
				startActivity(intent);
			}
		});

		share.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Here is the share content body";
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent, "Share via"));
			}
		});
		
		getit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new StoreClick().execute();
				
			}
		});
	}

	public static void setOffer(String offer) {
		what.setText(offer);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	class UpdateOffer extends AsyncTask<String, String, String> {

		public UpdateOffer() {

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			LoadingOffer.setMessage("Opening Offer");
			LoadingOffer.setIndeterminate(false);
			LoadingOffer.setCancelable(true);
			LoadingOffer.show();
		}

		@Override
		protected String doInBackground(String... args) {
			try {
				JSONParser jsonParser = new JSONParser();
				// List<NameValuePair> params = new ArrayList<NameValuePair>();
				// params.add(new BasicNameValuePair("post_id", post_id));
				// params.add(new BasicNameValuePair("message",
				// changed_Message));
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// params.add(new BasicNameValuePair("message", "dude"));
				JSONObject json = jsonParser.makeHttpRequest(GET_OFFER_URL,
						"GET", params);

				Offers = json.getJSONArray(TAG_POSTS);
				JSONObject c = Offers.getJSONObject(0);
				dboffer = c.getString("offer");
				// String expires = c.getString("expires");
				address = c.getString("where");
				lat = c.getString("lat");
				lon = c.getString("lon");
				expires = c.getString("expires");

				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				df.setTimeZone(TimeZone.getTimeZone("PST"));

				Date convertedexpiryDate = new Date();
				try {
					convertedexpiryDate = df.parse(expires);

					Calendar calendar = Calendar.getInstance();
					calendar.setTime(convertedexpiryDate);
					calendar.add(Calendar.HOUR, 7);

					convertedexpiryDate = calendar.getTime();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("time exception", e.toString());
				}

				Date curr_date = new Date();
				df.format(curr_date);
				Log.v("test expiry date", convertedexpiryDate.toString());
				Log.v("test current date", curr_date.toString());

				diffInMillies = convertedexpiryDate.getTime()
						- curr_date.getTime();

				// where.setText(address);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			LoadingOffer.dismiss();
			what.setText(dboffer);
			where.setText(address);
			map.addMarker(new MarkerOptions().position(
					new LatLng(Float.parseFloat(lat), Float.parseFloat(lon)))
					.title("Marker"));

			if (diffInMillies > 0) {
				new CountDownTimer(diffInMillies, 1000) { // adjust the milli
															// seconds here
					public void onTick(long millisUntilFinished) {
						long millis = millisUntilFinished;
						when.setText("Expires in "
								+ String.format(
										"%02d:%02d:%02d",
										TimeUnit.MILLISECONDS.toHours(millis)
												- TimeUnit.DAYS
														.toHours(TimeUnit.MILLISECONDS
																.toDays(millis)),
										TimeUnit.MILLISECONDS.toMinutes(millis)
												- TimeUnit.HOURS
														.toMinutes(TimeUnit.MILLISECONDS
																.toHours(millis)),
										TimeUnit.MILLISECONDS.toSeconds(millis)
												- TimeUnit.MINUTES
														.toSeconds(TimeUnit.MILLISECONDS
																.toMinutes(millis))));
					}

					public void onFinish() {
						when.setText("Discount Expired");
					}
				}.start();
			} else {
				when.setText("Discount Expired");
			}

		}

	}
	
	
	
	
	
	class StoreClick extends AsyncTask<String, String, String> {

		public StoreClick() {

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			LoadingOffer.setMessage("Processing Request");
			LoadingOffer.setIndeterminate(false);
			LoadingOffer.setCancelable(true);
			LoadingOffer.show();
		}

		@Override
		protected String doInBackground(String... args) {
			JSONParser jsonParser = new JSONParser();

			SharedPreferences prefs = getSharedPreferences("Myprefs", MODE_PRIVATE); 
			String name = prefs.getString("name", null);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("offer", what.getText().toString()));
			//Log.v("testi", name+ what.getText().toString());
			JSONObject json=jsonParser.makeHttpRequest(STORE_OFFERCLICK_URL,
					"POST", params);
			try {
				String success = json.getString("success");
				Log.v("testi", success);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.v("testi", "error");
				e.printStackTrace();
			}
			return null;

		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			LoadingOffer.dismiss();
			
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
