package com.example.clientmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.Object.BinhLuan;
import com.example.Object.ChiTietDichVu;
import com.example.Object.DiaDiem;
import com.example.Object.TaiKhoan;
import com.example.jsonparser.JSONParser;
import com.example.ultils.Constants;

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
	public static final String LOGIN_URL = "http://wegorest.viemde.cloudbees.net/user/login";
	public static final String LOGOUT_URL = "http://wegorest.viemde.cloudbees.net/user/logout";
	public static final String CHANGEPASSWORD_URL = "http://wegorest.viemde.cloudbees.net/user/changepassw";
	public static final String POSTCOMMENT_URL = "http://wegorest.viemde.cloudbees.net/user/comment";

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
	public static JSONObject RequestServerToGetJSONObjectByHttpPost(String uri,
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

	public static JSONObject RequestServerToGetJSONObjectByHttpGet(String uri)
			throws IllegalStateException, IOException, JSONException {

		JSONObject jsonObjectOutput;
		HttpClient client;
		HttpGet get;
		HttpResponse response;
		HttpEntity entity;
		InputStream is;

		client = new DefaultHttpClient();
		get = new HttpGet(uri);
		get.addHeader(new BasicHeader("Content-Type", "application/json"));

		// request server to get data
		response = client.execute(get);
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

	public static JSONObject RequestServerToGetJSONObjectByHttpDelete(String uri)
			throws IllegalStateException, IOException, JSONException {

		JSONObject jsonObjectOutput;
		HttpClient client;
		HttpDelete get;
		HttpResponse response;
		HttpEntity entity;
		InputStream is;

		client = new DefaultHttpClient();
		get = new HttpDelete(uri);
		get.addHeader(new BasicHeader("Content-Type", "application/json"));

		// request server to get data
		response = client.execute(get);
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

	// ############################################################################

	public static boolean RequestToRegisterAccount(TaiKhoan taiKhoan)
			throws IllegalStateException, IOException, JSONException {

		// request server
		JSONObject objResponse = ClientManager
				.RequestServerToGetJSONObjectByHttpPost(REGISTER_URL,
						JSONParser.getJSONFromObject(taiKhoan));

		Boolean result = objResponse.getBoolean("success");

		return result;
	}

	public static boolean RequestToLogIn(String username, String password)
			throws IllegalStateException, IOException, JSONException {

		String user = URLEncoder.encode(username, "UTF-8");
		String pass = URLEncoder.encode(password, "UTF-8");

		String LoginURL = LOGIN_URL + "?" + "ten_tai_khoan=" + user + "&"
				+ "mat_khau=" + pass;

		// request server
		JSONObject objResponse = ClientManager
				.RequestServerToGetJSONObjectByHttpGet(LoginURL);

		Boolean result = objResponse.getBoolean("success");
		Constants.LOGINUSER_TOKEN = (String) objResponse.get("token");
		Log.e("hoaphat", Constants.LOGINUSER_TOKEN);
		return result;
	}

	public static boolean RequestToLogOut(String token)
			throws IllegalStateException, IOException, JSONException {

		String tk = URLEncoder.encode(token, "UTF-8");

		String LogoutURL = LOGOUT_URL + "?" + "token=" + tk;

		// request server
		JSONObject objResponse = ClientManager
				.RequestServerToGetJSONObjectByHttpDelete(LogoutURL);

		Boolean result = objResponse.getBoolean("success");

		return result;
	}

	public static boolean RequestToChangePassword(String token, String oldPass,
			String newPass) throws IllegalStateException, IOException,
			JSONException {

		String tk = URLEncoder.encode(token, "UTF-8");

		String changePasswordURL = CHANGEPASSWORD_URL + "?" + "token=" + tk;

		JSONObject inputObj = new JSONObject();
		inputObj.put("mat_khau_cu", oldPass);
		inputObj.put("mat_khau_moi", newPass);
		// request server
		JSONObject objResponse = ClientManager
				.RequestServerToGetJSONObjectByHttpPost(changePasswordURL,
						inputObj);

		Log.e("hoaphat", objResponse.toString());
		Boolean result = objResponse.getBoolean("success");

		return result;
	}

	public static ArrayList<DiaDiem> RequestToGetListDiaDiemYeuThich(
			String userID) {
		ArrayList<DiaDiem> listDiaDiem = new ArrayList<DiaDiem>();

		return listDiaDiem;
	}

	public static boolean RequestToPostComment(String token, String placeID,
			String comment) throws IllegalStateException, IOException,
			JSONException {

		String postCommentURL = POSTCOMMENT_URL + "?token=" + token;

		JSONObject inputObj = new JSONObject();
		inputObj.put("ma_du_lieu", placeID);
		inputObj.put("comment", comment);
		// request server
		JSONObject objResponse = ClientManager
				.RequestServerToGetJSONObjectByHttpPost(postCommentURL,
						inputObj);

		Boolean result = objResponse.getBoolean("success");

		return result;
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
