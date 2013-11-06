package com.example.wego;

import com.example.Object.DiaDiem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LocationDetailFragment extends Fragment {

	private DiaDiem diaDiem;
	private TextView ratingText;
	private TextView descriptionText;

	private static LocationDetailFragment instanceFragment;

	public static LocationDetailFragment instance() {
		if (instanceFragment == null) {
			instanceFragment = new LocationDetailFragment();
		}
		return instanceFragment;
	}

	public LocationDetailFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.location_detail_fragment,
				container, false);
		
		ratingText = (TextView) view.findViewById(R.id.ratingText);
		descriptionText = (TextView) view.findViewById(R.id.descriptionText);
		
		updateDiaDiem();
		return view;
	}

	private void updateDiaDiem() {
		LocationDetailActivity parent = (LocationDetailActivity) getActivity();
		DiaDiem dd = parent.getDiaDiem();
		this.diaDiem = dd;
		
		setView();
	}

	private void setView() {
		if(this.diaDiem!=null){
			descriptionText.setText(this.diaDiem.moTa);
			ratingText.setText((int)this.diaDiem.diemDanhGia+"");
		}
	}

}
