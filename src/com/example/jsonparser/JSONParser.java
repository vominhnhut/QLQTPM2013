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
import com.example.ultils.StringTagJSON;

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

		obj.put(StringTagJSON.TAG_TEN_TAI_KHOAN, taiKhoan.tenTaiKhoan);
		obj.put(StringTagJSON.TAG_MAT_KHAU, taiKhoan.matKhau);
		obj.put(StringTagJSON.TAG_HO_VA_TEN, taiKhoan.hoTen);
		obj.put(StringTagJSON.TAG_EMAIL, taiKhoan.email);
		obj.put(StringTagJSON.TAG_NGAY_SINH, taiKhoan.ngaySinh);

		return obj;
	}

	public static DiaDiem getDiaDiemFromJSONObject(JSONObject obj)
			throws JSONException {

		DiaDiem diadiem = new DiaDiem();

		diadiem.ten = obj.getString(StringTagJSON.TAG_TEN_DIA_DIEM);
		diadiem.diemDanhGia = obj.getInt(StringTagJSON.TAG_SO_LUOT_THICH);
		diadiem.latitude = obj.getDouble(StringTagJSON.TAG_VI_DO);
		diadiem.longitude = obj.getDouble(StringTagJSON.TAG_KINH_DO);

		// dia chi
		String so_nha = obj.getString(StringTagJSON.TAG_SO_NHA);
		String ten_duong = obj.getString(StringTagJSON.TAG_TEN_DUONG);
		String ten_phuong = obj.getString(StringTagJSON.TAG_TEN_PHUONG);
		String ten_quan_huyen = obj.getString(StringTagJSON.TAG_QUAN_HUYEN);
		String ten_tinh_thanh = obj.getString(StringTagJSON.TAG_TEN_TINH_THANH);
		//

		diadiem.diaChi = so_nha + " " + ten_duong + ", " + ten_phuong + ", "
				+ ten_quan_huyen + ", " + ten_tinh_thanh + ".";

		return diadiem;

	}

	public static BinhLuan getBinhLuanFromJSONObject(JSONObject obj)
			throws JSONException {

		BinhLuan binhluan = new BinhLuan();

		binhluan.tenNguoiDang = obj.getString(StringTagJSON.TAG_TEN_NGUOI_DUNG);
		binhluan.noiDung = obj.getString(StringTagJSON.TAG_NOI_DUNG);
		// binhluan.thoiGianDang = Date.parse(obj
		// .getString(StringTagJSON.TAG_THOI_GIAN_DANG));
		binhluan.id = obj.getString(StringTagJSON.TAG_MA_BINH_LUAN);

		return binhluan;

	}

	// lay danh sach dia diem tom tat tu jsonarray
	public static ArrayList<DiaDiem> getListDiaDiemTomTatFromJSON(
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

	public static ArrayList<BinhLuan> getListBinhLuanFromJSON(JSONArray array)
			throws JSONException {

		ArrayList<BinhLuan> listBinhLuan = new ArrayList<BinhLuan>();

		BinhLuan binhluan = null;
		for (int i = 0; i < array.length(); i++) {
			binhluan = JSONParser.getBinhLuanFromJSONObject(array
					.getJSONObject(i));
			listBinhLuan.add(binhluan);
		}

		return listBinhLuan;
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
