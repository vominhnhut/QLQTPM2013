package com.example.ultils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Ultils {

	public static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
	
	public static String getDateString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STRING);
		return sdf.format(date);
	}

	public static Date getDateString(String date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STRING);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
