package wys.Modal.Handler;


import java.util.ArrayList;
import java.util.List;

import wys.Business.BaseBusiness;

public class ObjectHandler extends Jsonhandler {

//	private static final String CLASS_TAG = ObjectHandler.class.getSimpleName();

	public List<BaseBusiness> SaveToModal(String json) {
		BaseBusiness busObject = null;
        List<BaseBusiness> list = new ArrayList<BaseBusiness>();
		int value = Integer.parseInt(json.trim());
		busObject = new BaseBusiness();
		busObject.setStatus(value);
		list.add(busObject);

		return list;
	}

}
