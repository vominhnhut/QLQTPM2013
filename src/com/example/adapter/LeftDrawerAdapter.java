package com.example.adapter;

import java.util.ArrayList;

import com.example.wego.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftDrawerAdapter extends BaseAdapter {
	
	private Activity mActivity;
	private String[] mListItems;
	private ArrayList<Integer> mListIcon;

	private static class ViewHolder {
		public TextView txtTitle;
		public ImageView imgIcon;
	}

	public LeftDrawerAdapter(Activity activity) {
		mActivity = activity;
		mListItems = activity.getResources().getStringArray(
				R.array.left_items_array);
		mListIcon = new ArrayList<Integer>();
		mListIcon.add(R.drawable.home);
		mListIcon.add(R.drawable.user);
		mListIcon.add(R.drawable.setting);
		mListIcon.add(R.drawable.close);
	}

	@Override
	public int getCount() {

		return mListItems.length;
	}

	@Override
	public Object getItem(int position) {

		return mListItems[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		LayoutInflater inflator = mActivity.getLayoutInflater();
		if (convertView == null) {

			convertView = inflator.inflate(R.layout.left_item_view, null);
			viewHolder = new ViewHolder();

			viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txt);
			viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.txtTitle.setText(mListItems[position]);
		viewHolder.imgIcon.setImageDrawable(mActivity.getResources()
				.getDrawable(mListIcon.get(position)));

		return convertView;
	}

}
