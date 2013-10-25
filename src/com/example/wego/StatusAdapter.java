package com.example.wego;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class StatusAdapter extends BaseAdapter {
	private static int N = 5; // hard code
	private Activity mActivity;

	public StatusAdapter(Activity activity) {
		mActivity = activity;
	}

	@Override
	public int getCount() {
		return N;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// không giử hodler
		LayoutInflater inflator = mActivity.getLayoutInflater();

		View convertView = inflator.inflate(R.layout.mess_status_item, null);
		return convertView;
	}

}
