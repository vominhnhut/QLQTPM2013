package com.example.wego;

import java.util.ArrayList;

import com.example.Object.ChiTietDichVu;
import com.example.Object.DiaDiem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LocationDetailFragment extends Fragment {

	private DiaDiem diaDiem;
	private TextView ratingText;
	private TextView descriptionText;
	private LinearLayout container;

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
		this.container = (LinearLayout) view
				.findViewById(R.id.services_container);

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
		if (this.diaDiem != null) {
			descriptionText.setText(this.diaDiem.moTa);

			String rating = "";
			if (this.diaDiem.diemDanhGia > 0) {
				rating += (int) this.diaDiem.diemDanhGia + " "
						+ getString(R.string.like_ext1_string);
			}
			rating += " " + getString(R.string.like_ext2_string);
			ratingText.setText(rating);

			this.diaDiem.danhSachDichVu = new ArrayList<ChiTietDichVu>();
			for (int i = 0; i < 10; i++) {
				ChiTietDichVu dv = new ChiTietDichVu();
				dv.ten = "dv " + i;
				dv.donGia = i * 10000 + "";
				dv.moTa = "Dumb " + i;

				this.diaDiem.danhSachDichVu.add(dv);
			}
			if (this.diaDiem.danhSachDichVu != null
					&& this.diaDiem.danhSachDichVu.size() > 0) {
				for (ChiTietDichVu dv : this.diaDiem.danhSachDichVu) {
					View view = createServiceView(dv);
					// Add view
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							ViewGroup.LayoutParams.MATCH_PARENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
//					view.setPadding(R.dimen.dp3_pading, R.dimen.dp3_pading,
//							R.dimen.dp3_pading, R.dimen.dp3_pading);
					container.addView(view, params);
				}
			}
		}
	}

	private View createServiceView(ChiTietDichVu dichVu) {
		LayoutInflater vi = (LayoutInflater) getActivity()
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.service_detail_layout, null);

		TextView serviceName = (TextView) v.findViewById(R.id.service_name);
		TextView servicePrice = (TextView) v.findViewById(R.id.service_price);
		TextView serviceDetail = (TextView) v
				.findViewById(R.id.service_description);

		serviceName.setText(dichVu.ten);
		servicePrice.setText(dichVu.donGia);
		serviceDetail.setText(dichVu.moTa);

		return v;
	}
}
