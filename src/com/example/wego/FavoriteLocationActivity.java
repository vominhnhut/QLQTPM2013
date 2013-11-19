package com.example.wego;

import java.util.ArrayList;

import com.example.Object.DiaDiem;
import com.example.adapter.StatusAdapter;
import com.example.ultils.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class FavoriteLocationActivity extends FragmentActivity {

	ListView favoriteList;
	StatusAdapter favoriteAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite_location);

		getActionBar().setTitle(R.string.favoriteList_title);
		
		favoriteList = (ListView) findViewById(R.id.favoriteList);

		updateFavoriteAdapter(Constants.getDumbDDList());

		favoriteList.setAdapter(favoriteAdapter);
		favoriteList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				DiaDiem dd = (DiaDiem) favoriteAdapter.getItem(arg2);
				if(dd != null){
					toDetailActivity(dd);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.favorite_location, menu);
		return true;
	}

	public DiaDiem requestFullLocation(DiaDiem diaDiem) {
		return diaDiem;
	}

	private void toDetailActivity(DiaDiem diaDiem) {
		requestFullLocation(diaDiem);

		Intent intent = new Intent(FavoriteLocationActivity.this , LocationDetailActivity.class);
		if (diaDiem != null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(Constants.DIADIEM_KEY, diaDiem);
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	public void updateFavoriteAdapter(ArrayList<DiaDiem> listDD) {

		favoriteAdapter = new StatusAdapter(this, listDD) {
			@Override
			public void OnItemMenuClick(int id, View v) {
				// TODO Auto-generated method stub
				PopupMenu menu = new PopupMenu(FavoriteLocationActivity.this, v);
				menu.inflate(R.menu.favorite_location);

	//			final DiaDiem dd = (DiaDiem) favoriteAdapter.getItem(id);

				menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						// TODO Auto-generated method stub
						switch (item.getItemId()) {
						case R.id.fav_item_navigate:
							//
							break;
						case R.id.fav_item_delete:
							//
							break;
						default:
							break;
						}
						return false;
					}
				});
				menu.show();
				super.OnItemMenuClick(id, v);
			}
		};

	}
}
