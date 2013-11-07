package com.example.wego;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.Object.DiaDiem;
import com.example.adapter.StatusAdapter;
import com.example.ultils.Constants;
import com.google.android.gms.internal.bu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	private StatusAdapter searchedItemAdapter;
	// private LinearLayout mapLayout;
	private Hashtable<Marker, DiaDiem> hashTable;

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
		hashTable = new Hashtable<Marker, DiaDiem>();
		//
		mStatusList = (ListView) view.findViewById(R.id.list_status);
		if (mStatusList != null) {
			mStatusList.setAdapter(new StatusAdapter(getActivity(),
					new ArrayList<DiaDiem>()));//
			mStatusList.setOnItemClickListener(this);
		}

		// mapLayout = (LinearLayout) view.findViewById(R.id.mapLayout);

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
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub
				if(hashTable!=null){
					toDetailActivity(hashTable.get(marker));
				}
			}
		});

		MainActivity main = (MainActivity) getActivity();

		ArrayList<DiaDiem> diaDiem = main.getSearchedItems();

		this.searchedItemAdapter = new StatusAdapter(getActivity(), diaDiem);

		Log.e("Log", diaDiem.size() + "");

		mStatusList.setAdapter(this.searchedItemAdapter);
		addMapPin();
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
							mStatusList.setVisibility(View.GONE);
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub

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
					Animation scrollAnim = AnimationUtils.loadAnimation(
							getActivity(), R.anim.slide_in_bot_top);
					scrollAnim.setAnimationListener(new AnimationListener() {

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
					mStatusList.startAnimation(scrollAnim);

				}
			}, 200);
			isShow = true;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		DiaDiem diaDiem = (DiaDiem) this.searchedItemAdapter.getItem(arg2);

		toDetailActivity(diaDiem);
	}

	public DiaDiem requestFullLocation(DiaDiem diaDiem) {
		return diaDiem;
	}

	private void addMapPin() {
		if (this.searchedItemAdapter != null) {
			for (int i = 0; i < this.searchedItemAdapter.getCount(); i++) {
				DiaDiem diaDiem = (DiaDiem) this.searchedItemAdapter.getItem(i);
				Marker key = map.addMarker(new MarkerOptions()
						.position(diaDiem.getLatLng()).title(diaDiem.ten)
						.snippet(diaDiem.diaChi));

				hashTable.put(key, diaDiem);
			}
		}
		
		//forcusSearchItems();
	}

	private void toDetailActivity(DiaDiem diaDiem) {
		requestFullLocation(diaDiem);

		Intent intent = new Intent(getActivity(), LocationDetailActivity.class);
		if (diaDiem != null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(Constants.DIADIEM_KEY, diaDiem);
			intent.putExtras(bundle);
		}
		// getActivity().startActivity(intent);
		startActivity(intent);
	}
	
	private void forcusSearchItems(){
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		for (int i = 0; i < this.searchedItemAdapter.getCount(); i++) {
			DiaDiem diaDiem = (DiaDiem) this.searchedItemAdapter.getItem(i);
			builder.include(diaDiem.getLatLng());
		}
		CameraUpdate camUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(),10);
		map.animateCamera(camUpdate);
	}
}
