package wysApi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;
import wys.Business.BaseBusiness;
import wys.Business.UserBo;
import wys.Http.HttpApi;
import wys.Http.IHttpApi;
import wys.Modal.Handler.ObjectHandler;
import wys.Modal.Handler.UserHandler;

public class WysApi {

	private static final int SUCCESS = 0;
	private static final int ERROR = 1;

	private static final String HOST = "http://";
	// private static final String DOMAIN = "192.168.0.2/WYS/api/";
	private static final String DOMAIN = "129.107.148.205/WYS/api/";

	private IHttpApi mHttpApi;

	private final DefaultHttpClient mHttpClient = HttpApi.CreateHttpclient();

	public String GetUrl(String path) {
		return HOST + DOMAIN + path;
	}

	public WysApi() {
		this.mHttpApi = new HttpApi(mHttpClient);
	}

	public static int PostSignUp(UserBo user) {
		DefaultHttpClient client = new DefaultHttpClient();
		// HttpConnectionParams.setConnectionTimeout(client.getParams(),10000);

		HttpResponse response;
		JSONObject json = new JSONObject();
		String Url = "http://192.168.1.22/WYS/api/user/";

		try {
			HttpPost post = new HttpPost(Url);
			json.put("Username", user.get_username());
			json.put("Password", user.get_password());
			json.put("Email", user.get_email());
			json.put("RoleId", user.get_roleId());
			json.put("DomainId", user.get_domainId());

			StringEntity se = new StringEntity(json.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			post.setEntity(se);
			response = client.execute(post);
			if (response != null) {

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
					return SUCCESS;
				} else {
					return ERROR;
				}

			} else {
				return ERROR;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return ERROR;
		}

	}

	public int DoSignUp(UserBo user) {

		NameValuePair[] postBody = new NameValuePair[5];
		postBody[0] = (new BasicNameValuePair("Username", user.get_username()));
		postBody[1] = (new BasicNameValuePair("Password", user.get_password()));
		postBody[2] = (new BasicNameValuePair("Email", user.get_email()));
		postBody[3] = (new BasicNameValuePair("RoleId", Integer.toString(user
				.get_roleId())));
		postBody[4] = (new BasicNameValuePair("DomainId", Integer.toString(user
				.get_domainId())));

		String url = GetUrl(UrlManager.FETCH_SIGNUP_URL);
		String response = mHttpApi.DoHttpPost(url, postBody);

		if (!response.equals(null) && response.equals("0")) {
			SessionManager.setUserBo(user);
			return SUCCESS;

		} else {
			return ERROR;

		}

	}

	public List<BaseBusiness> GetUserByUsername(String username) {

		String path = String.format(UrlManager.FETCH_CHECK_USERNAME_URL,
				username);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		ObjectHandler objectHanlder = new ObjectHandler();

		@SuppressWarnings("unchecked")
		List<BaseBusiness> users = (List<BaseBusiness>) mHttpApi
				.DoHttpRequestJson(get, objectHanlder);

		return users;
	}

	public List<BaseBusiness> DoVerifyUser(String username, String code) {
		String path = String.format(UrlManager.FETCH_VERIFY_USER_URL, username,
				code);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		ObjectHandler objectHandler = new ObjectHandler();

		@SuppressWarnings("unchecked")
		List<BaseBusiness> users = (List<BaseBusiness>) mHttpApi
				.DoHttpRequestJson(get, objectHandler);

		return users;
	}

	public List<BaseBusiness> DoSignIn(String username, String password) {
		String path = String.format(UrlManager.FETCH_SIGNIN_URL, username,
				password);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		UserHandler userHandler = new UserHandler();
		userHandler.isSingleResultExpected = true;
		@SuppressWarnings("unchecked")
		List<BaseBusiness> users = (List<BaseBusiness>) mHttpApi
				.DoHttpRequestJson(get, userHandler);
		if (users != null) {
			SessionManager.setUserBo(users.get(0));
		}

		return users;
	}

	public List<BaseBusiness> DoResendVerificationCode(String username,
			String email) {
		String path = String.format(UrlManager.FETCH_RESEND_CODE_URL, username,
				email);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		ObjectHandler objectHandler = new ObjectHandler();

		@SuppressWarnings("unchecked")
		List<BaseBusiness> users = (List<BaseBusiness>) mHttpApi
				.DoHttpRequestJson(get, objectHandler);
		return users;
	}

}
