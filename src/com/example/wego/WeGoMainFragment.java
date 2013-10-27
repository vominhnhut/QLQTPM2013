package com.example.wego;

import com.example.adapter.StatusAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class WeGoMainFragment extends Fragment implements OnItemClickListener {
	private ListView mStatusList;
	private boolean isShow = true;
	private GoogleMap map;

	private static WeGoMainFragment instanceFragment = null;

	public static WeGoMainFragment instance() {
		if (instanceFragment == null) {
			instanceFragment = new WeGoMainFragment();
		}
		return instanceFragment;
	}

	public WeGoMainFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.main_fragment, container, false);

		//
		mStatusList = (ListView) view.findViewById(R.id.list_status);
		if (mStatusList != null) {
			mStatusList.setAdapter(new StatusAdapter(getActivity()));//
			mStatusList.setOnItemClickListener(this);
		}

		map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.mapView)).getMap();
		map.setMyLocationEnabled(true);
		map.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {
				// TODO Auto-generated method stub
				showHideListStatus();
			}
		});

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	public void showHideListStatus() {
		if (isShow) {

			mStatusList.postDelayed(new Runnable() {
				@Override
				public void run() {
					Animation anim = AnimationUtils.loadAnimation(
							getActivity(), R.anim.slide_out_top_bot);
					anim.setAnimationListener(new AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							mStatusList.setVisibility(View.GONE);
						}
					});
					mStatusList.startAnimation(anim);

				}
			}, 200);
			isShow = false;
		} else {
			mStatusList.postDelayed(new Runnable() {
				@Override
				public void run() {
					Animation anim = AnimationUtils.loadAnimation(
							getActivity(), R.anim.slide_in_bot_top);
					anim.setAnimationListener(new AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							mStatusList.setVisibility(View.VISIBLE);
						}
					});
					mStatusList.startAnimation(anim);

				}
			}, 200);
			isShow = true;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		getActivity().startActivity(new Intent(getActivity(), LocationDetailActivity.class));
	}
}
