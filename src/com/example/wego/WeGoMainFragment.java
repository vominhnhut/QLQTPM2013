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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WeGoMainFragment extends Fragment implements OnItemClickListener {
	public static final String TAG = "WeGoMainFragment";

	private ListView mStatusList;
	private LinearLayout mSearchWaitView;
	private ProgressBar loadMorePrg;
	public boolean isShow = true;
	private GoogleMap map;
	private StatusAdapter searchedItemAdapter;
	private boolean stopLoad = false;
	public String searchKey;
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
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
					int lastItem = firstVisibleItem + visibleItemCount;
					if (lastItem >= totalItemCount && totalItemCount > 0) {
						// Check dk
						MainActivity mainActivity = (MainActivity) getActivity();
						if (mainActivity != null
								&& mainActivity.isSearchTaskRunning() == false
								&& stopLoad == false) {
							mainActivity.search(searchKey, false);
						}
						// mainActivity.search(mainActivity.searchKey);
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

		loadMorePrg = (ProgressBar) view.findViewById(R.id.loadmorePrg);

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

	public void showListStatusIfHidden() {
		if (isShow == false) {
			showListStatus();
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

	public void updateSearchList(Boolean isNewSearch,
			ArrayList<DiaDiem> diaDiemList) {
		map.clear();
		hashTable.clear();

		if (diaDiemList == null || diaDiemList.size() == 0) {
			hideListStatus();
		} else if (searchedItemAdapter == null || isNewSearch) {
			searchedItemAdapter = new StatusAdapter(getActivity(), diaDiemList) {

				@Override
				public void OnItemMenuClick(int id, View v) {
					// TODO Auto-generated method stub
					PopupMenu menu = new PopupMenu(getActivity(), v);
					menu.inflate(R.menu.search_item_menu);

					final DiaDiem dd = (DiaDiem) searchedItemAdapter
							.getItem(id);

					menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

						@Override
						public boolean onMenuItemClick(MenuItem item) {
							// TODO Auto-generated method stub
							switch (item.getItemId()) {
							case R.id.search_item_forcusCam:
								LatLng coor = new LatLng(dd.latitude,
										dd.longitude);
								forcusLocation(coor, true);
								break;
							case R.id.search_item_navigate:
								// Navigate will be here
								LatLng sAddr = CurrentLocationHelper
										.getCurrentLocationLatLng(
												getActivity(),
												LocationManager.NETWORK_PROVIDER);
								if(sAddr == null){
									sAddr = CurrentLocationHelper
											.getCurrentLocationLatLng(
													getActivity(),
													LocationManager.GPS_PROVIDER);
								}
								LatLng dAddr = dd.getLatLng();

								CurrentLocationHelper.transferToNavigation(
										sAddr, dAddr, getActivity());

								break;
							default:
								break;
							}
							return false;
						}
					});
					menu.show();
					super.OnItemMenuClick(id, v);
				}

			};

			mStatusList.setAdapter(searchedItemAdapter);
			updateMap();
		} else {
			for (DiaDiem diaDiem : diaDiemList) {
				searchedItemAdapter.addItem(diaDiem);
			}
			Log.e("TAG 1", isNewSearch + " " + diaDiemList.size() + " "
					+ searchedItemAdapter.getCount());
			searchedItemAdapter.notifyDataSetChanged();
			updateMap();
		}

	}

	private void updateMap() {
		addMapPin();
		// showListStatus();
		showListStatusIfHidden();
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

	public void initViewsBeginSearch(boolean isNewSearch) {
		if (isNewSearch) {
			hideListStatusIfShown();
			mSearchWaitView.setVisibility(View.VISIBLE);
			stopLoad = true;
		} else {
			loadMorePrg.setVisibility(View.VISIBLE);
		}
	}

	public void initViewsBeginSearchFinish(boolean isNewSearch) {
		if (isNewSearch) {
			if (searchedItemAdapter != null
					&& searchedItemAdapter.getCount() > 0) {
				// showListStatus();
				showListStatusIfHidden();
			}
			this.stopLoad = false;
			mSearchWaitView.setVisibility(View.GONE);
		} else {
			loadMorePrg.setVisibility(View.GONE);
		}
	}
}
