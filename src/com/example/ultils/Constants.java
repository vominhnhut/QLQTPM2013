package com.example.ultils;

import java.util.ArrayList;
import com.example.Object.BinhLuan;
import com.example.Object.DiaDiem;

public class Constants {
	public static final String APP_PREF_NAME = "WeGoPref";
	
	public static final String LOG_IN_RESULT_TAG = "Login result";
	
	public static final String USERNAME_TAG = "Username";
	public static final String PASSWORD_TAG = "Password";
	public static boolean LOGGED_IN = false;
	
	public static String LOGINUSER_TOKEN;
	public static final String DIADIEM_KEY = "Diadiem";

	public static ArrayList<BinhLuan> createDumdComments() {
		ArrayList<BinhLuan> bls = new ArrayList<BinhLuan>();

		for (int i = 0; i < 10; i++) {
			BinhLuan newBinhLuan = new BinhLuan();
			newBinhLuan.noiDung = "This is dumb text of " + i;
			newBinhLuan.tenNguoiDang = "User " + i;
		//	newBinhLuan.thoiGianDang = Calendar.getInstance().getTime();
			bls.add(newBinhLuan);
		}
		return bls;
	}

	public static ArrayList<DiaDiem> getDumbDDList() {
		ArrayList<DiaDiem> ddList = new ArrayList<DiaDiem>();

		for (int i = 0; i < 10; i++) {
			DiaDiem dd = new DiaDiem();
			dd.id = i + "";
			dd.ten = "Dumb location " + i;
			dd.diaChi = "Dumb address" + i;

			double latitude = 17.385044;
			double longitude = 78.486671;
			dd.latitude = latitude + i;
			dd.longitude = longitude + i;

			dd.diemDanhGia = i * 992;
			dd.isLiked = true;
			dd.isSaved = true;
			dd.danhSachBinhLuan = createDumdComments();
			dd.moTa = "Dumb description of " + i;

			ddList.add(dd);
		}
		return ddList;

	}
}
