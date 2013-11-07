package com.example.wego;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.Object.BinhLuan;
import com.example.Object.DiaDiem;
import com.example.adapter.CommentListViewAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
	
	public void updateCommentList(){
		LocationDetailActivity parent = (LocationDetailActivity) getActivity();
		DiaDiem dd = parent.getDiaDiem();
		setCommentList(dd.danhSachBinhLuan);
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
			BinhLuan newBinhLuan = new BinhLuan();
			newBinhLuan.noiDung = "This is dumb text of me:"
					+ commentTxtview.getText();
			newBinhLuan.tenNguoiDang = "Me";
			newBinhLuan.thoiGianDang = Calendar.getInstance().getTime();
			addComment(newBinhLuan);
			break;

		default:
			break;
		}
	}

}
