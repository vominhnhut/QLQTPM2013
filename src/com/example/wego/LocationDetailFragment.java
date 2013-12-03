package com.example.wego;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.example.Object.ChiTietDichVu;
import com.example.Object.DiaDiem;
import com.example.clientmanager.ClientManager;
import com.example.clientmanager.ResponsedResult;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

	public void updateDiaDiem() {
		LocationDetailActivity parent = (LocationDetailActivity) getActivity();
		if (parent != null) {
			DiaDiem dd = parent.getDiaDiem();
			this.diaDiem = dd;

			setView();
		}
	}

	private void setView() {
		if (this.diaDiem != null) {
			descriptionText.setText(this.diaDiem.moTa);

			String rating = "";
			if (this.diaDiem.diemDanhGia > 0) {
				rating += (int) this.diaDiem.diemDanhGia + " "
						+ getString(R.string.like_ext1_string);
			}
			if (this.diaDiem.isLiked == false) {
				rating += " " + getString(R.string.like_ext2_string);
			}
			ratingText.setText(rating);

			new LoadServicesAsynctask().execute();
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

	public class LoadServicesAsynctask extends
			AsyncTask<Void, Integer, ResponsedResult> {

		ArrayList<ChiTietDichVu> listChiTietDichVu = new ArrayList<ChiTietDichVu>();

		@Override
		protected ResponsedResult doInBackground(Void... params) {

			ResponsedResult result = null;
			try {

				result = ClientManager.RequestToGetListChiTietDichVu(
						diaDiem.id, listChiTietDichVu);
				Log.e("hoaphat", result.success + "");
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
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

					diaDiem.danhSachDichVu = listChiTietDichVu;

					if (diaDiem.danhSachDichVu != null
							&& diaDiem.danhSachDichVu.size() > 0) {
						for (ChiTietDichVu dv : diaDiem.danhSachDichVu) {
							View view = createServiceView(dv);
							// Add view
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
									ViewGroup.LayoutParams.MATCH_PARENT,
									ViewGroup.LayoutParams.WRAP_CONTENT);
							container.addView(view, params);
						}
					}
				} else {
					Toast.makeText(getActivity().getApplicationContext(),
							result.content, Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getActivity().getApplicationContext(),
						"null detail request", Toast.LENGTH_LONG).show();
			}
		}
	}
}
