package com.example.jsonparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Object.BinhLuan;
import com.example.Object.ChiTietDichVu;
import com.example.Object.DiaDiem;
import com.example.Object.TaiKhoan;

/**
 * JSON to Object. Object to JSON. Parser
 * 
 * @author Hoa Phat
 * @since 2013/11/8
 * 
 */
public class JSONParser {

	public static JSONObject getJSONObjectFromHttpResponse(HttpResponse response)
			throws IllegalStateException, IOException, JSONException {

		JSONObject jsonObjectOutput;
		HttpEntity entity;
		InputStream is;
		entity = response.getEntity();
		is = entity.getContent();

		// get data
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"iso-8859-1"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		is.close();

		jsonObjectOutput = new JSONObject(sb.toString());

		return jsonObjectOutput;
	}

	public static JSONObject getJSONFromObject(TaiKhoan taiKhoan)
			throws JSONException {
		JSONObject obj = new JSONObject();

		obj.put("ten_tai_khoan", taiKhoan.tenTaiKhoan);
		obj.put("mat_khau", taiKhoan.matKhau);
		obj.put("ho_va_ten", taiKhoan.hoTen);
		obj.put("email", taiKhoan.email);
		obj.put("ngay_sinh", taiKhoan.ngaySinh);

		return obj;
	}

	public static DiaDiem parseDiaDiemFromJSON(JSONObject obj) {

		DiaDiem diadiem = new DiaDiem();

		return diadiem;

	}

	// lay danh sach dia diem tom tat tu jsonarray
	public static ArrayList<DiaDiem> parseListDiaDiemTomTatFromJSON(
			JSONArray array) {

		ArrayList<DiaDiem> listDiaDiem = new ArrayList<DiaDiem>();

		//

		//

		return listDiaDiem;
	}

	public static ArrayList<BinhLuan> parseListBinhLuanFromJSON(JSONArray array) {

		ArrayList<BinhLuan> listBinhLuan = new ArrayList<BinhLuan>();

		//

		//

		return listBinhLuan;
	}

	public static ArrayList<ChiTietDichVu> parseListChiTietDichVuFromJSON(
			JSONArray array) {

		ArrayList<ChiTietDichVu> listChiTietDichVu = new ArrayList<ChiTietDichVu>();

		//

		//

		return listChiTietDichVu;
	}

	// public static DiaDiem FakeDiaDiem() {
	// DiaDiem fake = new DiaDiem();
	//
	// fake.ten = "Osaka Ramen";
	// fake.diaChi =
	// "18 ThaÌ�i VÄƒn Lung, P.BÃªÌ�n NgheÌ�, Q.1, Ho Chi Minh City, Vietnam";
	// fake.diemDanhGia = 123;
	// fake.moTa = "QuÃ¡n Äƒn Nháº­t. MÃ¬ Nháº­t";
	// fake.toaDo = new LatLng(10.780023, 106.702282);
	//
	// return fake;
	// }
	//
	// public static ArrayList<DiaDiem> FakeListDiaDiem() {
	//
	// ArrayList<DiaDiem> list = new ArrayList<DiaDiem>();
	//
	// DiaDiem fake1 = new DiaDiem();
	//
	// fake1.ten = "Osaka Ramen";
	// fake1.diaChi =
	// "18 ThaÌ�i VÄƒn Lung, P.BÃªÌ�n NgheÌ�, Q.1, Ho Chi Minh City, Vietnam";
	// fake1.diemDanhGia = 123;
	// fake1.moTa = "QuÃ¡n Äƒn Nháº­t. MÃ¬ Nháº­t";
	// fake1.toaDo = new LatLng(10.780023, 106.702282);
	//
	// DiaDiem fake2 = new DiaDiem();
	//
	// fake2.ten = "Osaka Ramen 2 ";
	// fake2.diaChi =
	// "18 ThaÌ�i VÄƒn Lung, P.BÃªÌ�n NgheÌ�, Q.1, Ho Chi Minh City, Vietnam";
	// fake2.diemDanhGia = 123;
	// fake2.moTa = "QuÃ¡n Äƒn Nháº­t. MÃ¬ Nháº­t";
	// fake2.toaDo = new LatLng(10.780023, 106.702282);
	//
	// DiaDiem fake3 = new DiaDiem();
	//
	// fake3.ten = "Osaka Ramen 3";
	// fake3.diaChi =
	// "18 ThaÌ�i VÄƒn Lung, P.BÃªÌ�n NgheÌ�, Q.1, Ho Chi Minh City, Vietnam";
	// fake3.diemDanhGia = 123;
	// fake3.moTa = "QuÃ¡n Äƒn Nháº­t. MÃ¬ Nháº­t";
	// fake3.toaDo = new LatLng(10.780023, 106.702282);
	//
	// DiaDiem fake4 = new DiaDiem();
	//
	// fake4.ten = "Osaka Ramen 4";
	// fake4.diaChi =
	// "18 ThaÌ�i VÄƒn Lung, P.BÃªÌ�n NgheÌ�, Q.1, Ho Chi Minh City, Vietnam";
	// fake4.diemDanhGia = 123;
	// fake4.moTa = "QuÃ¡n Äƒn Nháº­t. MÃ¬ Nháº­t";
	// fake4.toaDo = new LatLng(10.780023, 106.702282);
	//
	// DiaDiem fake5 = new DiaDiem();
	//
	// fake5.ten = "Osaka Ramen 5";
	// fake5.diaChi =
	// "18 ThaÌ�i VÄƒn Lung, P.BÃªÌ�n NgheÌ�, Q.1, Ho Chi Minh City, Vietnam";
	// fake5.diemDanhGia = 123;
	// fake5.moTa = "QuÃ¡n Äƒn Nháº­t. MÃ¬ Nháº­t";
	// fake5.toaDo = new LatLng(10.780023, 106.702282);
	//
	// //
	//
	// list.add(fake1);
	// list.add(fake2);
	// list.add(fake3);
	// list.add(fake4);
	// list.add(fake5);
	//
	// return list;
	// }

	public static ArrayList<BinhLuan> FakeListBinhLuan() {

		ArrayList<BinhLuan> list = new ArrayList<BinhLuan>();

		BinhLuan fake1 = new BinhLuan();

		fake1.noiDung = "ccc";
		fake1.tenNguoiDang = "Hoa Phat";
		fake1.thoiGianDang = new Date(351511224);

		BinhLuan fake2 = new BinhLuan();

		fake2.noiDung = "ccc";
		fake2.tenNguoiDang = "Hoa Phat";
		fake2.thoiGianDang = new Date(351511224);

		BinhLuan fake3 = new BinhLuan();

		fake3.noiDung = "ccc";
		fake3.tenNguoiDang = "Hoa Phat";
		fake3.thoiGianDang = new Date(351511224);

		BinhLuan fake4 = new BinhLuan();

		fake4.noiDung = "ccc";
		fake4.tenNguoiDang = "Hoa Phat";
		fake4.thoiGianDang = new Date(351511224);

		BinhLuan fake5 = new BinhLuan();

		fake5.noiDung = "ccc";
		fake5.tenNguoiDang = "Hoa Phat";
		fake5.thoiGianDang = new Date(351511224);

		//

		list.add(fake1);
		list.add(fake2);
		list.add(fake3);
		list.add(fake4);
		list.add(fake5);

		return list;
	}
}
