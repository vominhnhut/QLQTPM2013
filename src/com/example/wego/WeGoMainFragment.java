package com.example.wego;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

public class WeGoMainFragment extends Fragment {
	private ListView mStatusList;
	private boolean isShow = true;

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
		}
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
					Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_top_bot);
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
					Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_bot_top);
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
}
