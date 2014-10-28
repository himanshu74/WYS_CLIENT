package wys.Http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;
import wys.Business.BaseBusiness;
import wys.Modal.Handler.Jsonhandler;
import wys.Modals.AppModal;

public class HttpApi implements IHttpApi {

	private static final String CLASS_TAG = HttpApi.class.getSimpleName();
	private DefaultHttpClient mHttpApiClient;
	private static final int TIME_OUT = 60;

	public HttpApi(DefaultHttpClient httpClient) {
		this.mHttpApiClient = httpClient;
	}

	private static final HttpParams createHttpParams() {
		final HttpParams params = new BasicHttpParams();

		// Turn off stale checking. Our connections break all the time anyway,
		// and it's not worth it to pay the penalty of checking every time.
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, TIME_OUT * 1000);
		HttpConnectionParams.setSoTimeout(params, TIME_OUT * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);

		return params;
	}

	public static DefaultHttpClient createHttpclient() {
		DefaultHttpClient client = new DefaultHttpClient();
		final HttpParams params = createHttpParams();
		client.setParams(params);

		SchemeRegistry registry = new SchemeRegistry();
		SocketFactory sf = PlainSocketFactory.getSocketFactory();
		registry.register(new Scheme("http", sf, 80));
		ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(
				client.getParams(), registry);
		DefaultHttpClient httpClient = new DefaultHttpClient(
				threadSafeClientConnManager, client.getParams());
		return httpClient;
	}

	public HttpResponse executeHttpRequest(HttpRequestBase httpRequest) {
		HttpResponse response = null;
		try {
			mHttpApiClient.getConnectionManager().closeExpiredConnections();
			response = mHttpApiClient.execute(httpRequest);

		} catch (ClientProtocolException e) {
			Log.e(CLASS_TAG, "", e);
		} catch (IOException e) {
			Log.e(CLASS_TAG, "", e);
		}

		return response;
	}

	@Override
	public List<? extends BaseBusiness> doHttpRequestJson(
			HttpRequestBase httpRequest, Jsonhandler parser) {

		HttpResponse response = executeHttpRequest(httpRequest);
		int statusCode = response.getStatusLine().getStatusCode();

		switch (statusCode) {
		case 200:
			if (parser != null) {
				try {
					return parser.parse(response);
				} catch (Exception ex) {
					Log.e(CLASS_TAG, "", ex);
				}
			}

		default:
			return null;
		}

	}

	@Override
	public String doHttpPost(String url, NameValuePair... nameValuePairs) {

		return null;
	}

	@Override
	public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs) {
		return null;
	}

	@Override
	public HttpGet createHttpGet(String url, NameValuePair... nameValuePairs) {

		String query = URLEncodedUtils.format(stripNulls(nameValuePairs),
				HTTP.UTF_8);

		HttpGet httpGet = null;

		if (query.equals(null) || query.length() == 0) {
			httpGet = new HttpGet(url);
			return httpGet;
		}

		else {
			httpGet = new HttpGet(url + "&" + query);
			return httpGet;
		}

	}

	public List<NameValuePair> stripNulls(NameValuePair... nameValuePairs) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		for (NameValuePair pair : nameValuePairs) {
			if (!pair.getValue().equals(null)) {
				params.add(pair);
			}
		}
		return params;
	}

}
