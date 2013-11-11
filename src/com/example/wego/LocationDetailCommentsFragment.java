package com.example.wego;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;

import com.example.Object.BinhLuan;
import com.example.Object.DiaDiem;
import com.example.adapter.CommentListViewAdapter;
import com.example.clientmanager.ClientManager;
import com.example.clientmanager.ResponsedResult;
import com.example.ultils.Constants;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class LocationDetailCommentsFragment extends Fragment implements
		OnClickListener {

	private ListView commentListview;
	private EditText commentTxtview;
	private Button postCommentBtn;

	private CommentListViewAdapter commentAdapter;
	private static LocationDetailCommentsFragment instanceFragment = null;

	public LocationDetailCommentsFragment() {
		// TODO Auto-generated constructor stub
	}

	public static LocationDetailCommentsFragment instance() {
		if (instanceFragment == null) {
			instanceFragment = new LocationDetailCommentsFragment();
		}
		return instanceFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.comment_list_fragment, container,
				false);

		commentTxtview = (EditText) view.findViewById(R.id.comment_text);
		postCommentBtn = (Button) view.findViewById(R.id.sned_comment_btn);
		postCommentBtn.setOnClickListener(this);

		commentListview = (ListView) view.findViewById(R.id.comments_listView);

		this.commentAdapter = null;

		ArrayList<BinhLuan> bls = new ArrayList<BinhLuan>();
		// for(int i=0 ; i< 10 ; i++){
		// BinhLuan newBinhLuan = new BinhLuan();
		// newBinhLuan.setNoiDung("This is dumb text of " + i);
		// newBinhLuan.setTenNguoiDang("User " + i );
		// newBinhLuan.setThoiGianDang(Calendar.getInstance().getTime());
		//
		// bls.add(newBinhLuan);
		// }
		this.commentAdapter = new CommentListViewAdapter(getActivity()
				.getApplicationContext(), bls);
		commentListview.setAdapter(commentAdapter);

		updateCommentList();

		
		
		new LoadListCommentAsynctask().execute();
		
		
		
		return view;
	}

	public boolean setCommentList(ArrayList<BinhLuan> comments) {
		CommentListViewAdapter adapter = new CommentListViewAdapter(
				getActivity(), comments);
		if (commentListview != null) {
			this.commentAdapter = adapter;
			commentListview.setAdapter(commentAdapter);
			commentAdapter.notifyDataSetChanged();

			return true;
		}
		return false;

	}

	public void updateCommentList() {
		LocationDetailActivity parent = (LocationDetailActivity) getActivity();
		DiaDiem dd = parent.getDiaDiem();
		if (dd != null) {
			setCommentList(dd.danhSachBinhLuan);
		}
	}

	public void addComment(BinhLuan binhLuan) {
		if (commentAdapter == null) {
			commentAdapter = new CommentListViewAdapter(getActivity()
					.getApplicationContext(), new ArrayList<BinhLuan>());
		}
		commentAdapter.addComment(binhLuan);
		commentAdapter.notifyDataSetChanged();

		commentListview.smoothScrollToPosition(commentAdapter.getCount() - 1);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sned_comment_btn:

			new PostCommentAsynctask().execute(commentTxtview.getText()
					.toString());

			break;

		default:
			break;
		}
	}

	public class PostCommentAsynctask extends
			AsyncTask<String, Integer, ResponsedResult> {

		BinhLuan binhluan = null;

		@Override
		protected ResponsedResult doInBackground(String... params) {
			// TODO Auto-generated method stub
			binhluan = new BinhLuan();

			ResponsedResult result = null;
			String comment = params[0];

			binhluan.tenNguoiDang = Constants.LOGINUSER_TOKEN;
			binhluan.noiDung = comment;
			binhluan.thoiGianDang = Calendar.getInstance().getTime().toString();

			String locationID = ((LocationDetailActivity) getActivity())
					.getDiaDiem().id;

			try {
				result = ClientManager.RequestToPostComment(
						Constants.LOGINUSER_TOKEN, locationID, comment);
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

			if (result != null) {

				Log.e("hoapphat1", result.success + "");
				if (result.success) {

					addComment(binhluan);

					updateCommentList();

				} else {

					Toast.makeText(getActivity().getApplicationContext(),
							"Fail to send comment", 2).show();

				}
			} else {
				Toast.makeText(getActivity().getApplicationContext(), "null", 2)
						.show();
			}

		}
	}

	public class LoadListCommentAsynctask extends
			AsyncTask<String, Integer, ResponsedResult> {

		ArrayList<BinhLuan> listBinhLuan;

		@Override
		protected ResponsedResult doInBackground(String... params) {
			// TODO Auto-generated method stub
			listBinhLuan = new ArrayList<>();
			ResponsedResult result = null;

			String locationID = ((LocationDetailActivity) getActivity())
					.getDiaDiem().id;

			try {
				result = ClientManager.RequestToGetListBinhLuan(locationID,
						listBinhLuan);
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

			if (result != null) {

				Log.e("hoapphat1", result.success + "");
				if (result.success) {

					for (BinhLuan bl : listBinhLuan) {

						addComment(bl);

					}

					updateCommentList();

				} else {

					Toast.makeText(getActivity().getApplicationContext(),
							"Fail to load comments", 2).show();

				}
			} else {
				Toast.makeText(getActivity().getApplicationContext(), "null", 2)
						.show();
			}

		}
	}

}
