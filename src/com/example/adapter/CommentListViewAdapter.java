package com.example.adapter;

import java.util.ArrayList;

import com.example.Object.BinhLuan;
import com.example.wego.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentListViewAdapter extends BaseAdapter {

	private Context adapterContext;
	private ArrayList<BinhLuan> binhLuanList;

	private static class ViewHolder {
		//ImageView userAvatar;
		TextView userCommentText;
		TextView userName;
		TextView postDate;
	}

	public CommentListViewAdapter(Context context, ArrayList<BinhLuan> list) {
		this.adapterContext = context;
		this.binhLuanList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return binhLuanList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return binhLuanList.get(arg0);
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();

		if (arg1 == null) {
            LayoutInflater inflator = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflator.inflate(R.layout.comment_list_item, null);
			
			//holder.userAvatar = (ImageView) view.findViewById(R.id.user_avata_img);
			holder.userName = (TextView) view.findViewById(R.id.user_name_txt);
			holder.userCommentText = (TextView) view.findViewById(R.id.searchLocationAddressTxt);
			holder.postDate = (TextView) view.findViewById(R.id.searchLocationNameTxt);
			
			arg1 = view;
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		BinhLuan binhLuan = (BinhLuan) getItem(arg0);
		
		if(binhLuan !=null){
			holder.userName.setText(binhLuan.tenNguoiDang);
			holder.userCommentText.setText( binhLuan.noiDung);
			holder.postDate.setText(binhLuan.thoiGianDang.toString());
		}
		
		return arg1;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void addComment(BinhLuan bl){
		this.binhLuanList.add(bl);
	}
}
