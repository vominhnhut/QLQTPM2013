package com.example.adapter;

import java.util.ArrayList;

import com.example.Object.DiaDiem;
import com.example.wego.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class StatusAdapter extends BaseAdapter {
	private static int N = 5; // hard code
	private Activity mActivity;

	private ArrayList<DiaDiem> diaDiemList;
	
	public StatusAdapter(Activity activity, ArrayList<DiaDiem> ddList) {
		mActivity = activity;
		this.diaDiemList = diaDiemList;
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
