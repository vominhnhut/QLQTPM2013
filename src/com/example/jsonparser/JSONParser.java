package com.example.jsonparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
				"UTF-8"), 8);
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

	public static DiaDiem getDiaDiemFromJSONObject(JSONObject obj)
			throws JSONException {

		DiaDiem diadiem = new DiaDiem();

		diadiem.ten = obj.getString("ten_dia_diem");
		diadiem.diemDanhGia = obj.getInt("so_luot_thich");
		diadiem.latitude = obj.getDouble("vi_do");
		diadiem.longitude = obj.getDouble("kinh_do");

		// dia chi
		String so_nha = obj.getString("so_nha");
		String ten_duong = obj.getString("ten_duong");
		String ten_phuong = obj.getString("ten_phuong");
		String ten_quan_huyen = obj.getString("ten_quan_huyen");
		String ten_tinh_thanh = obj.getString("ten_tinh_thanh");
		//

		diadiem.diaChi = so_nha + " " + ten_duong + ", " + ten_phuong + ", "
				+ ten_quan_huyen + ", " + ten_tinh_thanh + ".";

		return diadiem;

	}

	// lay danh sach dia diem tom tat tu jsonarray
	public static ArrayList<DiaDiem> parseListDiaDiemTomTatFromJSON(
			JSONArray array) throws JSONException {

		ArrayList<DiaDiem> listDiaDiem = new ArrayList<DiaDiem>();

		DiaDiem diadiem = null;
		for (int i = 0; i < array.length(); i++) {
			diadiem = JSONParser.getDiaDiemFromJSONObject(array
					.getJSONObject(i));
			listDiaDiem.add(diadiem);
		}

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

}
