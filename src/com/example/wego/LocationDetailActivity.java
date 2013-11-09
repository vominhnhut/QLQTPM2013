package com.example.wego;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import com.example.Object.DiaDiem;
import com.example.Object.TaiKhoan;
import com.example.adapter.LocationDetailPagerAdapter;
import com.example.clientmanager.ClientManager;
import com.example.clientmanager.ResponsedResult;
import com.example.ultils.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
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

		// DiaDiem a = new DiaDiem();
		// a.ten = "KFC NOWZONE";
		// a.diaChi = "KhÃ´ng nhá»›, Nguyá»…n VÄƒn Cá»«, Q5, HCM";
		// a.toaDo = new LatLng(40.76793169992044, -73.98180484771729);
		// a.danhSachBinhLuan = createDumdComments();
		// a.moTa="Ngon, Ä‘Ã´ng, vui... GÃ¡i nhiá»�u thá»ƒ loáº¡i";
		// a.diemDanhGia=1231;
		//
		// this.diaDiem = a;
		// setViewValue();

		//new LocationDetailAsynctask().execute();
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

		}
	}

	// private void setCommentList() {
	// if (diaDiem != null) {
	// LocationDetailCommentsFragment commentsFragment =
	// LocationDetailCommentsFragment
	// .instance();
	// boolean b = commentsFragment.setCommentList(diaDiem.danhSachBinhLuan);
	// pagerAdapter.replaceItem(COMMENT_FRAGMENT, commentsFragment);
	// Log.e("set comment", b+"");
	// }
	// }

	public DiaDiem getDiaDiem() {
		return this.diaDiem;
	}

	public class LocationDetailAsynctask extends
			AsyncTask<String, Integer, ResponsedResult> {
		TaiKhoan taikhoan;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected ResponsedResult doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			ResponsedResult result = null;

			try {
				result = ClientManager.RequestToLogIn("hoaphat92", "123456");
				// taikhoan = new TaiKhoan();
				// taikhoan.hoTen = "Nguyen Hoa Phat";
				// taikhoan.matKhau = "123456";
				// taikhoan.ngaySinh = "1992-11-6";
				// taikhoan.email = "phatnguyen.it.khtn@gmail.com";
				// taikhoan.tenTaiKhoan = "hoaphat92";
				// result = ClientManager.RequestToRegisterAccount(taikhoan);

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

//			boolean success = result.success;
//
//			if (success) {
//				Toast.makeText(getApplicationContext(),
//						"Logic Success" + Constants.LOGINUSER_TOKEN, 2).show();
//
//			} else {
//				Toast.makeText(getApplicationContext(),
//						"Fail" + result.content, 2).show();
//			}
		}
	}
}
