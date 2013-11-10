package com.example.wego;

import java.io.IOException;

import org.json.JSONException;

import com.example.clientmanager.ClientManager;
import com.example.clientmanager.ResponsedResult;
import com.example.ultils.Constants;
import com.example.ultils.DialogGenerator;
import com.example.ultils.Ultils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ChangePasswordActivity extends Activity implements OnClickListener {

	EditText edtOldPass;
	EditText edtNewPass;
	EditText edtNewPassConfirm;
	Button btnOk;
	Button btnCancel;

	LinearLayout changePassView;
	LinearLayout waitView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);

		changePassView = (LinearLayout) findViewById(R.id.change_pass_view);
		waitView = (LinearLayout) findViewById(R.id.chg_wait_view);

		edtOldPass = (EditText) findViewById(R.id.oldPass);
		edtNewPass = (EditText) findViewById(R.id.newPass);
		edtNewPassConfirm = (EditText) findViewById(R.id.newPassConfirm);
		btnOk = (Button) findViewById(R.id.btnChangePass);
		btnCancel = (Button) findViewById(R.id.btnCancelChange);

		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnChangePass:
			if (edtNewPass.getText().toString().equals("")
					|| edtNewPassConfirm.getText().toString().equals("")
					|| edtOldPass.getText().toString().equals("")) {
				DialogGenerator.createAlertDialog(ChangePasswordActivity.this,
						getResources().getString(R.string.empty_field)).show();
				break;
			}
			if (!edtNewPass.getText().toString()
					.equals(edtNewPassConfirm.getText().toString())) {
				DialogGenerator.createAlertDialog(ChangePasswordActivity.this,
						getResources().getString(R.string.password_missmatch))
						.show();
				break;
			}

			new ChangePassTask().execute("");
			break;
		case R.id.btnCancelChange:
			finish();
			break;

		default:
			break;
		}
	}

	private class ChangePassTask extends
			AsyncTask<String, String, ResponsedResult> {

		@Override
		protected void onPostExecute(ResponsedResult result) {
			// TODO Auto-generated method stub
			changePassView.setVisibility(View.VISIBLE);
			waitView.setVisibility(View.GONE);

			if (result != null && result.success) {
				Ultils.saveUserNameAndPasswordToSharedPref(
						ChangePasswordActivity.this, null, edtNewPass.getText()
								.toString());
				ChangePasswordActivity.this.finish();
			} else {
				if (result != null) {
					DialogGenerator.createAlertDialog(
							ChangePasswordActivity.this, result.content).show();
				} else {
					DialogGenerator.createAlertDialog(
							ChangePasswordActivity.this, "Null result").show();
				}
			}

			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			changePassView.setVisibility(View.GONE);
			waitView.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected ResponsedResult doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				ResponsedResult result = ClientManager.RequestToChangePassword(
						Constants.LOGINUSER_TOKEN, edtOldPass.getText()
								.toString(), edtNewPass.getText().toString());

				if (result != null) {
					return result;
				}
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

	}
}
