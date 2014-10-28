package wys.Modal.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;

import android.util.Log;
import wys.Business.BaseBusiness;

public class Jsonhandler {

	private static final String CLASS_TAG = Jsonhandler.class.getSimpleName();
	public int responseCode = 200;

	private void setValuesFromHeader(HttpResponse response) {

		if (!response.getStatusLine().equals(null)) {
			responseCode = response.getStatusLine().getStatusCode();
		}

	}

	public List<? extends BaseBusiness> parse(HttpResponse response) {
		setValuesFromHeader(response);
		List<BaseBusiness> busObject = null;
		if (responseCode != 204 && responseCode != 304) {
			try {
				String responseString = convertStreamToString(response
						.getEntity().getContent());
				busObject = saveToModal(responseString);
			} catch (IllegalStateException e) {
				Log.e(CLASS_TAG, "", e);

			} catch (IOException e) {
				Log.e(CLASS_TAG, "", e);

			}
		}
		return busObject;
	}

	private String convertStreamToString(InputStream inStream) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */

		BufferedReader buReader = new BufferedReader(new InputStreamReader(
				inStream));
		StringBuilder sb = new StringBuilder();

		String line = null;

		try {
			while ((line = buReader.readLine()) != null) {
				sb.append(line + "\n");
			}
		}

		catch (IOException io) {
			Log.e(CLASS_TAG, "", io);
		}

		finally {
			try {
				inStream.close();
			} catch (IOException ioException) {
				Log.e(CLASS_TAG, "", ioException);
			}

		}
		return sb.toString();

	}

	protected List<BaseBusiness> saveToModal(String json) {
		return null;

	}

}
