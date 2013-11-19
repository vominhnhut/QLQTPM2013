package com.example.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.Object.DiaDiem;
import com.example.wego.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class FavoriteLocationAdapter extends BaseAdapter {

	private static class ViewHolder {
		public GoogleMap map;
		public TextView locationAddress;
		public Button detailBtn;
		public Button deleteBtn;
	}

	// private static int N = 5; // hard code

	private FragmentActivity mActivity;

	private ArrayList<DiaDiem> diaDiemList;

	public FavoriteLocationAdapter(FragmentActivity activity, ArrayList<DiaDiem> ddList) {
		mActivity = activity;
		this.diaDiemList = ddList;
	}

	@Override
	public int getCount() {
		return diaDiemList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return diaDiemList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// không giử hodler
		ViewHolder holder = new ViewHolder();

		if (arg1 == null) {
			LayoutInflater inflator = (LayoutInflater) mActivity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflator.inflate(R.layout.favorite_item, null);

//			holder.map = ((SupportMapFragment) mActivity.getSupportFragmentManager()
//					.findFragmentById(R.id.favMiniMap)).getMap();
			holder.locationAddress = (TextView) view
					.findViewById(R.id.favlocationAddress);
			holder.detailBtn = (Button) view.findViewById(R.id.detail_btn);
			holder.deleteBtn = (Button) view.findViewById(R.id.delete_btn);

			arg1 = view;
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		DiaDiem diaDiem = (DiaDiem) getItem(arg0);

		if (diaDiem != null) {

			CameraUpdate center = CameraUpdateFactory.newLatLng(diaDiem
					.getLatLng());
			CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
//			holder.map.moveCamera(center);
//			holder.map.animateCamera(zoom);
//			holder.map.addMarker(new MarkerOptions().position(diaDiem
//					.getLatLng()));

			holder.locationAddress.setText(diaDiem.diaChi);

			final int itemId = arg0;

			holder.detailBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onDetailButtonClick(itemId);
				}
			});
			holder.deleteBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onDeleteButtonClick(itemId);
				}
			});

		}

		return arg1;
	}

	public void onDeleteButtonClick(int itemId) {

	}

	public void onDetailButtonClick(int itemId) {

	}
}