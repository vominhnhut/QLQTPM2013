package com.example.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class LocationDetailPagerAdapter extends FragmentPagerAdapter {

	 private List<Fragment> fragments;

	  public LocationDetailPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
	    super(fm);
	    this.fragments = fragments;
	  }
	  @Override 
	  public Fragment getItem(int position) {
	    return this.fragments.get(position);
	  }

	  @Override
	  public int getCount() {
	    return this.fragments.size();
	  }
	  
	  public void replaceItem(int position, Fragment newItem){
		  this.fragments.set(position, newItem);
	  }
}
