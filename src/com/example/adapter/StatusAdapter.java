package com.example.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.Object.DiaDiem;
import com.example.wego.R;

public class StatusAdapter extends BaseAdapter {

	private static class ViewHolder {
		public TextView locationName;
		public TextView locationAddress;
		public TextView locationRating;
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
			View view = inflator.inflate(R.layout.comment_list_item, null);

			holder.locationName = (TextView) view
					.findViewById(R.id.searchLocationNameTxt);
			holder.locationAddress = (TextView) view
					.findViewById(R.id.searchLocationAddressTxt);
			holder.locationRating = (TextView) view
					.findViewById(R.id.searchLocationRating);

			arg1 = view;
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		DiaDiem diaDiem = (DiaDiem) getItem(arg0);

		if (diaDiem != null) {
			holder.locationName.setText(diaDiem.ten);
			holder.locationAddress.setText(diaDiem.diaChi);
			holder.locationRating.setText(diaDiem.diemDanhGia + "");
		}

		return arg1;
	}

}
