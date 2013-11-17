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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class LocationDetailCommentsFragment extends Fragment implements
		OnClickListener {

	private ListView commentListview;
	private EditText commentTxtview;
	private Button postCommentBtn;
	private LinearLayout waitView;
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
		waitView = (LinearLayout) view.findViewById(R.id.cmt_wait_view);
		commentListview = (ListView) view.findViewById(R.id.comments_listView);

		this.commentAdapter = null;

		ArrayList<BinhLuan> bls = new ArrayList<BinhLuan>();
		this.commentAdapter = new CommentListViewAdapter(getActivity()
				.getApplicationContext(), bls);
		commentListview.setAdapter(commentAdapter);
		commentListview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				int lastItem = firstVisibleItem + visibleItemCount;
				if (lastItem >= totalItemCount && totalItemCount >0) {
					//Code here
					//
				}
			}
		});

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

	public void updateCommentListOfParent() {
		LocationDetailActivity parent = (LocationDetailActivity) getActivity();
		parent.updateCommentList(this.commentAdapter.getList());
	}

	public void addComment(BinhLuan binhLuan) {
		if (commentAdapter == null) {
			commentAdapter = new CommentListViewAdapter(getActivity()
					.getApplicationContext(), new ArrayList<BinhLuan>());
		}
		commentAdapter.addComment(binhLuan);
		commentAdapter.notifyDataSetChanged();

		commentListview.smoothScrollToPosition(0);
	}

	public void addCommentToTop(BinhLuan binhLuan) {
		if (commentAdapter == null) {
			commentAdapter = new CommentListViewAdapter(getActivity()
					.getApplicationContext(), new ArrayList<BinhLuan>());
		}
		commentAdapter.addCommentToTop(binhLuan);
		commentAdapter.notifyDataSetChanged();

		commentListview.smoothScrollToPosition(0);
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
				if (result.success) {
					binhluan.thoiGianDang = getResources().getString(
							R.string.posted_test);
					addCommentToTop(binhluan);
					updateCommentListOfParent();
					commentTxtview.setText("");
				} else {
					Toast.makeText(getActivity().getApplicationContext(),
							result.content, Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getActivity().getApplicationContext(),
						R.string.unknown_exceeption, Toast.LENGTH_SHORT).show();
			}

			waitView.setVisibility(View.GONE);
			commentListview.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			waitView.setVisibility(View.VISIBLE);
			commentListview.setVisibility(View.GONE);

			super.onPreExecute();
		}
	}

	/**
	 * 
	 * @author VMNhut
	 * 
	 */

	public class LoadListCommentAsynctask extends
			AsyncTask<String, Integer, ResponsedResult> {

		ArrayList<BinhLuan> listBinhLuan;

		@Override
		protected ResponsedResult doInBackground(String... params) {
			// TODO Auto-generated method stub
			listBinhLuan = new ArrayList<BinhLuan>();
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
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			waitView.setVisibility(View.VISIBLE);
			commentListview.setVisibility(View.GONE);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ResponsedResult result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result != null) {
				if (result.success) {
					for (BinhLuan bl : listBinhLuan) {
						commentTxtview.setText("");
						addComment(bl);
					}
					updateCommentListOfParent();
				} else {
					Toast.makeText(getActivity().getApplicationContext(),
							result.content, Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getActivity().getApplicationContext(),
						R.string.unknown_exceeption, Toast.LENGTH_SHORT).show();
			}
			waitView.setVisibility(View.GONE);
			commentListview.setVisibility(View.VISIBLE);
		}
	}

}
