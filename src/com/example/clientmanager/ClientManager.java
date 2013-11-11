package com.example.clientmanager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Object.BinhLuan;
import com.example.Object.DiaDiem;
import com.example.Object.TaiKhoan;
import com.example.jsonparser.JSONParser;
import com.example.ultils.Constants;
import com.example.ultils.StringTagJSON;

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
	public static final String FINDLOCATION_URL = "http://wegorest.viemde.cloudbees.net/place/find";
	public static final String LISTCOMMENT_URL = "http://wegorest.viemde.cloudbees.net/place/find";

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
	private static HttpResponse RequestServerByHttpPost(String uri,
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

	private static HttpResponse RequestServerByHttpGet(String uri)
			throws ClientProtocolException, IOException {

		HttpClient client;
		HttpGet get;
		HttpResponse response;

		client = new DefaultHttpClient();
		get = new HttpGet(uri);
		get.addHeader(new BasicHeader("Content-Type",
				"application/json;charset=utf-8"));

		// request server to get data
		response = client.execute(get);

		return response;
	}

	private static HttpResponse RequestServerByHttpDelete(String uri)
			throws IllegalStateException, IOException, JSONException {

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
	private static HttpResponse RequestServer(String uri, JSONObject input)
			throws IllegalStateException, IOException, JSONException {

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

		response = ClientManager.RequestServerByHttpPost(REGISTER_URL, accObj);

		statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			responsedJSONObj = JSONParser
					.getJSONObjectFromHttpResponse(response);

			boolean success = responsedJSONObj
					.getBoolean(StringTagJSON.TAG_SUCCESS);
			if (success) {
				result.success = true;
			} else {
				result.success = false;
				result.content = responsedJSONObj
						.getString(StringTagJSON.TAG_REASON);
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
		accObj.put(StringTagJSON.TAG_TEN_TAI_KHOAN, username);
		accObj.put(StringTagJSON.TAG_MAT_KHAU, password);

		// request server
		response = ClientManager.RequestServerByHttpPost(LOGIN_URL, accObj);

		statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			responsedJSONObj = JSONParser
					.getJSONObjectFromHttpResponse(response);

			boolean success = responsedJSONObj
					.getBoolean(StringTagJSON.TAG_SUCCESS);
			if (success) {

				result.success = true;
				Constants.LOGINUSER_TOKEN = (String) responsedJSONObj
						.get("token");
			} else {
				result.success = false;
				result.content = "Fail to login";
			}

		} else {

			result.success = false;
			result.content = "Problem with connecting server";
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
		response = ClientManager.RequestServerByHttpDelete(LogoutURL);

		statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			responsedJSONObj = JSONParser
					.getJSONObjectFromHttpResponse(response);

			boolean success = responsedJSONObj
					.getBoolean(StringTagJSON.TAG_SUCCESS);
			if (success) {
				result.success = true;
			} else {
				result.success = false;
				result.content = "Fail to logout";
			}

		} else {

			result.success = false;
			result.content = "Problem with connecting server";
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
		inputObj.put(StringTagJSON.TAG_MAT_KHAU_CU, oldPass);
		inputObj.put(StringTagJSON.TAG_MAT_KHAU_MOI, newPass);
		// request server
		response = ClientManager.RequestServerByHttpPost(changePasswordURL,
				inputObj);

		statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			responsedJSONObj = JSONParser
					.getJSONObjectFromHttpResponse(response);

			boolean success = responsedJSONObj
					.getBoolean(StringTagJSON.TAG_SUCCESS);
			String content = responsedJSONObj
					.getString(StringTagJSON.TAG_REASON);

			if (success) {
				result.success = true;
			} else {
				result.success = false;
				result.content = content;
			}

		} else {

			result.success = false;
			result.content = "Problem with connecting server";
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
		inputObj.put(StringTagJSON.TAG_MA_DU_LIEU, locationID);
		inputObj.put("comment", comment);
		// request server
		response = ClientManager.RequestServerByHttpPost(postCommentURL,
				inputObj);

		statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			responsedJSONObj = JSONParser
					.getJSONObjectFromHttpResponse(response);

			boolean success = responsedJSONObj
					.getBoolean(StringTagJSON.TAG_SUCCESS);

			if (success) {
				result.success = true;
			} else {
				result.success = false;
				result.content = "Fail to send comment";
			}

		} else {

			result.success = false;
			result.content = "Problem with connecting server";
		}

		return result;
	}

	public static ResponsedResult RequestToGetListBinhLuan(String diadiemID,
			ArrayList<BinhLuan> listBinhLuan) throws JSONException,
			ClientProtocolException, IOException {

		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;
		String listCommentURL;
		result = new ResponsedResult();

		String encodedDiaDiemID = URLEncoder.encode(diadiemID, "UTF-8");

		listCommentURL = LISTCOMMENT_URL + "?token=" + encodedDiaDiemID
				+ "&index=0";

		response = ClientManager.RequestServerByHttpGet(listCommentURL);

		statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			responsedJSONObj = JSONParser
					.getJSONObjectFromHttpResponse(response);

			boolean success = responsedJSONObj
					.getBoolean(StringTagJSON.TAG_SUCCESS);
			if (success) {

				result.success = true;

				JSONArray array = responsedJSONObj
						.getJSONArray(StringTagJSON.TAG_CONTENTString);

				listBinhLuan.addAll(JSONParser.getListBinhLuanFromJSON(array));

			} else {
				result.success = false;
				result.content = "Result not found";
			}

		} else {

			result.success = false;
			result.content = "Problem with connecting server";
		}

		return result;
	}

	public static ResponsedResult RequestToGetFindDiaDiemByKeywords(
			String keywords, ArrayList<DiaDiem> listDiaDiem)
			throws IllegalStateException, IOException, JSONException {

		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;
		String findLocation;
		result = new ResponsedResult();

		String encodedToken = URLEncoder.encode(Constants.LOGINUSER_TOKEN,
				"UTF-8");
		String encodedQuery = URLEncoder.encode(keywords, "UTF-8");

		findLocation = FINDLOCATION_URL + "?token=" + encodedToken + "&index=0"
				+ "&query=" + encodedQuery;

		response = ClientManager.RequestServerByHttpGet(findLocation);

		statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			responsedJSONObj = JSONParser
					.getJSONObjectFromHttpResponse(response);

			boolean success = responsedJSONObj
					.getBoolean(StringTagJSON.TAG_SUCCESS);
			if (success) {

				result.success = true;

				JSONArray array = responsedJSONObj
						.getJSONArray(StringTagJSON.TAG_CONTENTString);

				listDiaDiem.addAll(JSONParser
						.getListDiaDiemTomTatFromJSON(array));

			} else {
				result.success = false;
				result.content = "Result not found";
			}

		} else {

			result.success = false;
			result.content = "Problem with connecting server";
		}

		return result;
	}

	public static DiaDiem RequestToGetDiaDiemChiTiet(String diadiemID) {
		DiaDiem diadiem = new DiaDiem();

		return diadiem;
	}

}
