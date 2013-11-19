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
import com.example.ultils.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.Color;
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

				// Server chua cai dat day du

				// if (diaDiem.isSaved == false) {
				// new SaveOrUnSaveLocationAsynctask().execute(true);
				// } else if (diaDiem.isSaved = true) {
				// new SaveOrUnSaveLocationAsynctask().execute(false);
				// }
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

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			likeBtn.setEnabled(false);
		}

		@Override
		protected ResponsedResult doInBackground(String... params) {
			// TODO Auto-generated method stub
			ResponsedResult result = null;

			try {
				result = ClientManager.RequestToGetLikeOrNotLikeDiaDiem(
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
		protected void onPostExecute(ResponsedResult result) {
			// TODO Auto-generated method stub
			likeBtn.setEnabled(true);

			if (result != null) {
				if (result.success) {
					boolean liked = (Boolean) result.content2;
					diaDiem.isLiked = liked;
					setLikeButtonState(liked);
				} else {
					Toast.makeText(getApplicationContext(),
							"result" + result.content, Toast.LENGTH_LONG)
							.show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "null",
						Toast.LENGTH_LONG).show();
			}

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
				} else {
					// like thanh cong nhung sv tra ve success false. code mang
					// tinh test thu.
					setLikeButtonState(like);
					diaDiem.isLiked = like;
					Toast.makeText(getApplicationContext(), like + " - " +result.content,
							Toast.LENGTH_LONG).show();
				}

			} else {

				Toast.makeText(getApplicationContext(), "null",
						Toast.LENGTH_LONG).show();
			}

			super.onPostExecute(result);
		}
	}

	public class SaveOrUnSaveLocationAsynctask extends
			AsyncTask<Boolean, Integer, ResponsedResult> {
		boolean save = false;

		@Override
		protected ResponsedResult doInBackground(Boolean... params) {
			// TODO Auto-generated method stub
			ResponsedResult result = null;
			save = params[0];

			try {
				result = ClientManager.RequestToAddOrRemoveFavouriteDiaDiem(
						Constants.LOGINUSER_TOKEN, diaDiem.id, save);
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
			super.onPostExecute(result);

			if (result != null) {
				if (result.success) {

					// do something

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
