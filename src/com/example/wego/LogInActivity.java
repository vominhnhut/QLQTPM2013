package com.example.wego;

import java.io.IOException;

import org.json.JSONException;

import com.example.Object.TaiKhoan;
import com.example.clientmanager.ClientManager;
import com.example.clientmanager.ResponsedResult;
import com.example.ultils.Constants;
import com.example.ultils.DialogGenerator;
import com.example.ultils.NetworkHelper;
import com.example.ultils.Ultils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LogInActivity extends Activity implements OnClickListener {

	TextView txtUserName;
	TextView txtPassWord;
	Button btnLogIn;
	Button btnRegister;
	LinearLayout log_in_view;
	LinearLayout wait_view;

	boolean isSuccess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_log_in);

		getActionBar().hide();

		log_in_view = (LinearLayout) findViewById(R.id.log_in_view);
		wait_view = (LinearLayout) findViewById(R.id.wait_view);

		txtUserName = (TextView) findViewById(R.id.userName);
		txtPassWord = (TextView) findViewById(R.id.password);
		btnLogIn = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegist);

		btnLogIn.setOnClickListener(this);
		btnRegister.setOnClickListener(this);

		txtUserName.setText("nhut");
		txtPassWord.setText("nhut");

		autoLogIn();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLogin:
			TaiKhoan tk = new TaiKhoan();
			tk.tenTaiKhoan = txtUserName.getText().toString();
			tk.matKhau = txtPassWord.getText().toString();

			logIn(tk);
			break;
		case R.id.btnRegist:
			Intent intent = new Intent(LogInActivity.this,
					RegisterActivity.class);
			startActivity(intent);
			break;
		}
	}

	public boolean checkNetworkConnection() {
		return NetworkHelper.checkNetwork(getApplicationContext());
	}

	public void logIn(TaiKhoan taikhoan) {
		Boolean isNetworkConnected = checkNetworkConnection();
		if (isNetworkConnected) {
			new LoginStask().execute(taikhoan);
		} else {
			AlertDialog dialog = DialogGenerator
					.createNetworkConnectionDialogAleart(this,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(
											android.provider.Settings.ACTION_SETTINGS);
									startActivity(intent);
								}
							});
			dialog.show();
		}
	}

	private void autoLogIn() {
		TaiKhoan tk = Ultils.getUserNameAndPasswordFromSharedPref(this);
		if (tk != null) {
			logIn(tk);
		}
	}

	/**
	 * 
	 */
	private class LoginStask extends
			AsyncTask<TaiKhoan, String, ResponsedResult> {

		@Override
		protected ResponsedResult doInBackground(TaiKhoan... params) {
			// TODO Auto-generated method stub
			TaiKhoan tk = params[0];
			ResponsedResult result = null;
			try {
				result = ClientManager.RequestToLogIn(tk.tenTaiKhoan,
						tk.matKhau);

			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				Log.e("Log 1", e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("Log 2", e.getMessage());
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("Log 3", e.getMessage());
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(ResponsedResult result) {
			// TODO Auto-generated method stub
			if (result != null && result.success) {
				Intent intent = new Intent(LogInActivity.this,
						MainActivity.class);
				intent.putExtra(Constants.LOG_IN_RESULT_TAG, result.success);

				Ultils.saveUserNameAndPasswordToSharedPref(LogInActivity.this,
						txtUserName.getText().toString(), txtPassWord.getText()
								.toString());

				Constants.LOGGED_IN = true;

				finish();
			} else {
				AlertDialog dialog = DialogGenerator.createAlertDialog(
						LogInActivity.this, result.content);
				dialog.show();
			}

			wait_view.setVisibility(View.GONE);
			log_in_view.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			wait_view.setVisibility(View.VISIBLE);
			log_in_view.setVisibility(View.GONE);
			super.onPreExecute();
		}

	}
}
