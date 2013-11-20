package com.example.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Object.DiaDiem;
import com.example.wego.R;

public class StatusAdapter extends BaseAdapter {

	private static class ViewHolder {
		public TextView locationName;
		public TextView locationAddress;
		public TextView locationRating;
		public ImageView menuToggle;
	}

	// private static int N = 5; // hard code

	private Activity mActivity;

	private ArrayList<DiaDiem> diaDiemList;

	public StatusAdapter(Activity activity, ArrayList<DiaDiem> ddList) {
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
			View view = inflator.inflate(R.layout.mess_status_item, null);

			holder.locationName = (TextView) view
					.findViewById(R.id.searchLocationNameTxt);
			holder.locationAddress = (TextView) view
					.findViewById(R.id.searchLocationAddressTxt);
			holder.locationRating = (TextView) view
					.findViewById(R.id.searchLocationRating);
			holder.menuToggle = (ImageView) view.findViewById(R.id.menuTgl);

			final int itemId = arg0;
			final ImageView imgV = holder.menuToggle;
			holder.menuToggle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					OnItemMenuClick(itemId, imgV);
				}
			});

			arg1 = view;
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		DiaDiem diaDiem = (DiaDiem) getItem(arg0);

		if (diaDiem != null) {
			holder.locationName.setText(diaDiem.ten);
			holder.locationAddress.setText(diaDiem.diaChi);
			holder.locationRating.setText((int) diaDiem.diemDanhGia + "");
		}

		return arg1;
	}

	public void OnItemMenuClick(int id, View v) {

	}

	public void addItem(DiaDiem diaDiem) {
		this.diaDiemList.add(diaDiem);
	}
}
