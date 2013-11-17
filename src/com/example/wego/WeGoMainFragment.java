package com.example.wego;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.Object.DiaDiem;
import com.example.adapter.StatusAdapter;
import com.example.location_manager.CurrentLocationHelper;
import com.example.ultils.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class WeGoMainFragment extends Fragment implements OnItemClickListener {
	public static final String TAG = "WeGoMainFragment";

	private ListView mStatusList;
	private LinearLayout mSearchWaitView;
	public boolean isShow = true;
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
			mStatusList.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
					int lastItem = firstVisibleItem + visibleItemCount;
					if (lastItem >= totalItemCount && totalItemCount >0) {
						//Code here
						//
					}
				}
			});
		}
		showHideListStatus();
		// mapLayout = (LinearLayout) view.findViewById(R.id.mapLayout);

		map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.mapView)).getMap();
		map.setMyLocationEnabled(true);
		map.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {
				// TODO Auto-generated method stub
				if (searchedItemAdapter != null
						&& searchedItemAdapter.getCount() > 0) {
					showHideListStatus();
				} else {
					Toast.makeText(getActivity(),
							getString(R.string.no_search_item_text),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub
				if (hashTable != null) {
					toDetailActivity(hashTable.get(marker));
				}
			}
		});

		mSearchWaitView = (LinearLayout) view
				.findViewById(R.id.search_wait_view);

		zoomToLocation(10, false);
		forcusCurrentLocation(true);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	public void showHideListStatus() {
		if (isShow) {
			hideListStatus();
		} else {
			showListStatus();
		}
	}

	public void showListStatus() {
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
		}, 0);
		isShow = true;
	}

	public void hideListStatus() {
		mStatusList.postDelayed(new Runnable() {
			@Override
			public void run() {
				Animation anim = AnimationUtils.loadAnimation(getActivity(),
						R.anim.slide_out_top_bot);
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
		}, 0);

		isShow = false;
	}

	public void hideListStatusIfShown() {
		if (isShow) {
			hideListStatus();
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

		// forcusSearchItems();
	}

	private void toDetailActivity(DiaDiem diaDiem) {
		requestFullLocation(diaDiem);

		Intent intent = new Intent(getActivity(), LocationDetailActivity.class);
		if (diaDiem != null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(Constants.DIADIEM_KEY, diaDiem);
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	private void forcusSearchItems() {
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		for (int i = 0; i < this.searchedItemAdapter.getCount(); i++) {
			DiaDiem diaDiem = (DiaDiem) this.searchedItemAdapter.getItem(i);
			builder.include(diaDiem.getLatLng());
		}
		CameraUpdate camUpdate = CameraUpdateFactory.newLatLngBounds(
				builder.build(), 0);
		map.animateCamera(camUpdate);
	}

	public void updateSearchList(ArrayList<DiaDiem> diaDiemList) {
		map.clear();
		if (diaDiemList == null || diaDiemList.size() == 0) {
			hideListStatus();
		} else {
			searchedItemAdapter = new StatusAdapter(getActivity(), diaDiemList);
			mStatusList.setAdapter(searchedItemAdapter);
			updateMap();
		}
	}

	private void updateMap() {
		addMapPin();
		showListStatus();
		forcusSearchItems();
	}

	private void forcusCurrentLocation(boolean animation) {
		LatLng currentLocation = CurrentLocationHelper
				.getCurrentLocationLatLng(getActivity(),
						LocationManager.NETWORK_PROVIDER);
		if (currentLocation != null) {
			forcusLocation(currentLocation, animation);
		}
	}

	private void forcusLocation(LatLng location, boolean animation) {
		CameraUpdate camUpdate = CameraUpdateFactory.newLatLng(location);
		if (animation) {
			map.animateCamera(camUpdate);
		} else {
			map.moveCamera(camUpdate);
		}
	}

	private void zoomToLocation(int zoomLevel, boolean animation) {
		CameraUpdate camUpdate = CameraUpdateFactory.zoomTo(zoomLevel);
		if (animation) {
			map.animateCamera(camUpdate);
		} else {
			map.moveCamera(camUpdate);
		}
	}

	public void initViewsBeginSearch() {
		hideListStatusIfShown();
		mSearchWaitView.setVisibility(View.VISIBLE);
	}

	public void initViewsBeginSearchFinish() {
		if (searchedItemAdapter != null && searchedItemAdapter.getCount() > 0) {
			showListStatus();
		}
		mSearchWaitView.setVisibility(View.GONE);
	}
}
