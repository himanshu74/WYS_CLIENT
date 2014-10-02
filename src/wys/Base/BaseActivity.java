package wys.Base;

import wys.Helpers.PreferenceHelper;
import android.app.Activity;

public class BaseActivity extends Activity {

	private PreferenceHelper _prefHelper;

	public PreferenceHelper GetWYSPreferences() 
	{
		if (_prefHelper == null)
		{
			_prefHelper = new PreferenceHelper(BaseActivity.this);
		}
		return _prefHelper;
	}

}
