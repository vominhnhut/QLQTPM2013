package com.example.wego;

import java.util.ArrayList;

import com.example.Object.DiaDiem;
import com.example.adapter.LeftDrawerAdapter;
import com.example.ultils.Constants;
import com.example.ultils.DialogGenerator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		SearchView.OnQueryTextListener {

	public static final int HOME_ITEM_ID = 0;
	public static final int ACCOUNT_ITEM_ID = 1;
	public static final int EXIT_ITEM_ID = 2;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private ArrayList<DiaDiem> searchedDiadiem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = getTitle();
		mDrawerTitle = mTitle;
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// mDrawerLayout.setVisibility(View.GONE);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		mDrawerList.setAdapter(new LeftDrawerAdapter(this));

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				getMaGoMainFragment().hideListStatus();
				hideKeyPad();
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, WeGoMainFragment.instance(),
						WeGoMainFragment.TAG).commit();

		if (savedInstanceState == null) {
			selectItem(0);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setOnQueryTextListener(this);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		switch (position) {
		case HOME_ITEM_ID:
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			break;
		case ACCOUNT_ITEM_ID:
			break;
		case EXIT_ITEM_ID:
			AlertDialog dialog = DialogGenerator.createExitConfirmationDialog(
					this, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							System.exit(0);
						}
					});
			dialog.show();
			break;
		default:
			break;
		}

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public ArrayList<DiaDiem> getSearchedItems() {
		return this.searchedDiadiem;
	}

	public void search(String key) {
		this.searchedDiadiem = Constants.getDumbDDList();
		updateSearchList();
	}

	public void updateSearchList() {
		WeGoMainFragment fragment = getMaGoMainFragment();
		if (fragment != null) {
			fragment.updateSearchList(searchedDiadiem);
		} else {
			Toast.makeText(getApplicationContext(), "fuck", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		search(query);
		hideKeyPad();
		return false;
	}
	
	public WeGoMainFragment getMaGoMainFragment(){
		WeGoMainFragment fragment = (WeGoMainFragment) getFragmentManager()
				.findFragmentByTag(WeGoMainFragment.TAG);
		return fragment;
	}
	
	public void hideKeyPad() {
		InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
	}
}