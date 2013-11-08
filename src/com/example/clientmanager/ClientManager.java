package com.example.clientmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.Object.BinhLuan;
import com.example.Object.ChiTietDichVu;
import com.example.Object.DiaDiem;
import com.example.Object.TaiKhoan;
import com.example.jsonparser.JSONParser;

/**
 * 
 * ClientManager
 * 
 * @author Hoa Phat
 * @since 2013/11/8
 * 
 * 
 */
public class ClientManager {

	// URI
	public static final String REGISTER_URL = "http://wegorest.viemde.cloudbees.net/user/register";

	/**
	 * Request server to get JSONObject as a response
	 * 
	 * @param uri
	 *            Uri of webservice
	 * @param input
	 *            jsonObject - send to server
	 * @return jsonObject - recieve from server
	 * @author Hoa Phat
	 * @since 2013/11/8
	 */
	public static JSONObject RequestServerToGetJSONObject(String uri,
			JSONObject input) throws IllegalStateException, IOException,
			JSONException {

		JSONObject jsonObjectOutput;
		HttpClient client;
		HttpPost post;
		HttpResponse response;
		HttpEntity entity;
		InputStream is;

		client = new DefaultHttpClient();
		post = new HttpPost(uri);
		post.addHeader(new BasicHeader("Content-Type", "application/json"));
		post.setEntity(new StringEntity(input.toString()));

		// request server to get data
		response = client.execute(post);
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

	/**
	 * Request server to get JSONArray as a response
	 * 
	 * @param uri
	 *            Uri of webservice
	 * @param input
	 *            jsonObject - send to server
	 * @return jsonArray - recieve from server
	 * @author Hoa Phat
	 * @since 2013/11/8
	 */
	public static JSONArray RequestServerToGetJSONArray(String uri,
			JSONObject input) throws IllegalStateException, IOException,
			JSONException {

		JSONArray jsonArrayOutput;
		HttpClient client;
		HttpPost post;
		HttpResponse response;
		HttpEntity entity;
		InputStream is;

		client = new DefaultHttpClient();
		post = new HttpPost(uri);
		post.addHeader(new BasicHeader("Content-Type", "application/json"));
		post.setEntity(new StringEntity(input.toString()));

		// request server to get data
		response = client.execute(post);
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

		jsonArrayOutput = new JSONArray(sb.toString());

		return jsonArrayOutput;
	}

	public static boolean RequestToRegisterAccount(TaiKhoan taiKhoan)
			throws IllegalStateException, IOException, JSONException {

		// request server
		JSONObject objResponse = ClientManager.RequestServerToGetJSONObject(
				REGISTER_URL, JSONParser.getJSONFromObject(taiKhoan));

		Boolean result = objResponse.getBoolean("success");

		return result;
	}

	public static ArrayList<DiaDiem> RequestToGetListDiaDiemYeuThich(
			String userID) {
		ArrayList<DiaDiem> listDiaDiem = new ArrayList<DiaDiem>();

		return listDiaDiem;
	}

	public static ArrayList<BinhLuan> RequestToGetListBinhLuan(String diadiemID) {
		ArrayList<BinhLuan> listBinhLuan = new ArrayList<BinhLuan>();

		return listBinhLuan;
	}

	public static ArrayList<ChiTietDichVu> RequestToGetListChiTietDichVu(
			String diadiemID) {
		ArrayList<ChiTietDichVu> listChiTietDichVu = new ArrayList<ChiTietDichVu>();

		return listChiTietDichVu;
	}

	public static DiaDiem RequestToGetDiaDiemChiTiet(String diadiemID) {
		DiaDiem diadiem = new DiaDiem();

		return diadiem;
	}

}
