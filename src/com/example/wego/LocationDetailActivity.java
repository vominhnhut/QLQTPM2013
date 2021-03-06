package com.example.wego;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.example.Object.BinhLuan;
import com.example.Object.DiaDiem;
import com.example.adapter.LocationDetailPagerAdapter;
import com.example.clientmanager.ClientManager;
import com.example.clientmanager.ResponsedResult;
import com.example.location_manager.CurrentLocationHelper;
import com.example.ultils.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocationDetailActivity extends FragmentActivity {

	private GoogleMap map;
	// private ImageView locationType;
	private TextView locationName;
	private TextView locationAddress;

	private DiaDiem diaDiem;

	private ViewPager pager;
	private LocationDetailPagerAdapter pagerAdapter;

	private static LocationDetailActivity instance = null;

	// hoaphat
	private Button likeBtn;
	private Button saveBtn;

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
		map.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				// TODO Auto-generated method stub
				LatLng sAddr = CurrentLocationHelper
						.getCurrentLocationLatLng(
								getApplicationContext(),
								LocationManager.NETWORK_PROVIDER);
				if(sAddr == null){
					sAddr = CurrentLocationHelper
							.getCurrentLocationLatLng(
									getApplicationContext(),
									LocationManager.GPS_PROVIDER);
				}
				
				LatLng dAddr = diaDiem.getLatLng();

				CurrentLocationHelper.transferToNavigation(sAddr,
						dAddr, LocationDetailActivity.this);
			}
		});
		// map.getUiSettings().setScrollGesturesEnabled(false);

		likeBtn = (Button) findViewById(R.id.like_btn);
		saveBtn = (Button) findViewById(R.id.save_btn);

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
		new LoadLocationAsynctask().execute();

		likeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (diaDiem.isLiked == false) {
					new LikeOrUnlikeLocationAsynctask().execute(true);
				} else if (diaDiem.isLiked = true) {
					new LikeOrUnlikeLocationAsynctask().execute(false);
				}
			}
		});

		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (diaDiem.isSaved == false) {
					new SaveLocationAsynctask().execute();
				} else if (diaDiem.isSaved = true) {
					new UnSaveLocationAsynctask().execute();
				}
			}
		});
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

			setLikeButtonState(diaDiem.isLiked);
			setSaveButtonState(diaDiem.isSaved);

		}
	}

	public DiaDiem getDiaDiem() {
		return this.diaDiem;
	}

	public void updateCommentList(ArrayList<BinhLuan> listBinhLuan) {
		this.diaDiem.danhSachBinhLuan = listBinhLuan;
	}

	private void setLikeButtonState(boolean isLiked) {
		if (isLiked) {
			likeBtn.setTextColor(Color.WHITE);
			likeBtn.setBackgroundResource(R.drawable.hited_button_selector);
			likeBtn.setText(R.string.liked_text);
		} else {
			likeBtn.setTextColor(Color.BLACK);
			likeBtn.setBackgroundResource(R.drawable.hit_button_selector);
			likeBtn.setText(R.string.like_text);
		}
	}

	private void setSaveButtonState(boolean isLiked) {
		if (isLiked) {
			saveBtn.setTextColor(Color.WHITE);
			saveBtn.setBackgroundResource(R.drawable.hited_button_selector);
			saveBtn.setText(R.string.saved_text);
		} else {
			saveBtn.setTextColor(Color.BLACK);
			saveBtn.setBackgroundResource(R.drawable.hit_button_selector);
			saveBtn.setText(R.string.save_text);
		}
	}

	public class LoadLocationAsynctask extends
			AsyncTask<String, Integer, ResponsedResult> {
		ResponsedResult resultLike = null;
		ResponsedResult resultFavourite = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			likeBtn.setEnabled(false);
		}

		@Override
		protected ResponsedResult doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
				resultLike = ClientManager.RequestToGetLikeOrNotLikeDiaDiem(
						Constants.LOGINUSER_TOKEN, diaDiem.id);
				resultFavourite = ClientManager
						.RequestToGetSaveOrNotSaveDiaDiem(
								Constants.LOGINUSER_TOKEN, diaDiem.id);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(ResponsedResult result) {
			// TODO Auto-generated method stub
			likeBtn.setEnabled(true);
			saveBtn.setEnabled(true);

			if (resultLike != null) {
				if (resultLike.success) {
					boolean liked = (Boolean) resultLike.content2;
					diaDiem.isLiked = liked;
					setLikeButtonState(liked);
				} else {
					Toast.makeText(getApplicationContext(),
							"Get liked: " + resultLike.content,
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "Get liked: null",
						Toast.LENGTH_LONG).show();
			}

			if (resultFavourite != null) {
				if (resultFavourite.success) {
					boolean saved = (Boolean) resultFavourite.content2;
					diaDiem.isSaved = saved;
					setSaveButtonState(saved);
				} else {
					Toast.makeText(getApplicationContext(),
							"Get saved: " + resultFavourite.content,
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "Get saved: null",
						Toast.LENGTH_LONG).show();
			}

			LocationDetailFragment.instance().updateDiaDiem();
			
			super.onPostExecute(result);
		}
	}

	public class LikeOrUnlikeLocationAsynctask extends
			AsyncTask<Boolean, Integer, ResponsedResult> {
		boolean like = false;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			likeBtn.setEnabled(false);

		}

		@Override
		protected ResponsedResult doInBackground(Boolean... params) {
			// TODO Auto-generated method stub
			ResponsedResult result = null;
			like = params[0];

			try {
				result = ClientManager.RequestToLikeOrUnlikeDiaDiem(
						Constants.LOGINUSER_TOKEN, diaDiem.id, like);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(ResponsedResult result) {
			// TODO Auto-generated method stub
			likeBtn.setEnabled(true);

			if (result != null) {

				if (result.success) {
					setLikeButtonState(like);
					diaDiem.isLiked = like;
					if(like == false){
						diaDiem.diemDanhGia --;
					} else {
						diaDiem.diemDanhGia ++;
					}
					LocationDetailFragment.instance().updateDiaDiem();
				} else {
					// like thanh cong nhung sv tra ve success false. code mang
					// tinh test thu.
					setLikeButtonState(like);
					diaDiem.isLiked = like;
					Toast.makeText(getApplicationContext(),
							like + " - " + result.content, Toast.LENGTH_LONG)
							.show();
				}

			} else {

				Toast.makeText(getApplicationContext(), "null",
						Toast.LENGTH_LONG).show();
			}

			super.onPostExecute(result);
		}
	}

	public class SaveLocationAsynctask extends
			AsyncTask<Boolean, Integer, ResponsedResult> {

		@Override
		protected ResponsedResult doInBackground(Boolean... params) {
			// TODO Auto-generated method stub

			ResponsedResult result = null;
			try {
				result = ClientManager.RequestToAddFavouriteDiaDiem(
						Constants.LOGINUSER_TOKEN, diaDiem.id);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			saveBtn.setEnabled(false);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ResponsedResult result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			saveBtn.setEnabled(true);

			if (result != null) {
				if (result.success) {
					setSaveButtonState(true);
					diaDiem.isSaved = true;
				} else {
					Toast.makeText(getApplicationContext(), result.content,
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "null",
						Toast.LENGTH_LONG).show();
			}
		}

	}

	public class UnSaveLocationAsynctask extends
			AsyncTask<Boolean, Integer, ResponsedResult> {

		@Override
		protected ResponsedResult doInBackground(Boolean... params) {
			// TODO Auto-generated method stub
			ResponsedResult result = null;
			try {
				result = ClientManager.RequestToRemoveFavouriteDiaDiem(
						Constants.LOGINUSER_TOKEN, diaDiem.id);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			saveBtn.setEnabled(false);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ResponsedResult result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			saveBtn.setEnabled(true);

			if (result != null) {
				if (result.success) {
					setSaveButtonState(false);
					diaDiem.isSaved = false;
				} else {
					Toast.makeText(getApplicationContext(), result.content,
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "null",
						Toast.LENGTH_LONG).show();
			}

		}

	}
}
