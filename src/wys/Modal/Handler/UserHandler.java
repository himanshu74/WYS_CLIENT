package wys.Modal.Handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import wys.Business.BaseBusiness;
import wys.Business.UserBO;

public class UserHandler extends Jsonhandler {

	private static final String CLASS_TAG = UserHandler.class.getSimpleName();

	@Override
	protected List<BaseBusiness> saveToModal(String json) {
		List<BaseBusiness> userList = new ArrayList<BaseBusiness>();
		JSONArray jsonArray;

		try {
			jsonArray = new JSONArray(json);

			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					UserBO user = new UserBO();
					user.setUsername(jsonObject.getString("Username"));
					user.setPassword(jsonObject.getString("Password"));
					user.setEmail(jsonObject.getString("Email"));
					user.setRoleId(jsonObject.getInt("RoleId"));
					user.setDomainId(jsonObject.getInt("DomainId"));
					userList.add(user);
				}
			}

		} catch (JSONException jsException) {
			Log.e(CLASS_TAG, "", jsException);
		}

		return userList;

	}

}
