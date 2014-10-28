package wys.Http;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import wys.Business.BaseBusiness;
import wys.Modal.Handler.Jsonhandler;
import wys.Modals.AppModal;

public interface IHttpApi {

	public List<? extends BaseBusiness> doHttpRequestJson(HttpRequestBase httpRequest, Jsonhandler parser);

	public String doHttpPost(String url, NameValuePair... nameValuePairs );
	
	public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs);
	
	public HttpGet createHttpGet(String url,NameValuePair... nameValuePairs);
	
	public HttpResponse executeHttpRequest(HttpRequestBase httpRequest);
	
	
	
}
