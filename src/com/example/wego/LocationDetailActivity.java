package com.example.wego;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.Object.BinhLuan;
import com.example.adapter.LocationDetailPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class LocationDetailActivity extends FragmentActivity {

	ViewPager pager;
	LocationDetailPagerAdapter pagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_detail);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		pager = (ViewPager) findViewById(R.id.pager);
		
		ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
		
		fragmentList.add(LocationDetailFragment.instance());
		fragmentList.add(LocationDetailCommentsFragment.instance());
		
		pagerAdapter = new LocationDetailPagerAdapter(getSupportFragmentManager(), fragmentList);
		pager.setAdapter(pagerAdapter);
		
		ArrayList<BinhLuan> bls = new ArrayList<BinhLuan>();
		
		for(int i=0 ; i< 10 ; i++){
			BinhLuan newBinhLuan = new BinhLuan();
			newBinhLuan.setNoiDung("This is dumb text of " + i);
			newBinhLuan.setTenNguoiDang("User " + i );
			newBinhLuan.setThoiGianDang(Calendar.getInstance().getTime()); 
			
			bls.add(newBinhLuan);
		}
		LocationDetailCommentsFragment.instance().setCommentList(bls);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.location_detail, menu);
		return false;
	}

}
