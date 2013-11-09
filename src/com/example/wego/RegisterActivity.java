package com.example.wego;

import java.io.IOException;

import org.json.JSONException;

import com.example.Object.TaiKhoan;
import com.example.clientmanager.ClientManager;
import com.example.clientmanager.ResponsedResult;
import com.example.ultils.DialogGenerator;
import com.example.ultils.NetworkHelper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {

	private final int OK = 0;
	private final int EMPTY_FEILD = 1;
	private final int PASSWORD_MISSMATCH = 2;

	EditText edtRgtUsername;
	EditText edtRgtPassword;
	EditText edtRgtConfirmPassword;
	EditText edtRgtRealName;
	EditText edtRgtEmail;
	EditText edtRgtBirthDay;
	Button btnRegist;

	LinearLayout rgtView;
	LinearLayout rgtWait;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		getActionBar().hide();

		edtRgtUsername = (EditText) findViewById(R.id.edtRgtUserName);
		edtRgtPassword = (EditText) findViewById(R.id.edtRgtPassword);
		edtRgtConfirmPassword = (EditText) findViewById(R.id.edtRgtConfirmPassword);
		edtRgtRealName = (EditText) findViewById(R.id.edtRgtRealName);
		edtRgtEmail = (EditText) findViewById(R.id.edtRgtEmail);
		edtRgtBirthDay = (EditText) findViewById(R.id.edtRgtBirthday);
		btnRegist = (Button) findViewById(R.id.btnRegister);

		rgtWait = (LinearLayout) findViewById(R.id.rgt_wait_view);
		rgtView = (LinearLayout) findViewById(R.id.rgtView);

		edtRgtBirthDay.setFocusable(false);
		edtRgtBirthDay.setOnClickListener(this);
		btnRegist.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edtRgtBirthday:
			DatePickerDialog picker = DialogGenerator.createTimePickerDialog(
					this, new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							String date = year + "-";
							if (monthOfYear < 10) {
								date += "0";
							}
							date += monthOfYear + "-";
							if (dayOfMonth < 10) {
								date += "0";
							}
							date += dayOfMonth;
							edtRgtBirthDay.setText(date);
						}
					});
			picker.show();
			break;
		case R.id.btnRegister:
			Boolean isNetworkConnected = checkNetworkConnection();
			if (isNetworkConnected) {
				regist();
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
			break;
		}
	}

	public void regist() {
		int result = checkViewValues();

		switch (result) {
		case EMPTY_FEILD:
			Toast.makeText(getApplicationContext(), R.string.empty_field,
					Toast.LENGTH_SHORT).show();
			break;
		case PASSWORD_MISSMATCH:
			Toast.makeText(getApplicationContext(),
					R.string.password_missmatch, Toast.LENGTH_SHORT).show();
			break;
		case OK:
			TaiKhoan tk = new TaiKhoan();
			tk.tenTaiKhoan = edtRgtUsername.getText().toString();
			tk.matKhau = edtRgtPassword.getText().toString();
			tk.hoTen = edtRgtRealName.getText().toString();
			tk.email = edtRgtEmail.getText().toString();
			tk.ngaySinh = edtRgtBirthDay.getText().toString();

			new RegistTask().execute(tk);
			break;
		}
	}

	public boolean checkNetworkConnection() {
		return NetworkHelper.checkNetwork(getApplicationContext());
	}

	private int checkViewValues() {
		if (edtRgtUsername.getText().toString().equals("")
				|| edtRgtPassword.getText().toString().equals("")
				|| edtRgtConfirmPassword.getText().toString().equals("")
				|| edtRgtRealName.getText().toString().equals("")
				|| edtRgtEmail.getText().toString().equals("")
				|| edtRgtBirthDay.getText().toString().equals("")) {
			return EMPTY_FEILD;
		}

		if (!edtRgtPassword.getText().toString()
				.equals(edtRgtConfirmPassword.getText().toString())) {
			return PASSWORD_MISSMATCH;
		}

		return OK;
	}

	/**
	 * 
	 */
	private class RegistTask extends
			AsyncTask<TaiKhoan, String, ResponsedResult> {

		@Override
		protected ResponsedResult doInBackground(TaiKhoan... params) {
			// TODO Auto-generated method stub
			TaiKhoan tk = params[0];
			try {
				return ClientManager.RequestToRegisterAccount(tk);
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
			return null;
		}

		@Override
		protected void onPostExecute(ResponsedResult result) {
			// TODO Auto-generated method stub
			rgtView.setVisibility(View.VISIBLE);
			rgtWait.setVisibility(View.GONE);

			if (result != null && result.success == true) {
				finish();
			} else {
				AlertDialog dialog = DialogGenerator
						.createAlertDialog(RegisterActivity.this, result.content);
				dialog.show();
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			rgtView.setVisibility(View.GONE);
			rgtWait.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

	}
}
