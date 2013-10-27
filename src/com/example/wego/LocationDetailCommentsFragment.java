package com.example.wego;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.Object.BinhLuan;
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

		if (commentListview == null) {
			Toast.makeText(getActivity().getApplicationContext(), "null",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getActivity().getApplicationContext(), "not null",
					Toast.LENGTH_SHORT).show();
		}
		
		this.commentAdapter = null;

		 ArrayList<BinhLuan> bls = new ArrayList<BinhLuan>();
		 for(int i=0 ; i< 10 ; i++){
		 BinhLuan newBinhLuan = new BinhLuan();
		 newBinhLuan.setNoiDung("This is dumb text of " + i);
		 newBinhLuan.setTenNguoiDang("User " + i );
		 newBinhLuan.setThoiGianDang(Calendar.getInstance().getTime());
		
		 bls.add(newBinhLuan);
		 }
		 this.commentAdapter = new CommentListViewAdapter(getActivity(), bls);
		 commentListview.setAdapter(commentAdapter);

		return view;
	}

	public void setCommentList(ArrayList<BinhLuan> comments) {
		CommentListViewAdapter adapter = new CommentListViewAdapter(
				getActivity(), comments);
		if (commentListview != null) {
			this.commentAdapter = adapter;
			commentListview.setAdapter(commentAdapter);
		}
	}

	public void addComment(BinhLuan binhLuan) {
		if (commentAdapter == null) {
			commentAdapter = new CommentListViewAdapter(getActivity(),
					new ArrayList<BinhLuan>());
		}
		commentAdapter.addComment(binhLuan);
		commentAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sned_comment_btn:
			BinhLuan newBinhLuan = new BinhLuan();
			newBinhLuan.setNoiDung("This is dumb text of me");
			newBinhLuan.setTenNguoiDang("Me");
			newBinhLuan.setThoiGianDang(Calendar.getInstance().getTime());
			addComment(newBinhLuan);
			Toast.makeText(getActivity(),
					"Comment: " + commentTxtview.getText(), Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}
	}

}
