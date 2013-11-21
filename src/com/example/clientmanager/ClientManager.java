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
	private static final String REGISTER_URL = "http://wegorest.viemde.cloudbees.net/user/register";
	private static final String LOGIN_URL = "http://wegorest.viemde.cloudbees.net/user/login";
	private static final String LOGOUT_URL = "http://wegorest.viemde.cloudbees.net/user/logout";
	private static final String CHANGEPASSWORD_URL = "http://wegorest.viemde.cloudbees.net/user/changepassw";
	private static final String POSTCOMMENT_URL = "http://wegorest.viemde.cloudbees.net/place/comment";
	private static final String FINDLOCATION_URL = "http://wegorest.viemde.cloudbees.net/place/find";
	private static final String LISTCOMMENT_URL = "http://wegorest.viemde.cloudbees.net/place/comment";
	private static final String LIKEORUNLIKE_URL = "http://wegorest.viemde.cloudbees.net/place/feeling";
	private static final String SERVICESLIST_URL = "http://wegorest.viemde.cloudbees.net/place/details";
	private static final String FAVOURITELOCATION_URL = "http://wegorest.viemde.cloudbees.net/place/lovedplace";

	public static int next_Index_LoadedBinhLuan = 0;
	public static boolean isStop_LoadListBinhLuan = false;
	public static int max_Index_LoadedDiaDiem = 0;
	public static boolean isStop_LoadListDiaDiem = false;

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
		Log.e("hoaphat", changePasswordURL.toString());
		JSONObject inputObj = new JSONObject();
		inputObj.put(StringTagJSON.TAG_MAT_KHAU_CU, oldPass);
		inputObj.put(StringTagJSON.TAG_MAT_KHAU_MOI, newPass);

		Log.e("hoaphat", inputObj.toString());
		// request server
		response = ClientManager.RequestServerByHttpPost(changePasswordURL,
				inputObj);

		statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			responsedJSONObj = JSONParser
					.getJSONObjectFromHttpResponse(response);

			Log.e("hoaphat", responsedJSONObj.toString());

			boolean success = responsedJSONObj
					.getBoolean(StringTagJSON.TAG_SUCCESS);

			if (success) {
				result.success = true;
			} else {
				result.success = false;
				result.content = "Fail to change password";
			}

		} else {

			result.success = false;
			result.content = "Problem with connecting server";
		}

		Log.e("hoaphat", result.success + "");
		return result;
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
		inputObj.put(StringTagJSON.TAG_COMMENT, comment);

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
			ArrayList<BinhLuan> listBinhLuan, int index) throws JSONException,
			ClientProtocolException, IOException {

		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;
		String listCommentURL;
		result = new ResponsedResult();

		String encodedDiaDiemID = URLEncoder.encode(diadiemID, "UTF-8");
		String encodedToken = URLEncoder.encode(Constants.LOGINUSER_TOKEN,
				"UTF-8");

		listCommentURL = LISTCOMMENT_URL + "?token=" + encodedToken + "&index="
				+ index + "&ma_du_lieu=" + encodedDiaDiemID;

		Log.e("hoaphat", listCommentURL);

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
				if (array.length() == 0 || array.length() < 20) {
					isStop_LoadListBinhLuan = true;
				}
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

	public static ResponsedResult RequestToGetListChiTietDichVu(
			String diadiemID, ArrayList<ChiTietDichVu> listChiTietDichVu)
			throws JSONException, ClientProtocolException, IOException {

		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;
		String serviceListURL;
		result = new ResponsedResult();

		String encodedDiaDiemID = URLEncoder.encode(diadiemID, "UTF-8");
		String encodedToken = URLEncoder.encode(Constants.LOGINUSER_TOKEN,
				"UTF-8");

		serviceListURL = SERVICESLIST_URL + "?token=" + encodedToken
				+ "&ma_du_lieu=" + encodedDiaDiemID;

		Log.e("hoaphat", serviceListURL.toString());

		response = ClientManager.RequestServerByHttpGet(serviceListURL);

		statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			responsedJSONObj = JSONParser
					.getJSONObjectFromHttpResponse(response);

			Log.e("hoaphat", responsedJSONObj.toString());

			boolean success = responsedJSONObj
					.getBoolean(StringTagJSON.TAG_SUCCESS);
			if (success) {

				result.success = true;

				JSONArray array = responsedJSONObj
						.getJSONArray(StringTagJSON.TAG_CONTENTString);

				Log.e("hoaphat", array.length() + "");
				listChiTietDichVu.addAll(JSONParser
						.getListChiTietDichVuFromJSON(array));

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
			String keywords, ArrayList<DiaDiem> listDiaDiem, int index)
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

		findLocation = FINDLOCATION_URL + "?token=" + encodedToken + "&index="
				+ index + "&query=" + encodedQuery;
		Log.e("hoaphat", findLocation.toString());

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

				if (array.length() < 20) {
					ClientManager.isStop_LoadListDiaDiem = true;
				}

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

	public static ResponsedResult RequestToGetListDiaDiemYeuThich(
			ArrayList<DiaDiem> listDiaDiem, int index)
			throws IllegalStateException, IOException, JSONException {

		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;
		String findLocation;
		result = new ResponsedResult();

		String encodedToken = URLEncoder.encode(Constants.LOGINUSER_TOKEN,
				"UTF-8");

		findLocation = FAVOURITELOCATION_URL + "?token=" + encodedToken
				+ "&index=0";

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

	public static ResponsedResult RequestToLikeOrUnlikeDiaDiem(String token,
			String diadiemID, boolean like) throws JSONException,
			IllegalStateException, IOException {

		String likeOrUnlikeURL;
		String tokenEncoded;
		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;

		result = new ResponsedResult();

		tokenEncoded = URLEncoder.encode(token, "UTF-8");

		likeOrUnlikeURL = LIKEORUNLIKE_URL + "?token=" + tokenEncoded;

		JSONObject inputObj = new JSONObject();
		inputObj.put(StringTagJSON.TAG_MA_DU_LIEU, diadiemID);
		inputObj.put(StringTagJSON.TAG_LIKE, ((Boolean) like).toString());

		// request server
		response = ClientManager.RequestServerByHttpPost(likeOrUnlikeURL,
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
				result.content = "Fail to like/unlike location.";
			}

		} else {

			result.success = false;
			result.content = "Problem with connecting server";
		}

		return result;

	}

	public static ResponsedResult RequestToAddOrRemoveFavouriteDiaDiem(
			String token, String diadiemID, boolean save) throws JSONException,
			IllegalStateException, IOException {

		String likeOrUnlikeURL;
		String tokenEncoded;
		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;

		result = new ResponsedResult();

		tokenEncoded = URLEncoder.encode(token, "UTF-8");

		likeOrUnlikeURL = FAVOURITELOCATION_URL + "?token=" + tokenEncoded;

		JSONObject inputObj = new JSONObject();
		inputObj.put(StringTagJSON.TAG_MA_DU_LIEU, diadiemID);

		// request server
		response = ClientManager.RequestServerByHttpPost(likeOrUnlikeURL,
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
				result.content = "Fail to add favourite location.";
			}

		} else {

			result.success = false;
			result.content = "Problem with connecting server";
		}

		return result;

	}

	public static ResponsedResult RequestToGetLikeOrNotLikeDiaDiem(
			String token, String diadiemID) throws ClientProtocolException,
			IOException, IllegalStateException, JSONException {

		String likeOrUnlikeURL;
		String tokenEncoded;
		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;

		result = new ResponsedResult();

		tokenEncoded = URLEncoder.encode(token, "UTF-8");

		likeOrUnlikeURL = LIKEORUNLIKE_URL + "?token=" + tokenEncoded
				+ "&ma_du_lieu=" + diadiemID;

		// request server
		response = ClientManager.RequestServerByHttpGet(likeOrUnlikeURL);

		statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			responsedJSONObj = JSONParser
					.getJSONObjectFromHttpResponse(response);

			boolean success = responsedJSONObj
					.getBoolean(StringTagJSON.TAG_SUCCESS);

			if (success) {
				result.success = true;

				JSONObject obj = responsedJSONObj
						.getJSONObject(StringTagJSON.TAG_CONTENTString);

				boolean liked = obj.getBoolean(StringTagJSON.TAG_LIKE);

				if (liked) {
					result.content2 = Boolean.TRUE;
				} else {
					result.content2 = Boolean.FALSE;
				}

			} else {
				result.success = false;
				result.content = "Fail get like";
			}

		} else {

			result.success = false;
			result.content = "Problem with connecting server";
		}

		return result;

	}

	public static ResponsedResult RequestToGetSaveOrNotSaveDiaDiem(
			String token, String diadiemID) throws ClientProtocolException,
			IOException, IllegalStateException, JSONException {

		String likeOrUnlikeURL;
		String tokenEncoded;
		ResponsedResult result;
		JSONObject responsedJSONObj;
		HttpResponse response;
		int statusCode;

		result = new ResponsedResult();

		tokenEncoded = URLEncoder.encode(token, "UTF-8");

		likeOrUnlikeURL = FAVOURITELOCATION_URL + "?token=" + tokenEncoded
				+ "&ma_du_lieu=" + diadiemID;

		Log.e("hoaphat", likeOrUnlikeURL.toString());

		// request server
		response = ClientManager.RequestServerByHttpGet(likeOrUnlikeURL);

		statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			responsedJSONObj = JSONParser
					.getJSONObjectFromHttpResponse(response);
			Log.e("hoaphat", responsedJSONObj.toString());
			boolean success = responsedJSONObj
					.getBoolean(StringTagJSON.TAG_SUCCESS);

			if (success) {
				result.success = true;

				JSONObject obj = responsedJSONObj
						.getJSONObject(StringTagJSON.TAG_CONTENTString);

				boolean liked = obj.getBoolean(StringTagJSON.TAG_LIKE);

				if (liked) {
					result.content2 = Boolean.TRUE;
				} else {
					result.content2 = Boolean.FALSE;
				}

			} else {
				result.success = false;
				result.content = "Fail get saved";
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

	// ############

	public static int GetMaxIndexDiaDiem(ArrayList<DiaDiem> list) {

		int index = 0;

		for (DiaDiem dd : list) {
			int id = Integer.parseInt(dd.id);
			if (id > index) {
				index = id;
			}
		}

		return index;
	}
}
