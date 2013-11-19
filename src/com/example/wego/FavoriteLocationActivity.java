package com.example.wego;

import com.example.adapter.FavoriteLocationAdapter;
import com.example.ultils.Constants;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ListView;

public class FavoriteLocationActivity extends FragmentActivity {

	ListView favoriteList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite_location);

		favoriteList = (ListView) findViewById(R.id.favoriteList);

		favoriteList.setAdapter(new FavoriteLocationAdapter(this, Constants
				.getDumbDDList()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorite_location, menu);
		return true;
	}

}
