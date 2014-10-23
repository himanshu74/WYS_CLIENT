package wys.Modal.Handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import wys.Business.BaseBusiness;
import wys.Business.UserBo;

public class UserHandler extends Jsonhandler {

	private static final String CLASS_TAG = UserHandler.class.getSimpleName();

	@Override
	protected List<BaseBusiness> SaveToModal(String json) {
		List<BaseBusiness> userList = new ArrayList<BaseBusiness>();
		JSONArray jsonArray;

		try {
			jsonArray = new JSONArray(json);

			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					UserBo user = new UserBo();
					user.set_username(jsonObject.getString("Username"));
					user.set_password(jsonObject.getString("Password"));
					user.set_email(jsonObject.getString("Email"));
					user.set_roleId(jsonObject.getInt("RoleId"));
					user.set_domainId(jsonObject.getInt("DomainId"));
					userList.add(user);
				}
			}

		} catch (JSONException jsException) {
			Log.e(CLASS_TAG, "", jsException);
		}

		return userList;

	}

}
