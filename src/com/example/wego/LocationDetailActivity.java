package com.example.wego;

import java.util.ArrayList;


import com.example.Object.BinhLuan;
import com.example.Object.DiaDiem;
import com.example.adapter.LocationDetailPagerAdapter;
import com.example.ultils.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.TextView;

public class LocationDetailActivity extends FragmentActivity {

	private GoogleMap map;
	// private ImageView locationType;
	private TextView locationName;
	private TextView locationAddress;

	private DiaDiem diaDiem;

	private ViewPager pager;
	private LocationDetailPagerAdapter pagerAdapter;

	private static LocationDetailActivity instance = null;

	public LocationDetailActivity instance() {
		if (instance == null) {
			instance = new LocationDetailActivity();
		}
		return instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_detail);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(R.string.app_name);

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.miniMap)).getMap();
		map.getUiSettings().setZoomControlsEnabled(false);
		map.getUiSettings().setZoomGesturesEnabled(false);
		// map.getUiSettings().setScrollGesturesEnabled(false);

		locationName = (TextView) findViewById(R.id.locationName);
		locationAddress = (TextView) findViewById(R.id.locationAddress);

		pager = (ViewPager) findViewById(R.id.pager);

		ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

		fragmentList.add(LocationDetailFragment.instance());
		fragmentList.add(LocationDetailCommentsFragment.instance());

		pagerAdapter = new LocationDetailPagerAdapter(
				getSupportFragmentManager(), fragmentList);
		pager.setAdapter(pagerAdapter);

		getDiaDiemFromBundle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location_detail, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void getDiaDiemFromBundle() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			this.diaDiem = (DiaDiem) bundle
					.getSerializable(Constants.DIADIEM_KEY);
			if (diaDiem != null) {
				setViewValue();
			}
		}
	}

	private void setViewValue() {
		if (diaDiem != null) {
			setTitle(diaDiem.ten);

			locationName.setText(diaDiem.ten);
			locationAddress.setText(diaDiem.diaChi);

			CameraUpdate center = CameraUpdateFactory.newLatLng(diaDiem
					.getLatLng());
			CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
			map.moveCamera(center);
			map.animateCamera(zoom);
			map.addMarker(new MarkerOptions().position(diaDiem.getLatLng()));

		}
	}

	public DiaDiem getDiaDiem() {
		return this.diaDiem;
	}
	
	public void updateCommentList(ArrayList<BinhLuan> listBinhLuan){
		this.diaDiem.danhSachBinhLuan = listBinhLuan;
	}
}
