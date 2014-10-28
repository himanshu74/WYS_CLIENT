package wys.Modal.Handler;


import java.util.ArrayList;
import java.util.List;

import wys.Business.BaseBusiness;

public class ObjectHandler extends Jsonhandler {

//	private static final String CLASS_TAG = ObjectHandler.class.getSimpleName();

	public List<BaseBusiness> saveToModal(String json) {
		BaseBusiness busObject = null;
        List<BaseBusiness> list = new ArrayList<BaseBusiness>();
		/*
		 * JSONArray jsonArray;
		 * 
		 * try { jsonArray = new JSONArray(json); if (jsonArray.length() > 0) {
		 * for (int i = 0; i < jsonArray.length(); i++) { JSONObject jsonObject
		 * = jsonArray.getJSONObject(i); if (!jsonObject.equals(null)) {
		 * BaseBusiness baseBusiness = new BaseBusiness();
		 * baseBusiness.setIsUsernameAvail(jsonObject .getInt("status"));
		 * list.add(baseBusiness); }
		 * 
		 * } }
		 */
		int value = Integer.parseInt(json.trim());
		busObject = new BaseBusiness();
		busObject.setIsUsernameAvail(value);
		list.add(busObject);

		return list;
	}

}
