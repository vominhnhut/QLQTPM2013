package com.example.clientmanager;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
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
	private static HttpResponse RequestServerToGetJSONObjectByHttpPost(
			String uri, JSONObject input) throws IllegalStateException,
			IOException, JSONException {

		HttpClient client;
		HttpPost post;
		HttpResponse response;

		client = new DefaultHttpClient();
		post = new HttpPost(uri);
		post.addHeader(new BasicHeader("Content-Type", "application/json"));
		post.setEntity(new StringEntity(input.toString()));

		// request server to get data
		response = client.execute(post);

		return response;
	}

	private static HttpResponse RequestServerToGetJSONObjectByHttpGet(String uri)
			throws ClientProtocolException, IOException {

		HttpClient client;
		HttpGet get;
		HttpResponse response;

		client = new DefaultHttpClient();
		get = new HttpGet(uri);
		get.addHeader(new BasicHeader("Content-Type", "application/json"));

		// request server to get data
		response = client.execute(get);

		return response;
	}

	private static HttpResponse RequestServerToGetJSONObjectByHttpDelete(
			String uri) throws IllegalStateException, IOException,
			JSONException {

		HttpClient client;
		HttpDelete get;
		HttpResponse response;

		client = new DefaultHttpClient();
		get = new HttpDelete(uri);
		get.addHeader(new BasicHeader("Content-Type", "application/json"));

		// request server to get data
		response = client.execute(get);

		return response;
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
	private static HttpResponse RequestServerToGetJSONArray(String uri,
			JSONObject input) throws IllegalStateException, IOException,
			JSONException {

		HttpClient client;
		HttpPost post;
		HttpResponse response;

		client = new DefaultHttpClient();
		post = new HttpPost(uri);
		post.addHeader(new BasicHeader("Content-Type", "application/json"));
		post.setEntity(new StringEntity(input.toString()));

		// request server to get data
		response = client.execute(post);

		return response;
	}

	// ############################################################################

	public static ResponsedResult RequestToRegisterAccount(TaiKhoan taiKhoan)
			throws IllegalStateException, IOException, JSONException {

		ResponsedResult result;
		JSONObject accObj;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;

		result = new ResponsedResult();

		accObj = JSONParser.getJSONFromObject(taiKhoan);

		response = ClientManager.RequestServerToGetJSONObjectByHttpPost(
				REGISTER_URL, accObj);

		statusCode = response.getStatusLine().getStatusCode();

		responsedJSONObj = JSONParser.getJSONObjectFromHttpResponse(response);

		if (statusCode == 200) {
			boolean success = responsedJSONObj.getBoolean("success");
			if (success) {
				result.success = true;
			} else {
				result.success = false;
				result.content = responsedJSONObj.getString("reason");
			}
		} else {

			result.success = false;
			result.content = "Can not connect Wego Server.";
		}

		return result;
	}

	public static ResponsedResult RequestToLogIn(String username,
			String password) throws IllegalStateException, IOException,
			JSONException {

		ResponsedResult result;
		JSONObject accObj;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;

		result = new ResponsedResult();

		accObj = new JSONObject();
		accObj.put("ten_tai_khoan", username);
		accObj.put("mat_khau", password);

		// request server
		response = ClientManager.RequestServerToGetJSONObjectByHttpPost(
				LOGIN_URL, accObj);

		statusCode = response.getStatusLine().getStatusCode();

		responsedJSONObj = JSONParser.getJSONObjectFromHttpResponse(response);

		if (statusCode == 200) {
			boolean success = responsedJSONObj.getBoolean("success");
			if (success) {

				result.success = true;
				// Constants.LOGINUSER_TOKEN = (String) responsedJSONObj
				// .get("token");
			} else {
				result.success = false;
				result.content = "Fail to login";
			}

		} else {

			result.success = false;
			result.content = "Can not connect Wego Server.";
		}

		return result;
	}

	public static ResponsedResult RequestToLogOut(String token)
			throws IllegalStateException, IOException, JSONException {

		String LogoutURL;
		String tokenEncoded;
		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;

		result = new ResponsedResult();

		tokenEncoded = URLEncoder.encode(token, "UTF-8");

		LogoutURL = LOGOUT_URL + "?" + "token=" + tokenEncoded;

		// request server
		response = ClientManager
				.RequestServerToGetJSONObjectByHttpDelete(LogoutURL);

		statusCode = response.getStatusLine().getStatusCode();

		responsedJSONObj = JSONParser.getJSONObjectFromHttpResponse(response);

		if (statusCode != 200) {
			boolean success = responsedJSONObj.getBoolean("success");
			if (success) {
				result.success = true;
			} else {
				result.success = false;
				result.content = "Fail to logout";
			}

		} else {

			result.success = false;
			result.content = "Can not connect Wego Server.";
		}

		return result;
	}

	public static ResponsedResult RequestToChangePassword(String token,
			String oldPass, String newPass) throws IllegalStateException,
			IOException, JSONException {

		String changePasswordURL;
		String tokenEncoded;
		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;

		result = new ResponsedResult();

		tokenEncoded = URLEncoder.encode(token, "UTF-8");

		changePasswordURL = CHANGEPASSWORD_URL + "?" + "token=" + tokenEncoded;

		JSONObject inputObj = new JSONObject();
		inputObj.put("mat_khau_cu", oldPass);
		inputObj.put("mat_khau_moi", newPass);
		// request server
		response = ClientManager.RequestServerToGetJSONObjectByHttpPost(
				changePasswordURL, inputObj);

		statusCode = response.getStatusLine().getStatusCode();

		responsedJSONObj = JSONParser.getJSONObjectFromHttpResponse(response);

		if (statusCode != 200) {

			boolean success = responsedJSONObj.getBoolean("success");
			String content = responsedJSONObj.getString("reason");

			if (success) {
				result.success = true;
			} else {
				result.success = false;
				result.content = content;
			}

		} else {

			result.success = false;
			result.content = "Can not connect Wego Server.";
		}

		return result;
	}

	public static ArrayList<DiaDiem> RequestToGetListDiaDiemYeuThich(
			String userID) {
		ArrayList<DiaDiem> listDiaDiem = new ArrayList<DiaDiem>();

		return listDiaDiem;
	}

	public static ResponsedResult RequestToPostComment(String token,
			String locationID, String comment) throws IllegalStateException,
			IOException, JSONException {

		String postCommentURL;
		String tokenEncoded;
		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;

		result = new ResponsedResult();

		tokenEncoded = URLEncoder.encode(token, "UTF-8");

		postCommentURL = POSTCOMMENT_URL + "?token=" + tokenEncoded;

		JSONObject inputObj = new JSONObject();
		inputObj.put("ma_du_lieu", locationID);
		inputObj.put("comment", comment);
		// request server
		response = ClientManager.RequestServerToGetJSONObjectByHttpPost(
				postCommentURL, inputObj);

		statusCode = response.getStatusLine().getStatusCode();

		responsedJSONObj = JSONParser.getJSONObjectFromHttpResponse(response);

		if (statusCode != 200) {

			boolean success = responsedJSONObj.getBoolean("success");

			if (success) {
				result.success = true;
			} else {
				result.success = false;
				result.content = "Fail to send comment";
			}

		} else {

			result.success = false;
			result.content = "Can not connect Wego Server.";
		}

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
