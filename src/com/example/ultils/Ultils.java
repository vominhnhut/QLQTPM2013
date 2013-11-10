package com.example.ultils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.Object.TaiKhoan;

@SuppressLint("SimpleDateFormat")
public class Ultils {

	public static final String DATE_FORMAT_STRING = "yyyy-MM-dd";

	public static String getDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STRING);
		return sdf.format(date);
	}

	public static Date getDateString(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STRING);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static TaiKhoan getUserNameAndPasswordFromSharedPref(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				Constants.APP_PREF_NAME, Context.MODE_PRIVATE);

		TaiKhoan tk = new TaiKhoan();
		tk.tenTaiKhoan = prefs.getString(Constants.USERNAME_TAG, null);
		tk.matKhau = prefs.getString(Constants.PASSWORD_TAG, null);

		if (tk.tenTaiKhoan != null && tk.matKhau != null) {
			return tk;
		} else {
			return null;
		}
	}

	public static void saveUserNameAndPasswordToSharedPref(Context context,
			String username, String password) {
		SharedPreferences prefs = context.getSharedPreferences(
				Constants.APP_PREF_NAME, Context.MODE_PRIVATE);

		SharedPreferences.Editor edit = prefs.edit();

		if (username != null) {
			edit.putString(Constants.USERNAME_TAG, username);
			edit.commit();
		}
		if (password != null) {
			edit.putString(Constants.PASSWORD_TAG, password);
			edit.commit();
		}

	}

	public static void removeUserNameAndPasswordToSharedPref(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				Constants.APP_PREF_NAME, Context.MODE_PRIVATE);

		SharedPreferences.Editor edit = prefs.edit();

		edit.remove(Constants.USERNAME_TAG);
		edit.commit();
		edit.remove(Constants.PASSWORD_TAG);
		edit.commit();

	}

}
