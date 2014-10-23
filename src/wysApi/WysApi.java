package wysApi;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

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
	private static final String DOMAIN = "192.168.0.3/WYS/api/";

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
		String Url = "http://192.168.0.3/WYS/api/user/";

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

	public  List<BaseBusiness> GetUserByUsername(String username) {

		String path = String.format(UrlManager.FETCH_CHECK_USERNAME_URL,
				username);
	
		
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		 ObjectHandler objectHanlder = new ObjectHandler();

		 @SuppressWarnings("unchecked")
		 List<BaseBusiness> users = (List<BaseBusiness>) mHttpApi.DoHttpRequestJson(get,
				 objectHanlder);

		return users;
	}

}
