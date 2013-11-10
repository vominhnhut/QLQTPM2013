package com.example.jsonparser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Object.BinhLuan;
import com.example.Object.DiaDiem;
import com.example.ultils.StringTagJSON;
/**
 * 
 * @author vhnam
 * Name: Vo Hoai Nam
 * Email: vhnam.7@gmail.com
 * Phone: 0985272003
 * Skype: vhnam.7
 */
public class NamJSONParser {
	public static ArrayList<DiaDiem> getListDiaDiemFromJsonOject(JSONObject jsonObject) throws JSONException {
		ArrayList<DiaDiem> diaDiems = new ArrayList<DiaDiem>();
		String success = jsonObject.getString(StringTagJSON.TAG_SUCCESS);
		
		if (success.equals("true")) {
			JSONArray content = jsonObject.getJSONArray(StringTagJSON.TAG_CONTENTString);
			
			for (int i = 0; i < content.length(); i++) {
				JSONObject c = content.getJSONObject(i);
				DiaDiem diaDiem = new DiaDiem();
				
				diaDiem.id = c.getString(StringTagJSON.TAG_MA_DU_LIEU);
				diaDiem.ten = c.getString(StringTagJSON.TAG_TEN_DIA_DIEM);
				diaDiem.diaChi = c.getString(StringTagJSON.TAG_SO_NHA) + " " 
						+ c.getString(StringTagJSON.TAG_TEN_DUONG) + ", "
						+ c.getString(StringTagJSON.TAG_TEN_PHUONG) + ", "
						+ c.getString(StringTagJSON.TAG_TEN_TINH_THANH);
				diaDiem.latitude = c.getDouble(StringTagJSON.TAG_VI_DO);
				diaDiem.longitude = c.getDouble(StringTagJSON.TAG_KINH_DO);
				diaDiem.moTa = c.getString(StringTagJSON.TAG_CHU_THICH);
				
				diaDiems.add(diaDiem);
			}
		}
	
		return diaDiems;
	}
	
	public static ArrayList<BinhLuan> getListBinhLuanFromJsonOject(JSONObject jsonObject) throws JSONException {
		ArrayList<BinhLuan> binhLuans = new ArrayList<BinhLuan>();
		String success = jsonObject.getString(StringTagJSON.TAG_SUCCESS);
		
		if (success.equals("true")) {
			JSONArray content = jsonObject.getJSONArray(StringTagJSON.TAG_CONTENTString);
			
			for (int i = 0; i < content.length(); i++) {
				JSONObject c = content.getJSONObject(i);
				BinhLuan binhluan = new BinhLuan();
				
				binhluan.id = c.getString(StringTagJSON.TAG_MA_DU_LIEU);
				binhluan.tenNguoiDang = c.getString(StringTagJSON.TAG_TEN_NGUOI_DUNG);
//				binhluan.thoiGianDang = Date.parse(c.getString(StringTagJSON.TAG_THOI_GIAN_DANG)); 
				//ham bi xoa, chua biet cau truc
				
				binhluan.noiDung = c.getString(StringTagJSON.TAG_NOI_DUNG);
				
				binhLuans.add(binhluan);
			}
		}
	
		return binhLuans;
	}
}
