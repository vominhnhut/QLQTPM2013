package com.example.location_manager;

import com.example.wego.R;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class CurrentLocationHelper {

	private static Location lastLocation;

	public static LocationListener locationListener = new LocationListener() {

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

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			lastLocation = location;
		}
	};

	public static Location getCurrentLocation(Context context,
			String locationManagerSource) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		if (locationManager != null) {
			locationManager.requestLocationUpdates(locationManagerSource, 0, 0,
					locationListener);
			lastLocation = locationManager
					.getLastKnownLocation(locationManagerSource);

			return lastLocation;
		}
		return null;
	}

	public static LatLng getCurrentLocationLatLng(Context context,
			String locationManagerSource) {
		Location location = getCurrentLocation(context, locationManagerSource);
		if (location == null) {
			return null;
		}
		return new LatLng(location.getLatitude(), location.getLongitude());
	}

	public static void transferToNavigation(LatLng saddr, LatLng daddr,
			Activity callerActivity) {
		if (saddr != null && daddr != null && callerActivity != null) {
			String navigationUrl = "http://maps.google.com/maps?saddr="
					+ saddr.latitude + "," + saddr.longitude + "&daddr="
					+ daddr.latitude + "," + daddr.longitude;

			Intent navIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(navigationUrl));
			callerActivity.startActivity(navIntent);
		} else {
			Toast.makeText(callerActivity, R.string.fail_to_navigate,
					Toast.LENGTH_SHORT).show();
		}
	}
}
