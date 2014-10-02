package wys.httpApi;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import wys.Business.*;
import wys.Dialogs.SignUpDialog;

public  class WysApi 
{

	private static final int SUCCESS =0;
	private static final int ERROR =1;
	

	public static int PostSignUp(UserBo user )
	{
		DefaultHttpClient client = new DefaultHttpClient();
		//HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
	
		HttpResponse response;
		JSONObject json = new JSONObject();
		String Url = "http://129.107.148.95/WYS/api/user/";
		
		try
		{
			HttpPost  post = new HttpPost(Url);
			json.put("Username", user.get_username());
			json.put("Password", user.get_password());
			json.put("Email", user.get_email());
			json.put("RoleId", user.get_roleId());
			json.put("DomainId", user.get_domainId());
			
			StringEntity se = new StringEntity(json.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			response = client.execute(post);
			if(response !=null)
			{
				
				if( response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT)
				{
					return SUCCESS;
				}
				else 
				{
					return ERROR;
				}
				
			}
			else
			{
				return ERROR;
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			return ERROR;
		}
		
		
	}
	
	
	
}
