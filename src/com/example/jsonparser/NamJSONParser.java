package com.example.jsonparser;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Object.DiaDiem;
import com.example.ultils.StringTagJSON;

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
}
