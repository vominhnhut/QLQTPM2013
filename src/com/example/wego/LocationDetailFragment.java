package com.example.wego;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LocationDetailFragment extends Fragment {

	private static LocationDetailFragment instanceFragment;
	
	public static LocationDetailFragment instance(){
		if(instanceFragment == null){
			instanceFragment = new LocationDetailFragment();
		}
		return instanceFragment;
	}
	
	public LocationDetailFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.location_detail_fragment, container,
				false);
		
		return view;
	}
}
